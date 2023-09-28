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
                        AssetType.WATER,
                        2,
                        WATER1200
                    )
                )
            }

            2 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_WATER,
                        AssetType.WATER,
                        VEHICLECOUNT4,
                        WATER3000
                    ),
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_LADDER,
                        AssetType.LADDER,
                        1,
                        LADDERLENGTH30
                    ),
                    EmergencyRequirement(VehicleType.FIREFIGHTER_TRANSPORTER, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 1, 1)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_WATER,
                        AssetType.WATER,
                        VEHICLECOUNT6,
                        WATER5400
                    ),
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_LADDER,
                        AssetType.LADDER,
                        2,
                        LADDERLENGTH40
                    ),
                    EmergencyRequirement(VehicleType.FIREFIGHTER_TRANSPORTER, null, 2, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 2, 2),
                    EmergencyRequirement(VehicleType.EMERGENCY_DOCTOR_CAR, null, 1, null)
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
    fun getMedicalRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(EmergencyRequirement(VehicleType.FIRE_TRUCK_TECHNICAL, null, 1, null))
            }

            2 -> {
                listOf(
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_TECHNICAL, null, 2, null),
                    EmergencyRequirement(VehicleType.POLICE_MOTOR_CYCLE, null, 1, null),
                    EmergencyRequirement(VehicleType.POLICE_CAR, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 1, 1)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.FIRE_TRUCK_TECHNICAL,
                        null,
                        VEHICLECOUNT4,
                        null
                    ),
                    EmergencyRequirement(VehicleType.POLICE_MOTOR_CYCLE, null, 2, null),
                    EmergencyRequirement(VehicleType.POLICE_CAR, null, VEHICLECOUNT4, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 3, 2),
                    EmergencyRequirement(VehicleType.EMERGENCY_DOCTOR_CAR, null, 1, null)
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
                listOf(EmergencyRequirement(VehicleType.POLICE_CAR, AssetType.CRIMINAL, 1, 1))
            }

            2 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        AssetType.CRIMINAL,
                        VEHICLECOUNT4,
                        CRIMINALCAPACITY4
                    ),
                    EmergencyRequirement(VehicleType.K9_POLICE_CAR, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, null, 1, null)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.POLICE_CAR,
                        AssetType.CRIMINAL,
                        VEHICLECOUNT6,
                        CRIMINALCAPACITY8
                    ),
                    EmergencyRequirement(VehicleType.POLICE_MOTOR_CYCLE, null, 2, null),
                    EmergencyRequirement(VehicleType.K9_POLICE_CAR, null, 2, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 1, 1),
                    EmergencyRequirement(VehicleType.FIREFIGHTER_TRANSPORTER, null, 1, null)
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
    fun getCrimeRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(EmergencyRequirement(VehicleType.AMBULANCE, null, 1, null))
            }

            2 -> {
                listOf(
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 2, 2),
                    EmergencyRequirement(VehicleType.EMERGENCY_DOCTOR_CAR, null, 1, null)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(
                        VehicleType.AMBULANCE,
                        AssetType.PATIENT,
                        VEHICLECOUNT5,
                        PATIENTCAPACITY5
                    ),
                    EmergencyRequirement(VehicleType.EMERGENCY_DOCTOR_CAR, null, 2, null),
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_TECHNICAL, null, 2, null)
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

        filterAssetsByRequirement(model, vehicles, emergency.currentRequiredAssets)

        for (v in vehicles) {
            when (v.status) {
                VehicleStatus.AT_BASE -> {
                    // If vehicle is currently at base, calculate drive time from base to emergency
                    val p = Dijkstra.getShortestPathFromVertexToEdge(
                        model.graph,
                        model.getBaseById(v.baseID)!!.vertexID,
                        emergency.location,
                        v.height
                    )

                    if (!emergency.canReachInTime(p.totalTicksToArrive)) {
                        vehicles.remove(v)
                    }
                }

                VehicleStatus.RETURNING, VehicleStatus.TO_EMERGENCY -> {
                    // If vehicle is currently on the road,
                    // calculate drive time from their precise position on the edge to emergency

                    val pt = v.positionTracker
                    val p = Dijkstra.getShortestPathFromEdgeToEdge(
                        model.graph,
                        pt.path.vertexPath[pt.currentVertexIndex],
                        pt.path.vertexPath[pt.currentVertexIndex + 1],
                        pt.positionOnEdge,
                        emergency.location,
                        v.height
                    )
                    if (!emergency.canReachInTime(p.totalTicksToArrive)) {
                        vehicles.remove(v)
                    }
                }

                else -> {}
            }

        }
        filterAssetsByOptimalSolution(vehicles, emergency.currentRequiredAssets)
        for (v in vehicles) {
            if (v) { // if v will be reallocated
                val oldEmergency = model.getAssignedEmergencyById(v.emergencyID!!)
                var req: EmergencyRequirement
                if (oldEmergency!!.currentRequiredAssets.any { it.vehicleType == v.vehicleType }) { // if old emergency had
                    // requirement of type v.VehicleType
                    req =
                        oldEmergency.currentRequiredAssets.first { it.vehicleType == v.vehicleType }
                    // find requirement that needs to be changed

                    req.numberOfVehicles++ // increase the number of current needed vehicles of this type by 1
                } else {
                    req = EmergencyRequirement(v.vehicleType, null, 1, null)
                    oldEmergency.currentRequiredAssets.add(req)
                }

                if (req.assetType != null) {
                    val originalReq =
                        oldEmergency.requiredAssets.first { it.vehicleType == v.vehicleType } // get original requirement
                    req.amountOfAsset = originalReq.amountOfAsset!!
                    for (v2 in model.getVehiclesByIds(oldEmergency.assignedVehicleIDs)) {
                        if (v2.vehicleType == v.vehicleType) { // go through all vehicles of same type as v
                            // and add up their total asset capacity (v should not be part of this list anymore)
                            req.amountOfAsset = req.amountOfAsset!! - v2.maxAssetCapacity
                        } // req.amountOfAsset is now the actual missing amount of asset to fulfill the requirement
                    }
                }
                var newReq: EmergencyRequirement
                oldEmergency.currentRequiredAssets.add(newReq)
                emergency.addAsset(v)
            }
        }
    }

    /**
     * modifies the parameter lists to contain the optimal selection of concrete vehicles to be assigned to an emergency
     * @param vehicles List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByOptimalSolution(
        vehicles: MutableList<Vehicle>, requirements: MutableList<EmergencyRequirement>
    ) {
        vehicles
        requirements
        TODO()
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
        for (v in vehiclesToCheck) {
            if (!requirements.any { v.vehicleType == it.vehicleType
                        && v.staffCapacity <= model.getBaseById(v.baseID)!!.currStaff })
            // if v does not fit any of the requirements, remove it from the list
                vehiclesToCheck.remove(v)
        }
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
                    .map { it.vehicleID }.contains(it1) })
        emergency.currentRequiredAssets.clear()
        fulfillRequirementsOfEmergency(emergency, remainingVehicles)
        // remove the vehicles from the emergency.
        for (vehicle in vehiclesToBeReallocated){
            emergency.removedAssignedVehicle(vehicle)
            vehicle.emergencyID = null
            vehicle.status = VehicleStatus.AT_BASE
        }
    }


    /**
     * Updates the current requirements of the emergency.
     * @param emergency The emergency to update.
     * @param vehicles The list of new vehicles to be assigned to the emergency.
     * */
    private fun fulfillRequirementsOfEmergency(emergency: Emergency,vehicles: List<Vehicle>){
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
}
