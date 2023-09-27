package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.*
import de.unisaarland.cs.se.selab.util.Dijkstra

/**
 * Iterates through all vehicles and emergencies and updates their states and fields
 */
class UpdatePhase {

    /**
     * begins the update phase processing
     */
    fun execute(model: Model) {
        processVehicles(model.getSortedVehicleList())
        processEmergencies(model.getAssignedEmergenciesObjects(), model)
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
        for (emergency in emergencies) {
            if (emergency.status == EmergencyStatus.WAITING_FOR_ASSETS) {
                if (emergency.canStart()) {
                    emergency.changeStatus(EmergencyStatus.BEING_HANDLED)
                }
            }
        }
        for (emergency in emergencies) {
            if (emergency.status == EmergencyStatus.BEING_HANDLED) {
                if (emergency.handleTime == 0 && emergency.maxDuration > emergency.timeElapsed) {
                    emergency.changeStatus(EmergencyStatus.RESOLVED)
                    for (vehicleID in emergency.assignedVehicleIDs) {
                        val vehicle = model.getVehicleById(vehicleID)!!
                        vehicle.status =
                            VehicleStatus.RETURNING // change status for all vehicles of this emergency to RETURNING
                        val path =
                            Dijkstra.getShortestPathFromVertexToVertex( // calculate path back to base for vehicle
                                model.graph,
                                vehicle.positionTracker.path.vertexPath[vehicle.positionTracker.currentVertexIndex],
                                model.getBaseById(vehicle.baseID)!!.vertexID,
                                vehicle.height
                            )
                        vehicle.setNewPath(path)
                    }
                }
            }
        }
    }

private fun processActiveEvents() {
    // todo
}

private fun processPostponedEvents() {
    // todo
}

private fun timeUpdate(model: Model) {
    // todo
}

private fun printLog(vehicles: List<Vehicle>) {
    // todo
}

private fun collectArrivedV(vehicles: List<Vehicle>): List<Vehicle> {
    // todo
    return TODO()
}
}
