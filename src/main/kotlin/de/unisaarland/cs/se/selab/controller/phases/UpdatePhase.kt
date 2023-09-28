package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyStatus
import de.unisaarland.cs.se.selab.model.Model

import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus
import de.unisaarland.cs.se.selab.util.Dijkstra
import de.unisaarland.cs.se.selab.util.Logger

/**
 * Update phase
 * In this phase the elements of the simulation get updated.
 */
class UpdatePhase {
    var eventOccurred: Boolean = false

    /**
     * begins the update phase processing
     */
    fun execute(model: Model) {
        // TODO check all code, everything in this method might be wrong, it was used to fix detekt problems
        collectArrivedV(model.getSortedVehicleList())
        processVehicles(model.getSortedVehicleList())
        printLog(model.getSortedVehicleList())
        processEmergencies(model.getCurrentEmergencies(), model) // TODO needs checking might be wrong
        processActiveEvents()
        processPostponedEvents()
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

    private fun processEmergencies(emergencies: List<Emergency>, model: Model) {
        for (emergency in emergencies) {
            emergency.decrementTimer()
        }
        val failingEmergencies = emergencies.filter {
            it.timeElapsed >= it.maxDuration
        }.toList()

        val resolvableEmergencies = emergencies.filter {
            it.status == EmergencyStatus.BEING_HANDLED && it.handleTime == 0 && it.timeElapsed < it.maxDuration
        }.toList()

        val ongoingEmergencies = emergencies.filter {
            it.status == EmergencyStatus.ONGOING && it.isFulfilled() && it.timeElapsed < it.maxDuration
        }.toList()

        val handleableEmergencies = emergencies.filter {
            it.status == EmergencyStatus.WAITING_FOR_ASSETS && it.canStart() && it.timeElapsed < it.maxDuration
        }.toList()


        for (emergency in ongoingEmergencies) {
            emergency.changeStatus(EmergencyStatus.WAITING_FOR_ASSETS)
        }

        for (emergency in handleableEmergencies) {
            emergency.changeStatus(EmergencyStatus.BEING_HANDLED)
            Logger.logEmergencyHandlingStart(emergency.id)
            for (requirement in emergency.requiredAssets) {
                var amount = requirement.amountOfAsset
                if (amount != null) {
                    for (v in (model.getVehiclesByIds(emergency.availableVehicleIDs))) {
                        if (v.vehicleType == requirement.vehicleType)
                        amount = v.handleEmergency(amount!!)
                        // every call of handleEmergency reduces the still required amount of asset
                    }
                }
            }
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
            val vehicle = model.getVehicleById(vehicleID)!!
            vehicle.status = VehicleStatus.RETURNING // change status for all vehicles of this emergency to RETURNING
            setReturnPath(vehicle, model)
        }
        model.assignedEmergencies.remove(emergency.id) // remove failed emergencies from model
    }

    private fun setReturnPath(vehicle: Vehicle, model: Model) {
        val path = Dijkstra.getShortestPathFromVertexToVertex( // calculate path back to base for vehicle
            model.graph,
            vehicle.positionTracker.path.vertexPath[vehicle.positionTracker.currentVertexIndex],
            model.getBaseById(vehicle.baseID)!!.vertexID,
            vehicle.height
        )
        vehicle.setNewPath(path)
    }

    private fun processActiveEvents() {
        // todo
    }

    private fun processPostponedEvents() {
        TODO()
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
