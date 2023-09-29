package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.controller.events.EventStatus
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus

/**
 * Update phase
 * In this phase the elements of the simulation get updated.
 */
class UpdatePhase {
    var eventOccurred: Boolean = false

    /**
     * @param model the model
     */
    fun execute(model: Model) {
        // TODO check all code, everything in this method might be wrong, it was used to fix detekt problems
        collectArrivedV(model.getSortedVehicleList())
        processVehicles(model.getSortedVehicleList())
        printLog(model.getSortedVehicleList())
        processEmergencies(model.getCurrentEmergencies()) // TODO needs checking might be wrong
        processActiveEvents(model.getCurrentEventsObjects(), model.currentEvents)
        processPostponedEvents(model)
        timeUpdate(model)
        TODO()
    }

    /**
     * update the status for all vehicles
     */
    private fun processVehicles(vehicles: List<Vehicle>) {
        for (vehicle in vehicles) {
            when (vehicle.status) {
                VehicleStatus.BUSY -> {
                    if (vehicle.decreaseBusyTicks()) vehicle.status = VehicleStatus.AT_BASE
                }

                VehicleStatus.RETURNING, VehicleStatus.ASSIGNED, VehicleStatus.TO_EMERGENCY -> {
                    vehicle.driveUpdate()
                }

                else -> {}
            }
        }
    }

    private fun processEmergencies(emergencies: List<Emergency>) {
        for (emergency in emergencies) {
            when (emergency.status) {
                // todo
                else -> {}
            }
        }
        TODO()
    }

    /** used to decrement timer of active events and if required remove them from list of
     * current event ids
     * */

    private fun processActiveEvents(
        activeEvents: List<de.unisaarland.cs.se.selab.controller.events.Event>,
        activeEventIds: MutableList<Int>
    ) {
        for (event in activeEvents) {
            event.decrementTimer()
            if (event.duration == 0) {
                activeEventIds.remove(event.id)
                event.status = EventStatus.FINISHED
            }
        }
    }

    private fun processPostponedEvents(model: Model) {
        val eventsToTrigger: List<Int>? = model.tickToEventId[model.currentTick]
        if (eventsToTrigger != null) {
            for (eventToBeCheckedAndAdded in model.getEventsByIds(eventsToTrigger.sorted())) {
                eventToBeCheckedAndAdded.applyEffect(model)
            }
        }
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
    }

    private fun timeUpdate(model: Model) {
        model
        TODO()
    }

    private fun printLog(vehicles: List<Vehicle>) {
        vehicles
        TODO()
    }

    private fun collectArrivedV(vehicles: List<Vehicle>): List<Vehicle> {
        vehicles
        TODO()
    }
}
