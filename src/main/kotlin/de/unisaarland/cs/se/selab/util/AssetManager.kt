package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.*

/**
 * Returns requirements for emergencies and handles the allocation of assets to emergencies */
object AssetManager {
    /**
     * Constants to avoid 'magic number' errors */
    private const val VEHICLECOUNT4 = 4
    private const val VEHICLECOUNT5 = 5
    private const val VEHICLECOUNT6 = 6
    private const val WATER1200 = 1200
    private const val WATER3000 = 3000
    private const val WATER5400 = 5400
    private const val LADDERLENGTH30 = 30
    private const val LADDERLENGTH40 = 40
    private const val CRIMINALCAPACITY4 = 4
    private const val CRIMINALCAPACITY8 = 8
    private const val PATIENTCAPACITY5 = 5

    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements
     * */
    fun getFireRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_WATER,
                        2,
                        WATER1200
                    )
                )
            }

            2 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_WATER,
                        VEHICLECOUNT4,
                        WATER3000
                    ),
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_LADDER,
                        1,
                        LADDERLENGTH30
                    ),
                    EmergencyRequirement(
                        VehicleType.FIREFIGHTER_TRANSPORTER,
                        1,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        1,
                        1
                    )
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_WATER,
                        VEHICLECOUNT6,
                        WATER5400
                    ),
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_LADDER,
                        2,
                        LADDERLENGTH40
                    ),
                    EmergencyRequirement(
                        VehicleType.FIREFIGHTER_TRANSPORTER,
                        2,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        2,
                        2
                    ),
                    EmergencyRequirement(
                        VehicleType.EMERGENCY_DOCTOR_CAR,
                        1,
                        1
                    )
                )

            }

            else -> {
                listOf()
            }
        }
    }

    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements */
    fun getAccidentRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_TECHNICAL,
                        1,
                        null
                    )
                )
            }

            2 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_TECHNICAL,
                        2,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.POLICE_MOTOR_CYCLE,
                        1,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        1,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        1,
                        1
                    )
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_TECHNICAL,
                        VEHICLECOUNT4,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.POLICE_MOTOR_CYCLE,
                        2,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        VEHICLECOUNT4,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        3,
                        2
                    ),
                    EmergencyRequirement(
                        VehicleType.EMERGENCY_DOCTOR_CAR,
                        1,
                        1
                    )
                )
            }

            else -> {
                listOf()
            }
        }
    }

    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements */
    fun getCrimeRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        1,
                        1
                    )
                )
            }

            2 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        VEHICLECOUNT4,
                        CRIMINALCAPACITY4
                    ),
                    EmergencyRequirement(
                        VehicleType.K9_POLICE_CAR,
                        1,
                        1
                    ),
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        1,
                        null
                    )
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        VEHICLECOUNT6,
                        CRIMINALCAPACITY8
                    ),
                    EmergencyRequirement(
                        VehicleType.POLICE_MOTOR_CYCLE,
                        2,
                        null
                    ),
                    EmergencyRequirement(
                        VehicleType.K9_POLICE_CAR,
                        2,
                        2
                    ),
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        2,
                        1
                    ),
                    EmergencyRequirement(
                        VehicleType.FIREFIGHTER_TRANSPORTER,
                        1,
                        null
                    )
                )
            }

            else -> {
                listOf()
            }
        }
    }

    /**
     * @param severity Severity of the emergency
     * @return list of EmergencyRequirements for this emergency */
    fun getMedicalRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        1,
                        null
                    )
                )
            }

            2 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        2,
                        2
                    ),
                    EmergencyRequirement(
                        VehicleType.EMERGENCY_DOCTOR_CAR,
                        1,
                        1
                    )
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        VEHICLECOUNT5,
                        PATIENTCAPACITY5
                    ),
                    EmergencyRequirement(
                        VehicleType.EMERGENCY_DOCTOR_CAR,
                        2,
                        2
                    ),
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_TECHNICAL,
                        2,
                        null
                    )
                )
            }

            else -> {
                listOf()
            }
        }
    }

    /**
     * @param model The model
     * @param emergency The emergency to allocate to
     * @param vehicles The list of vehicles to assign to the emergency */
    fun allocateAssetsToEmergency(
        model: Model,
        emergency: Emergency,
        vehicles: MutableList<Vehicle>
    ) {
        // remove vehicles that do not fit the requirement type or do not fulfill staff requirements.
        filterAssetsByRequirement(model, vehicles, emergency.currentRequiredAssets)

        // find vehicles that cannot reach the emergency in time.
        val vehiclesThatCannotReachInTime = mutableListOf<Vehicle>()
        for (vehicle in vehicles) {
            when (vehicle.status) {
                VehicleStatus.AT_BASE -> {
                    // If vehicle is currently at base, calculate drive time from base to emergency
                    val newPath = Dijkstra.getShortestPathFromVertexToEdge(
                        model.graph,
                        model.getBaseById(vehicle.baseID)!!.vertexID,
                        emergency.location,
                        vehicle.height
                    )

                    if (!emergency.canReachInTime(newPath.totalTicksToArrive)) {
                        vehiclesThatCannotReachInTime.add(vehicle)
                    }
                }

                VehicleStatus.RETURNING, VehicleStatus.TO_EMERGENCY -> {
                    // If vehicle is currently on the road,
                    // calculate drive time from their precise position on the edge to emergency
                    val newPath = Dijkstra.getShortestPathFromEdgeToEdge(
                        model.graph,
                        vehicle.getCurrentVertexID(),
                        vehicle.getNextVertexID(),
                        vehicle.getDistanceOnEdge(),
                        emergency.location,
                        vehicle.height
                    )
                    if (!emergency.canReachInTime(newPath.totalTicksToArrive)) {
                        vehiclesThatCannotReachInTime.add(vehicle)
                    }
                }

                else -> {}
            }
        }
        // remove vehicles that cannot reach the emergency in time.
        vehicles.removeAll(vehiclesThatCannotReachInTime)
        val mainBase = emergency.mainBaseID?.let { model.getBaseById(it) }!!
        filterAssetsByOptimalSolution(mainBase, vehicles, emergency.currentRequiredAssets)
        for (vehicle in vehicles) {
            if (vehicle) { // if vehicle will be reallocated
                val oldEmergency = model.getAssignedEmergencyById(vehicle.emergencyID!!)
                var req: EmergencyRequirement
                if (oldEmergency!!.currentRequiredAssets.any { it.vehicleType == vehicle.vehicleType }) { // if old
                    // emergency already has requirement of type vehicle.VehicleType
                    req =
                        oldEmergency.currentRequiredAssets.first { it.vehicleType == vehicle.vehicleType }
                    // find requirement that needs to be changed

                    req.numberOfVehicles++ // increase the number of current needed vehicles of this type by 1
                } else {
                    req = EmergencyRequirement(vehicle.vehicleType, 1, null)
                    oldEmergency.currentRequiredAssets.add(req)
                    TODO()
                }

            }
        }
    }

    /**
     * modifies the parameter lists to contain the optimal selection of concrete vehicles to be assigned to an emergency
     * @param vehicles List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByOptimalSolution(
        mainBase: Base,
        vehicles: MutableList<Vehicle>,
        requirements: MutableList<EmergencyRequirement>
    ) {
        val vehicleIds = vehicles.map { it.vehicleID }
        val idToVehicleMap = vehicles.associateBy { it.vehicleID }
        for (size in requirements.size downTo 1) {
            val combinationsOfIds = computeCombinations(vehicleIds, size)
            val validCombinations =
                mutableListOf<List<Int>>() // combinations that fulfill the requirements
            for (combination in combinationsOfIds) {
                val subsetToCheck: List<Vehicle> =
                    idToVehicleMap.filter { combination.contains(it.key) }.values.toList()
                if (checkIfVehiclesFulfillRequirements(mainBase, subsetToCheck, requirements)) {
                    validCombinations.add(combination)
                }
            }
            if (validCombinations.isNotEmpty()) {
                // remove all vehicles that are not part of the valid combinations
                vehicles.removeAll(vehicles.filter {
                    !validCombinations.flatten().contains(it.vehicleID)
                })
                break
            }
        }
    }

    /**
     * modifies the list of Vehicles to contain vehicles that can be assigned to an emergency
     * @param vehiclesToCheck List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByRequirement(
        model: Model,
        vehiclesToCheck: MutableList<Vehicle>,
        requirements: List<EmergencyRequirement>
    ) {
        val vehiclesToBeRemoved = mutableListOf<Vehicle>()
        for (vehicle in vehiclesToCheck) {
            // check if the vehicle type does not match the requirement type
            // or if the base does not have enough staff to send the vehicle.
            if (!requirements.any {
                    vehicle.vehicleType != it.vehicleType
                            && vehicle.staffCapacity > model.getBaseById(vehicle.baseID)!!.currStaff
                }) {
                // collect vehicles that needed to be removed.
                vehiclesToBeRemoved.add(vehicle)
            }
        }
        vehiclesToCheck.removeAll(vehiclesToBeRemoved)
    }

    /**
     * removes vehicles from an emergency and resets the emergency's requirements
     * @param emergency The emergency to remove vehicles from
     * @param vehiclesToBeReallocated The list of vehicles to be removed
     * @param model The model
     * */
    private fun removeVehiclesFromEmergency(
        emergency: Emergency,
        vehiclesToBeReallocated: List<Vehicle>,
        model: Model
    ) {
        val remainingVehicles =
            model.getVehiclesByIds((emergency.assignedVehicleIDs)
                .filter { it1 ->
                    vehiclesToBeReallocated
                        .map { it.vehicleID }.contains(it1)
                })
        emergency.currentRequiredAssets.clear()
        fulfillRequirementsOfEmergency(emergency, remainingVehicles)
        // remove the vehicles from the emergency.
        for (vehicle in vehiclesToBeReallocated) {
            emergency.removedAssignedVehicle(vehicle)
            vehicle.emergencyID = null
        }
    }


    /**
     * Updates the current requirements of the emergency.
     * @param emergency The emergency to update.
     * @param vehicles The list of new vehicles to be assigned to the emergency.
     * */
    private fun fulfillRequirementsOfEmergency(emergency: Emergency, vehicles: List<Vehicle>) {
        val totalRequirements = emergency.requiredAssets
        val currentRequirements = emergency.currentRequiredAssets

        for (requirement in totalRequirements) {
            val requiredType = requirement.vehicleType
            var totalVehiclesNeeded = requirement.numberOfVehicles
            var totalAssetsNeeded = requirement.amountOfAsset

            for (vehicle in vehicles) {
                // check if the type matches
                // and decrement the required number of vehicles if matches.
                if (vehicle.vehicleType != requiredType) {
                    continue
                }
                totalVehiclesNeeded--
                // do not decease the current assets if it has no assets.
                if (totalAssetsNeeded == null || totalAssetsNeeded == 0) {
                    continue
                }
                // if the vehicle has assets, decrease the current assets
                if (vehicle.vehicleType != VehicleType.FIRE_TRUCK_LADDER) {
                    totalAssetsNeeded -= vehicle.currentNumberOfAssets
                    continue
                }
                // for ladders if the ladder is not long enough, do not decrease the current assets.
                if (totalAssetsNeeded < vehicle.currentNumberOfAssets) {
                    totalAssetsNeeded = 0
                    continue
                }
            }
            // update the current requirements.
            currentRequirements.add(
                EmergencyRequirement(
                    requiredType,
                    totalVehiclesNeeded,
                    totalAssetsNeeded
                )
            )
        }
        removedFulfilledRequirements(emergency)
    }

    private fun checkIfVehiclesFulfillRequirements(
        mainBase: Base,
        vehicles: List<Vehicle>,
        requirements: List<EmergencyRequirement>
    ): Boolean {
        for (requirement in requirements) {

            if (!isThereEnoughStaffAtBase(mainBase, vehicles)) {
                return false
            }

            val requiredType = requirement.vehicleType
            var totalVehiclesNeeded = requirement.numberOfVehicles
            var totalAssetsNeeded = requirement.amountOfAsset

            for (vehicle in vehicles) {
                // check if the type matches
                // and decrement the required number of vehicles if matches.
                if (vehicle.vehicleType != requiredType) {
                    continue
                }
                totalVehiclesNeeded--
                // do not decease the current assets if it has no assets.
                if (totalAssetsNeeded == null || totalAssetsNeeded == 0) {
                    continue
                }
                // if the vehicle has assets, decrease the current assets
                if (vehicle.vehicleType != VehicleType.FIRE_TRUCK_LADDER) {
                    totalAssetsNeeded -= vehicle.currentNumberOfAssets
                    continue
                }
                // for ladders if the ladder is not long enough, do not decrease the current assets.
                if (totalAssetsNeeded < vehicle.currentNumberOfAssets) {
                    totalAssetsNeeded = 0
                    continue
                }
            }
            // update the current requirements.
            if (totalVehiclesNeeded == 0 || totalAssetsNeeded!! > 0) {
                return false
            }
        }
        return true
    }

    /**
     * removes fulfilled requirements from the emergency.
     * */
    private fun removedFulfilledRequirements(emergency: Emergency) {
        val currentRequirements = emergency.currentRequiredAssets
        val requirementsToRemove = mutableListOf<EmergencyRequirement>()
        for (requirement in currentRequirements) {
            if (requirement.numberOfVehicles == 0) {
                requirementsToRemove.add(requirement)
            }
        }
        currentRequirements.removeAll(requirementsToRemove)
    }

    private fun isThereEnoughStaffAtBase(base: Base, vehicles: List<Vehicle>): Boolean {
        return base.currStaff >= vehicles.sumOf { it.staffCapacity }
    }

    private fun computeCombinations(ids: List<Int>, size: Int): List<List<Int>> {
        if (size == 0) {
            return listOf(listOf())
        }
        if (ids.isEmpty()) {
            return listOf()
        }
        val head = ids.first()
        val tail = ids.drop(1)
        val withHead = computeCombinations(tail, size - 1).map { listOf(head) + it }
        val withoutHead = computeCombinations(tail, size)
        return withHead + withoutHead
    }
}
