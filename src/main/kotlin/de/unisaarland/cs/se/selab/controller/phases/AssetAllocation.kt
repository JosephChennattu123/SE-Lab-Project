package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyStatus
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Request
import de.unisaarland.cs.se.selab.model.vehicle.Vehicle
import de.unisaarland.cs.se.selab.model.vehicle.VehicleStatus
import de.unisaarland.cs.se.selab.model.vehicle.VehicleType
import de.unisaarland.cs.se.selab.util.AssetManager
import de.unisaarland.cs.se.selab.util.Dijkstra
import de.unisaarland.cs.se.selab.util.Logger

/**
 * Phase for allocation, reallocation and request creation for assets.
 * */
class AssetAllocation {

    /**
     * Allocate and reallocate assets for each emergency.
     * */
    fun execute(model: Model) {
        val sortedEmergencies = sortEmergencies(model.getAssignedEmergenciesObjects())
        for (emergency in sortedEmergencies) {
            handleEmergency(model, emergency)
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
     * Sort Vehicles by id
     * */
    private fun sortVehicles(vehicles: List<Vehicle>): MutableList<Vehicle> {
        return vehicles.sortedBy { it.vehicleID }.toMutableList()
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
                    .filter {
                        it.baseID == mainBase.baseId &&
                            it.canBeReallocated()
                    }
            )
        }
        vehiclesCanReroute.addAll(
            model.getVehiclesByIds(mainBase.vehicles).sortedBy { it.vehicleID }.filter {
                it.status == VehicleStatus.RETURNING &&
                    it.canBeReallocated()
            }
        )
        // vehiclesCanReroute.addAll(vehicles.filter { it.status == VehicleStatus.RETURNING })
        return vehiclesCanReroute
    }

    private fun handleEmergency(model: Model, emergency: Emergency) {
        if (emergency.status != EmergencyStatus.ONGOING) return
        val mainBase = model.getBaseById(
            emergency.mainBaseID
                ?: throw IllegalArgumentException("Emergency should have mainBase!")
        ) ?: throw IllegalArgumentException("Wrong base ID!")
        val vehicles = sortVehicles(model.getVehiclesByIds(mainBase.vehicles))

        AssetManager.allocateAssetsToEmergency(model, emergency, vehicles, false)
        if (!emergency.isFulfilled()) {
            reallocateAssets(model, emergency, mainBase, vehicles)
        }
    }

    private fun reallocateAssets(
        model: Model,
        emergency: Emergency,
        mainBase: Base,
        vehicles: List<Vehicle>
    ) {
        val reallocatableVehicles =
            getVehiclesCanReroute(emergency, mainBase, vehicles, model)
        AssetManager.allocateAssetsToEmergency(model, emergency, reallocatableVehicles, true)
        // if allocate & reallocate not failed, then change this canRequest
        if (emergency.assignedVehicleIDs.isNotEmpty()) emergency.canRequest = true
        if (!emergency.isFulfilled() && emergency.canRequest) {
            creatRequest(model, emergency, mainBase)
        }
    }

    private fun creatRequest(model: Model, emergency: Emergency, mainBase: Base) {
        val baseNeedRequest: MutableList<Int> = mutableListOf()
        val baseTypesNeeded = emergency.currentRequirements
            .map { VehicleType.getBaseType(it.vehicleType) }.toSet()
        for (baseType in baseTypesNeeded) {
            Dijkstra.getNextNearestBase(
                model.graph,
                mainBase.vertexID,
                baseType,
                setOf(mainBase.vertexID)
            )?.let { baseNeedRequest.add(it) }
        }

        if (baseNeedRequest.isNotEmpty()) {
            baseNeedRequest.sort()
            baseNeedRequest.forEach {
                val requestNew = Request.createNewRequest(
                    mainBase.baseId,
                    emergency.id,
                    it,
                    setOf(mainBase.baseId, it)
                )
                model.addRequest(requestNew)
                Logger.logRequest(
                    requestNew.requestId,
                    model.graph.vertices[it]?.baseId
                        ?: throw IllegalArgumentException("This vertex must have base!"),
                    emergency.id
                )
            }
        }
    }
}
