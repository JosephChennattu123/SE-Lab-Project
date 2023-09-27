package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.*
import de.unisaarland.cs.se.selab.util.AssetManager

class AssetAllocation {

    /**
     * Allocate and reallocate assets for each emergency.
     * */
    fun execute(model: Model) {
        val sortedEmergencies = sortEmergencies(model.getAssignedEmergenciesObjects())
        for (emergency in sortedEmergencies) {
            // allocate
            if (emergency.status == EmergencyStatus.ONGOING) {
                val mainBase = model.getBaseById(emergency.mainBaseID!!)!!
                val vehicles = sortAtBaseVehicles(model.getVehiclesByIds(mainBase.vehicles))
                AssetManager.allocateAssetsToEmergency(model, emergency, vehicles)
                if (!emergency.isFulfilled()) {
                    // reallocate
                    val lowerSeverityEmergencies =
                        model.getAssignedEmergenciesByIds(mainBase.assignedEmergencies)
                            .filter { it.severity < emergency.severity }
                    for (lowEmergency in lowerSeverityEmergencies) {
                        val vehiclesCanReroute =
                            model.getVehiclesByIds(lowEmergency.assignedVehicleIDs)
                                .filter { it.baseID == mainBase.baseId }
                    }

                }
            }
        }
    }

    /**
     * Sort Emergencies firstly by severity and then by id.
     * */
    private fun sortEmergencies(emergencies: List<Emergency>): List<Emergency> {
        return emergencies.sortedWith(
            compareByDescending<Emergency> { it.severity }
                .thenBy { it.id }
        )
    }

    /**
     * Sort Vehicles by id, and check if the status is AT_BASE
     * */
    private fun sortAtBaseVehicles(vehicles: List<Vehicle>): List<Vehicle> {
        return vehicles.filter { it.status == VehicleStatus.AT_BASE }.sortedBy { it.vehicleID }
    }
}
