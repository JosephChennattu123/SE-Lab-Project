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
                listOf(EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, AssetType.WATER, 2, WATER1200))
            }

            2 -> {
                listOf(
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, AssetType.WATER, VEHICLECOUNT4, WATER3000),
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_LADDER, AssetType.LADDER, 1, LADDERLENGTH30),
                    EmergencyRequirement(VehicleType.FIREFIGHTER_TRANSPORTER, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 1, 1)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, AssetType.WATER, VEHICLECOUNT6, WATER5400),
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_LADDER, AssetType.LADDER, 2, LADDERLENGTH40),
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
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_TECHNICAL, null, VEHICLECOUNT4, null),
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
                    EmergencyRequirement(VehicleType.POLICE_CAR, AssetType.CRIMINAL, VEHICLECOUNT4, CRIMINALCAPACITY4),
                    EmergencyRequirement(VehicleType.K9_POLICE_CAR, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, null, 1, null)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(VehicleType.POLICE_CAR, AssetType.CRIMINAL, VEHICLECOUNT6, CRIMINALCAPACITY8),
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
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, VEHICLECOUNT5, PATIENTCAPACITY5),
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
    fun allocateAssetsToEmergency(model: Model, emergency: Emergency, vehicles: MutableList<Vehicle>) {

        filterAssetsByRequirement(model, vehicles, emergency.currentRequiredAssets)

        for (v in vehicles) {
            when (v.status) {
                VehicleStatus.AT_BASE -> {
                    // If vehicle is currently at base, calculate drive time from base to emergency
                    val p = Dijkstra.getShortestPathFromVertexToEdge(
                        model.graph, model.getBaseById(v.baseID)!!.vertexID, emergency.location, v.height
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
            val oldEmergency = model.getAssignedEmergencyById(v.emergencyID!!) // ONLY FOR REALLOCATED VEHICLES
            for (req in oldEmergency.currentRequiredAssets) {
                if (req.vehicleType == v.vehicleType) { // find requirement that needs to be changed
                    req.numberOfVehicles++ // increase the number of current needed vehicles of this type by 1
                    // to compensate for loss of v
                    if (req.assetType != null) {
                        var originalreq: EmergencyRequirement? = null
                        for (oreq in oldEmergency!!.requiredAssets) { // find original requirement that corresponds to
                                                                    // req
                            if (oreq.vehicleType == v.vehicleType) {
                                originalreq = oreq
                            }
                        }
                        req.amountOfAsset = originalreq!!.amountOfAsset!!
                        for (v2 in model.getVehiclesByIds(oldEmergency.assignedVehicleIDs)) {
                            if (v2.vehicleType == v.vehicleType) { // go through all vehicles of the same type
                                // and add up their total asset capacity
                                req.amountOfAsset = req.amountOfAsset!! - v2.maxAssetCapacity
                            } // req.amountOfAsset is now the actual missing amount of asset to fulfill the requirement
                        }
                    }
                }
            }
            var newReq: EmergencyRequirement
            oldEmergency.currentRequiredAssets.add(newReq)
            emergency.addAsset(v)
        }
    }

    /**
     * modifies the parameter lists to contain the optimal selection of concrete vehicles to be assigned to an emergency
     * @param vehicles List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByOptimalSolution(
        vehicles: MutableList<Vehicle>, requirements: MutableList<EmergencyRequirement>
    ) {
        // TODO
    }

    /**
     * modifies the list of Vehicles to contain vehicles that can be assigned to an emergency
     * @param vehiclesToCheck List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByRequirement(
        model: Model, vehiclesToCheck: MutableList<Vehicle>, requirements: List<EmergencyRequirement>
    ) {
        for (v in vehiclesToCheck) {
            var found = false
            for (r in requirements) {
                if (v.vehicleType == r.vehicleType && v.staffCapacity <= model.getBaseById(v.baseID)!!.currStaff) {
                    // if vehicle is needed and base has enough staff
                    found = true
                }
            }
            if (!found) { // if v does not fit any of the requirements, remove it from the list
                vehiclesToCheck.remove(v)
            }
        }
    }
}
