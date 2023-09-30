package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyRequirement
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus
import de.unisaarland.cs.se.selab.model.VehicleType

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
     */
    fun getFireRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, 2, WATER1200))
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
                    EmergencyRequirement(VehicleType.AMBULANCE, 1, 1)
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
     * @return List of EmergencyRequirements
     */
    fun getAccidentRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(EmergencyRequirement(VehicleType.FIRE_TRUCK_TECHNICAL, 1, null))
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
                        0
                    ),
                    EmergencyRequirement(VehicleType.AMBULANCE, 1, 1)
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
                        0
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
     * @return List of EmergencyRequirements
     */
    fun getCrimeRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(EmergencyRequirement(VehicleType.POLICE_CAR, 1, 1))
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
                        0
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
     * @return list of EmergencyRequirements for this emergency
     */
    fun getMedicalRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        1,
                        0
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
     * @param vehicles The list of vehicles to assign to the emergency
     */
    fun allocateAssetsToEmergency(
        model: Model,
        emergency: Emergency,
        vehicles: MutableList<Vehicle>
    ) {
        // remove vehicles that do not fit the requirement type or do not fulfill staff requirements.
        filterAssetsByRequirement(model, vehicles, emergency.currentRequirements)

        // find vehicles that cannot reach the emergency in time.
        val vehiclesThatCannotReachInTime = mutableListOf<Vehicle>()
        for (vehicle in vehicles) {
            when (vehicle.status) {
                // If vehicle is currently at base, calculate drive from vertex to edge.
                VehicleStatus.AT_BASE -> {
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

                // if vehicle is currently on the road, calculate drive time from edge to edge.
                VehicleStatus.RETURNING, VehicleStatus.TO_EMERGENCY -> {
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
        // remove vehicles that do not fulfill the requirements.
        filterAssetsByOptimalSolution(mainBase, vehicles, emergency.currentRequirements)
        TODO("assign vehicles to emergency and update emergency requirements here.")
    }

    /**
     * modifies the parameter lists to contain the optimal selection of concrete vehicles to be assigned to an emergency
     * @param vehicles List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against
     */
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
            // filter out combinations that do not fulfill the requirements
            for (combination in combinationsOfIds) {
                val subsetToCheck: List<Vehicle> =
                    idToVehicleMap.filter { combination.contains(it.key) }.values.toList()
                if (checkIfVehiclesFulfillRequirements(mainBase, subsetToCheck, requirements)) {
                    validCombinations.add(combination)
                }
            }
            // if there are valid combinations,
            // remove all vehicles that are not part of the valid combinations
            TODO("get the one optimal set of vehicles from all valid sets.")
            if (validCombinations.isNotEmpty()) {
                // val optimalSet = validCombinations.sortedWith().first()
                // remove all vehicles that are not part of the valid combinations
                vehicles.removeAll(
                    vehicles.filter {
                        !validCombinations.flatten().contains(it.vehicleID)
                    }
                )
                break
            }
        }
    }

    /**
     * modifies the list of Vehicles to contain vehicles that can be assigned to an emergency
     * @param vehiclesToCheck List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against
     */
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
                    vehicle.vehicleType != it.vehicleType &&
                        vehicle.staffCapacity > model.getBaseById(vehicle.baseID)!!.currStaff
                }
            ) {
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
        // remove the vehicles that are reallocated from the emergency.
        emergency.assignedVehicleIDs.removeAll(vehiclesToBeReallocated.map { it.vehicleID })
        val remainingVehicles = model
            .getVehiclesByIds(emergency.assignedVehicleIDs)

        // reset the current requirements of the emergency.
        emergency.currentRequirements.clear()

        // update the current requirements of the emergency.
        refreshEmergencyRequirements(emergency, remainingVehicles)

        // remove the vehicles from the emergency.
        for (vehicle in vehiclesToBeReallocated) {
            emergency.removedAssignedVehicle(vehicle)
            vehicle.emergencyID = null
        }
    }

    /**
     * Updates the current requirements of the emergency based on the assigned vehicles.
     * @param emergency The emergency to update.
     * @param vehicles vehicles that are assigned to the emergency.
     * */
    private fun refreshEmergencyRequirements(emergency: Emergency, vehicles: List<Vehicle>) {
        val totalRequirements = emergency.baseRequirements
        val requirementsCopy = totalRequirements.map { it.copy() }.toMutableList()
        for (vehicle in vehicles) {
            fulfillAndUpdateEmergencyRequirements(requirementsCopy, vehicle)
        }
        // only add requirements that are not fully fulfilled.
        for (requirement in requirementsCopy) {
            if (requirement.numberOfVehicles > 0) {
                emergency.currentRequirements.add(requirement)
            }
        }
    }

    /**
     * checks if the given vehicles fulfill the requirements of the emergency.
     * @param mainBase The main base of the emergency.
     * @param vehicles The list of vehicles to check.
     * */
    private fun checkIfVehiclesFulfillRequirements(
        mainBase: Base,
        vehicles: List<Vehicle>,
        requirements: List<EmergencyRequirement>
    ): Boolean {
        val requirementsCopy = requirements.map { it.copy() }.toMutableList()
        if (!isThereEnoughStaffAtBase(mainBase, vehicles)) {
            return false
        }
        // check if every vehicle satisfies a requirement.
        for (vehicle in vehicles) {
            if (!tryToFulfillRequirements(vehicle, requirementsCopy)) {
                return false
            }
        }
        // check if emergency blocked (vehicle amount satisfied but asset amount not satisfied).
        for (requirement in requirementsCopy) {
            val numberOfAssets = requirement.amountOfAsset
            if (numberOfAssets != null) {
                if (requirement.numberOfVehicles == 0 && numberOfAssets > 0) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Checks if a single vehicle satisfies any requirement of the emergency (partially or fully).
     * successful assignments of vehicles will modify the state of requirements.
     * to be used in optimal solution for checking if a set of vehicles satisfies the requirements of an emergency.
     * @param vehicle The vehicle that will fulfill the requirement.
     * @return true if the vehicle satisfies a requirement, false otherwise.
     * */
    private fun tryToFulfillRequirements(
        vehicle: Vehicle,
        requirements: List<EmergencyRequirement>
    ): Boolean {
        for (requirement in requirements.filter {
            it.numberOfVehicles > 0 &&
                it.vehicleType == vehicle.vehicleType
        }) {
            var totalAssetsNeeded = requirement.amountOfAsset
            if (totalAssetsNeeded != null) {
                if (requirement.vehicleType != VehicleType.FIRE_TRUCK_LADDER) {
                    totalAssetsNeeded -= vehicle.currentNumberOfAssets
                    // if the vehicle is a firetruck with ladder.
                } else if (vehicle.currentNumberOfAssets >= totalAssetsNeeded) {
                    requirement.amountOfAsset = 0
                    requirement.numberOfVehicles--
                    return true
                }
                // if ladder is too short.
                else {
                    return false
                }
                totalAssetsNeeded -= vehicle.currentNumberOfAssets
            }
            requirement.amountOfAsset = totalAssetsNeeded
            requirement.numberOfVehicles--
            return true
        }
        return false
    }

    /**
     * updates the current requirements of the emergency.
     * @param requirements The requirements to be fulfilled.
     * @param vehicle The vehicles to check.
     * */
    private fun fulfillAndUpdateEmergencyRequirements(
        requirements: List<EmergencyRequirement>,
        vehicle: Vehicle
    ) {
        for (requirement in requirements.filter { it.vehicleType == vehicle.vehicleType }) {
            if (requirement.amountOfAsset == null) {
                requirement.numberOfVehicles--
            } else {
                if (requirement.vehicleType != VehicleType.FIRE_TRUCK_LADDER) {
                    requirement.amountOfAsset = requirement.amountOfAsset!! - vehicle.currentNumberOfAssets
                    requirement.numberOfVehicles--
                } else {
                    if (vehicle.currentNumberOfAssets >= requirement.amountOfAsset!!) {
                        requirement.amountOfAsset = 0
                        requirement.numberOfVehicles--
                        return
                    }
                }
            }
        }
    }

    /**
     * removes fulfilled requirements from the emergency.
     * */
    private fun removedFulfilledRequirements(emergency: Emergency) {
        val currentRequirements = emergency.currentRequirements
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
