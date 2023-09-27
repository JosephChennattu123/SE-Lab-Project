package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.*
import de.unisaarland.cs.se.selab.util.AssetManager
import de.unisaarland.cs.se.selab.util.Dijkstra

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
                    val vehiclesCanReroute =
                        getVehiclesCanReroute(emergency, mainBase, vehicles, model)
                    AssetManager.allocateAssetsToEmergency(model, emergency, vehiclesCanReroute)
                    // if allocate & reallocate not failed, then change this canRequest
                    if (emergency.assignedVehicleIDs.isNotEmpty()) emergency.canRequest = true
                    if (!emergency.isFulfilled() && emergency.canRequest) {
                        // request
                        for (req in emergency.currentRequiredAssets) {
                            val nextNearestBase = Dijkstra.getNextNearestBase(
                                model.graph,
                                mainBase.vertexID,
                                req.vehicleType.getBaseType(req.vehicleType),
                                setOf()
                            )

                        }


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

    /**
     * @return Vehicles can reroute:
     * 1. on their way to an emergency with lower severity
     * 2. or on their way back to the base
     * */
    private fun getVehiclesCanReroute(
        emergency: Emergency,
        mainBase: Base,
        vehicles: List<Vehicle>,
        model: Model
    ): MutableList<Vehicle> {
        val vehiclesCanReroute = mutableListOf<Vehicle>()
        val lowerSeverityEmergencies =
            model.getAssignedEmergenciesByIds(mainBase.assignedEmergencies)
                .filter { it.severity < emergency.severity }
        for (lowEmergency in lowerSeverityEmergencies) {
            vehiclesCanReroute.addAll(
                model.getVehiclesByIds(lowEmergency.assignedVehicleIDs)
                    .filter { it.baseID == mainBase.baseId && it.status == VehicleStatus.TO_EMERGENCY }
            )
        }
        vehiclesCanReroute.addAll(vehicles.filter { it.status == VehicleStatus.RETURNING })
        return vehiclesCanReroute
    }

}
