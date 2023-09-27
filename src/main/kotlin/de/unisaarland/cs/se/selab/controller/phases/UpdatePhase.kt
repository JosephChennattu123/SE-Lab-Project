package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus

/**
 * Iterates through all vehicles and emergencies and updates their states and fields
 */
class UpdatePhase {

    /**
     * begins the update phase processing
     */
    fun execute(model: Model) {
        processVehicles(model.getSortedVehicleList())
        processEmergencies(model.getAssignedEmergenciesObjects())
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
            emergency.handle()
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
