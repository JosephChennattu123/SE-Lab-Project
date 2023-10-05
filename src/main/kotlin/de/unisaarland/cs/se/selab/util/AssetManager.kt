package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyRequirement
import de.unisaarland.cs.se.selab.model.EmergencyStatus
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.vehicle.Path
import de.unisaarland.cs.se.selab.model.vehicle.Vehicle
import de.unisaarland.cs.se.selab.model.vehicle.VehicleStatus
import de.unisaarland.cs.se.selab.model.vehicle.VehicleType

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
                emptyList()
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
                emptyList()
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
                emptyList()
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
                emptyList()
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
        vehicles: MutableList<Vehicle>,
        reallocate: Boolean
    ) {
        // remove vehicles that do not fit the requirement type or do not fulfill staff requirements.
        filterAssetsByRequirement(model, vehicles, emergency.currentRequirements)

        // find vehicles that cannot reach the emergency in time.
        val vehiclesThatCannotReachInTime = mutableListOf<Vehicle>()
        val vehicleIdToPath: MutableMap<Int, Path> = mutableMapOf()
        for (vehicle in vehicles) {
            when (vehicle.status) {
                // If vehicle is currently at base, calculate drive from vertex to edge.
                VehicleStatus.AT_BASE -> {
                    val newPath = Dijkstra.getShortestPathFromVertexToEdge(
                        model.graph,
                        model.bases.getValue(vehicle.baseID).vertexID,
                        emergency.location,
                        vehicle.height
                    )

                    if (!emergency.canReachInTime(newPath.totalTicksToArrive)) {
                        vehiclesThatCannotReachInTime.add(vehicle)
                    } else {
                        vehicleIdToPath[vehicle.vehicleID] = newPath
                    }
                }

                // if vehicle is currently on the road, calculate drive time from edge to edge.
                VehicleStatus.RETURNING, VehicleStatus.TO_EMERGENCY -> {
                    val currentVertex =
                        vehicle.getCurrentVertexID() ?: error("current vertex is null")
                    val nextVertex = vehicle.getNextVertexID() ?: error("next vertex is null")
                    val distanceOnEdge =
                        vehicle.getDistanceOnEdge() ?: error("distance on edge is null")
                    vehicle.getDistanceOnEdge()
                    val newPath = Dijkstra.getShortestPathFromEdgeToEdge(
                        model.graph,
                        currentVertex,
                        nextVertex,
                        distanceOnEdge,
                        emergency.location,
                        vehicle.height
                    )
                    if (!emergency.canReachInTime(newPath.totalTicksToArrive)) {
                        vehiclesThatCannotReachInTime.add(vehicle)
                    } else {
                        vehicleIdToPath[vehicle.vehicleID] = newPath
                    }
                }

                else -> {}
            }
        }
        // remove vehicles that cannot reach the emergency in time.
        vehicles.removeAll(vehiclesThatCannotReachInTime)

        // remove vehicles that are not part of the optimal set.
        val mainBase = emergency.mainBaseID?.let { model.bases.getValue(it) }
        if (mainBase != null) {
            filterAssetsByOptimalSolution(mainBase, vehicles, emergency.currentRequirements)
        } else {
            error("Emergency has no main base.")
        }

        // assign.
        assignVehiclesAndLog(model, emergency, vehicles, vehicleIdToPath, reallocate)
    }

    private fun assignVehiclesAndLog(
        model: Model,
        emergency: Emergency,
        vehicles: List<Vehicle>,
        paths: Map<Int, Path>,
        reallocate: Boolean
    ) {
        for (vehicle in vehicles) {
            fulfillAndUpdateEmergencyRequirements(emergency.currentRequirements, vehicle)
            vehicle.setNewPath(paths.getValue(vehicle.vehicleID))
            if (reallocate) {
                // remove vehicles that are already assigned to an emergency.
                val emergencyOfReallocatedVehicle = model
                    .getAssignedEmergencyById(vehicle.emergencyID as Int) as Emergency
                removeVehicleFromEmergency(emergencyOfReallocatedVehicle, vehicle, model)
                Logger.logAssetReallocated(vehicle.vehicleID, emergency.id)
            } else {
                Logger.logAssetAllocated(
                    vehicle.vehicleID,
                    emergency.id,
                    paths.getValue(vehicle.vehicleID).totalTicksToArrive
                )
                if (vehicle.status == VehicleStatus.AT_BASE) {
                    vehicle.status = VehicleStatus.ASSIGNED
                } else {
                    vehicle.status = VehicleStatus.TO_EMERGENCY
                }
            }
            emergency.addAsset(vehicle.vehicleID)
            vehicle.emergencyID = emergency.id
        }
        // remove fulfilled requirements from the emergency.
        removedFulfilledRequirements(emergency)
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
        for (size in getAmountOfRequiredVehicles(requirements, mainBase) downTo 1) {
            val combinationsOfIds = computeCombinations(vehicleIds, size)
            val validCombinations =
                mutableListOf<List<Int>>() // combinations that fulfill the requirements
            // filter out combinations that do not fulfill the requirements
            for (combination in combinationsOfIds) {
                val subsetToCheck: List<Vehicle> =
                    idToVehicleMap.filter { combination.contains(it.key) }.values.toList()
                if (checkIfVehiclesFulfillRequirements(subsetToCheck, requirements)) {
                    validCombinations.add(combination)
                }
            }
            // if there are valid combinations,
            // remove all vehicles that are not part of the valid combinations

            if (validCombinations.isNotEmpty()) {
                val optimalSet = sortLexicallyAndReturnFirst(validCombinations)
                // remove all vehicles that are not part of the optimal set.
                vehicles.removeAll(vehicles.filter { !optimalSet.contains(it.vehicleID) })
                break
            }
        }
    }

    private fun getAmountOfRequiredVehicles(
        requirements: MutableList<EmergencyRequirement>,
        base: Base
    ): Int {
        val requirementsForBaseType =
            requirements.filter { VehicleType.getBaseType(it.vehicleType) == base.baseType }
        return requirementsForBaseType.sumOf { it.numberOfVehicles }
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
            if (vehicle.isUnavailable ||
                !requirements
                    .none {
                        vehicle.vehicleType != it.vehicleType ||
                            !isThereEnoughStaffAtBase(model.bases.getValue(vehicle.baseID), vehicle)
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
    private fun removeVehicleFromEmergency(
        emergency: Emergency,
        vehicleToBeReallocated: Vehicle,
        model: Model
    ) {
        // remove the vehicles that are reallocated from the emergency.
        emergency.assignedVehicleIDs.remove(vehicleToBeReallocated.vehicleID)
        val remainingVehicles = model
            .getVehiclesByIds(emergency.assignedVehicleIDs)

        // update status of emergency
        emergency.status = EmergencyStatus.ONGOING

        // reset the current requirements of the emergency.
        emergency.currentRequirements.clear()

        // update the current requirements of the emergency.
        refreshEmergencyRequirements(emergency, remainingVehicles)

        // remove emergency from vehicle.
        vehicleToBeReallocated.emergencyID = null
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
        vehicles: List<Vehicle>,
        requirements: List<EmergencyRequirement>
    ): Boolean {
        var requirementFulfilled = true
        val requirementsCopy = requirements.map { it.copy() }.toMutableList()
        // check if every vehicle satisfies a requirement.
        for (vehicle in vehicles) {
            if (!tryToFulfillRequirements(vehicle, requirementsCopy)) {
                requirementFulfilled = false
            }
        }
        // check if emergency blocked (vehicle amount satisfied but asset amount not satisfied).
        for (requirement in requirementsCopy) {
            val numberOfAssets = requirement.amountOfAsset
            if (numberOfAssets != null) {
                if (requirement.numberOfVehicles == 0 && numberOfAssets > 0) {
                    requirementFulfilled = false
                }
            }
        }
        return requirementFulfilled
    }

    /**
     * Checks if a single vehicle satisfies any requirement of the emergency (partially or fully).
     * successful assignments of vehicles will modify the state of requirements.
     * To be used in optimal solution for checking if a set of vehicles satisfies the requirements of an emergency.
     * @param vehicle The vehicle that will fulfill the requirement.
     * @return true if the vehicle satisfies a requirement, false otherwise.
     * */
    private fun tryToFulfillRequirements(
        vehicle: Vehicle,
        requirements: List<EmergencyRequirement>
    ): Boolean {
        var requirementFulfilled = false
        var requirementsIndex = 0
        val fittingRequirements = requirements.filter {
            it.numberOfVehicles > 0 &&
                it.vehicleType == vehicle.vehicleType
        }
        // iterate over unfulfilled requirements of the given vehicle's type.
        while (!requirementFulfilled && requirementsIndex < requirements.size) {
            val requiredType = fittingRequirements[requirementsIndex].vehicleType
            val requiredAmount = fittingRequirements[requirementsIndex].amountOfAsset
            if (requiredType == VehicleType.FIRE_TRUCK_LADDER) {
                requirementFulfilled = checkIfLadderIsLongEnough(vehicle, requiredAmount)
                fittingRequirements[requirementsIndex].amountOfAsset = 0
                fittingRequirements[requirementsIndex].numberOfVehicles--
            } else if (requiredAmount == null) {
                requirementFulfilled = true
                fittingRequirements[requirementsIndex].numberOfVehicles--
            } else {
                requirementFulfilled = true
                fittingRequirements[requirementsIndex].amountOfAsset =
                    requiredAmount - vehicle.currentNumberOfAssets as Int
            }
            requirementsIndex++
        }
        return requirementFulfilled
    }

    private fun checkIfLadderIsLongEnough(
        vehicle: Vehicle,
        requiredAmount: Int?
    ): Boolean {
        val ladderLength = vehicle.currentNumberOfAssets ?: error("ladder length is null")
        if (requiredAmount != null) {
            return ladderLength >= requiredAmount
        } else {
            error("required ladder is null")
        }
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
            } else if (requirement.vehicleType != VehicleType.FIRE_TRUCK_LADDER) {
                requirement.amountOfAsset =
                    requirement.amountOfAsset as Int - vehicle.currentNumberOfAssets as Int
                requirement.numberOfVehicles--
            } else if (vehicle.currentNumberOfAssets as Int >= requirement.amountOfAsset as Int) {
                requirement.amountOfAsset = 0
                requirement.numberOfVehicles--
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

    private fun isThereEnoughStaffAtBase(base: Base, vehicle: Vehicle): Boolean {
        val dogsNeeded = if (vehicle.vehicleType == VehicleType.K9_POLICE_CAR) 1 else 0
        val doctorsNeeded = if (vehicle.vehicleType == VehicleType.EMERGENCY_DOCTOR_CAR) 1 else 0
        val staffNeeded = vehicle.staffCapacity
        return when (base.baseType) {
            BaseType.POLICE_STATION -> {
                val dogs = base.dogs ?: error("dogs is null in Police Station")
                base.currentStaff >= staffNeeded && dogs >= dogsNeeded
            }

            BaseType.HOSPITAL -> {
                val doctors = base.doctors ?: error("doctors is null in Hospital")
                base.currentStaff >= staffNeeded && doctors >= doctorsNeeded
            }

            BaseType.FIRE_STATION -> {
                base.currentStaff >= staffNeeded
            }
        }
    }

    /**
     * Calculates lexically the smallest list of a collection of lists.
     *
     * @param combinations a list of lists. Lists should be of same size.
     * @return lexically the smallest list (sorted). Returns an empty list if [combinations] was an empty list
     */
    private fun sortLexicallyAndReturnFirst(combinations: List<List<Int>>): MutableList<Int> {
        var sortedCombinations = combinations.map { it.toSortedSet() }
        val sortedIds = sortedCombinations.flatten().toSortedSet()

        var index = 0
        while (index < sortedIds.size) {
            val indexElements: MutableList<Int> = mutableListOf()

            // get the elements of all lists at index "index"
            for (list in sortedCombinations) {
                indexElements.add(list.toList()[index])
            }

            // calculate the smallest value
            var smallest = Int.MAX_VALUE
            for (indexElement in 0 until indexElements.size) {
                val valElement = indexElements[indexElement]
                if (valElement < smallest) {
                    smallest = valElement
                }
            }

            // remove the lists that do not have the smallest element at index "index"
            sortedCombinations = sortedCombinations.filter { it.toList()[index] == smallest }

            index++
            if (index == sortedCombinations.first().size) {
                break
            }
        }
        if (sortedCombinations.isEmpty()) {
            return mutableListOf()
        }

        // return the smallest
        return sortedCombinations.first().toMutableList()
    }

    private fun computeCombinations(ids: List<Int>, size: Int): List<List<Int>> {
        if (size == 0) {
            return listOf(emptyList())
        }
        if (ids.isEmpty()) {
            return emptyList()
        }
        val head = ids.first()
        val tail = ids.drop(1)
        val withHead = computeCombinations(tail, size - 1).map { listOf(head) + it }
        val withoutHead = computeCombinations(tail, size)
        return withHead + withoutHead
    }
}
