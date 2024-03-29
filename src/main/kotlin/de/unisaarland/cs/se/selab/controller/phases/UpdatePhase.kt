package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.controller.events.EventStatus
import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyStatus
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.vehicle.Vehicle
import de.unisaarland.cs.se.selab.model.vehicle.VehicleStatus
import de.unisaarland.cs.se.selab.util.Dijkstra
import de.unisaarland.cs.se.selab.util.Logger

/**
 * Update phase
 * In this phase the elements of the simulation get updated.
 */
class UpdatePhase {
    // var eventOccurred: Boolean = false

    /**
     * begins the update phase processing
     * @param model the model
     */
    fun execute(model: Model) {
        model.eventOccurred = false
        // TODO check all code, everything in this method might be wrong, it was used to fix detekt problems
        processVehicles(model.getSortedVehicleList(), model)
        processEmergencies(model.getAssignedEmergenciesObjects(), model) // TODO needs checking might be wrong
        processActiveEvents(model.getCurrentActiveEvents(), model)
        processPostponedEvents(model)
    }

    /**
     * update the status for all vehicles
     */
    private fun processVehicles(vehicles: List<Vehicle>, model: Model) {
        for (vehicle in vehicles) {
            when (vehicle.status) {
                VehicleStatus.BUSY -> {
                    if (vehicle.decreaseBusyTicks()) vehicle.status = VehicleStatus.AT_BASE
                }

                VehicleStatus.ASSIGNED -> {
                    vehicle.status = VehicleStatus.TO_EMERGENCY
                }

                VehicleStatus.RETURNING, VehicleStatus.TO_EMERGENCY -> {
                    vehicle.driveUpdate()
                    if (vehicle.status == VehicleStatus.WAITING) { // if a vehicle arrived at an emergency,
                        // add it to the list of the emergency's available vehicles
                        val emergencyID = vehicle.emergencyID as Int
                        val emergency = model.getAssignedEmergencyById(emergencyID) as Emergency
                        emergency.addArrivedVehicleId(vehicle.vehicleID)
                    }
                }

                else -> {}
            }
        }
    }

    private fun processEmergencies(emergencies: List<Emergency>, model: Model) {
        emergencies.forEach {
            it.decrementTimer()
        }
        val failingEmergencies = emergencies.filter {
            it.timeElapsed >= it.maxDuration
        }.toList().sortedBy { it.id }

        val resolvableEmergencies = emergencies.filter {
            it.status == EmergencyStatus.BEING_HANDLED && it.handleTime == 0 && it.timeElapsed < it.maxDuration
        }.toList().sortedBy { it.id }

        val fulfilledEmergencies = emergencies.filter {
            it.status == EmergencyStatus.ONGOING && it.isFulfilled() && !it.canStart() &&
                it.timeElapsed < it.maxDuration
        }.toList()

        val handleableEmergencies = emergencies.filter {
            (it.status == EmergencyStatus.WAITING_FOR_ASSETS || it.status == EmergencyStatus.ONGOING) &&
                it.canStart() && it.timeElapsed < it.maxDuration
        }.toList().sortedBy { it.id }

        fulfilledEmergencies.forEach {
            it.changeStatus(EmergencyStatus.WAITING_FOR_ASSETS)
        }

        handleableEmergencies.forEach { emergency ->
            emergency.changeStatus(EmergencyStatus.BEING_HANDLED)
            Logger.logEmergencyHandlingStart(emergency.id)
            /**
             * Goes through all vehicles by type of Base and subtracts available assets from them
             */
            emergency.handle(model)
        }

        for (emergency in resolvableEmergencies) {
            emergency.changeStatus(EmergencyStatus.RESOLVED)
            Logger.logEmergencyResult(emergency.id, true)
            endEmergency(emergency, model)
        }

        for (emergency in failingEmergencies) {
            emergency.changeStatus(EmergencyStatus.FAILED)
            Logger.logEmergencyResult(emergency.id, false)
            endEmergency(emergency, model)
        }
    }

    private fun endEmergency(emergency: Emergency, model: Model) {
        for (vehicleID in emergency.assignedVehicleIDs) {
            assert(model.getVehicleById(vehicleID) != null)
            val vehicle = model.getVehicleById(vehicleID) as Vehicle
            vehicle.status = VehicleStatus.RETURNING // change status for all vehicles of this emergency to RETURNING
            setReturnPath(vehicle, model)
        }
        model.finishedEmergencies.add(emergency.id)
        model.assignedEmergencies.remove(emergency.id) // remove failed emergencies from model
    }

    private fun setReturnPath(vehicle: Vehicle, model: Model) {
        assert(model.getBaseById(vehicle.baseID) != null)
        val base = model.getBaseById(vehicle.baseID) as Base
        val vehiclesOldDestination = vehicle.getDestination() ?: error("destination is null")
        val path = Dijkstra.getShortestPathFromVertexToVertex( // calculate path back to base for vehicle
            model.graph,
            vehiclesOldDestination,
            base.vertexID,
            vehicle.height
        )
        vehicle.setNewPath(path)
    }

    private fun processActiveEvents(
        activeEvents: List<Event>,
        model: Model
    ) {
        for (event in activeEvents) {
            event.decrementTimer()
            if (event.duration == 0) {
                event.status = EventStatus.FINISHED
                event.removeEffect(model)
            }
        }
    }

    private fun processPostponedEvents(model: Model) {
        // road event
        for (listRoadEventsPostponed in model.roadToPostponedEvents.toSortedMap().values) {
            for (roadEventPostponed in listRoadEventsPostponed.distinct().sorted()) { // sorts to pick lowest id first
                (model.getEventById(roadEventPostponed) as Event).applyEffect(model)
            }
        }
        // vehicle event
        for (listVehicleEventsPostponed in model.vehicleToPostponedEvents.toSortedMap().values) {
            for (vehicleEventsPostponed in listVehicleEventsPostponed.sortedBy { it.id }) { // sorts to pick lowest
                // id first
                vehicleEventsPostponed.applyEffect(model)
            }
        }
        val eventsToTrigger: List<Int>? = model.tickToEventId[model.currentTick]
        if (eventsToTrigger != null) {
            for (eventToBeCheckedAndAdded in model.getEventsByIds(eventsToTrigger.sorted())) {
                eventToBeCheckedAndAdded.applyEffect(model)
            }
        }
    }
}
