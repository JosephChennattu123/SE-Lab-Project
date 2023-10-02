package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus

/**
 * Update phase
 * In this phase the elements of the simulation get updated.
 */
class UpdatePhase {
    var eventOccured: Boolean = false

    /**
     * @param model the model
     */
    fun execute(model: Model) {
        // TODO check all code, everything in this method might be wrong, it was used to fix detekt problems
        collectArrivedV(model.getSortedVehicleList())
        processVehicles(model.getSortedVehicleList())
        printLog(model.getSortedVehicleList())
        processEmergencies(model.getCurrentEmergencies()) // TODO needs checking might be wrong
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

    private fun processEmergencies(emergencies: List<Emergency>) {
        for (emergency in emergencies) {
            when (emergency.status) {
                // todo
                else -> {}
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
