package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.*

/**
 * Returns requirements for emergencies and handles the allocation of assets to emergencies */
object AssetManager {
    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements
     * */
    fun getFireRequirements(severity: Int): List<EmergencyRequirement> {
        return when (severity) {
            1 -> {
                listOf(EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, AssetType.WATER, 2, 1200))
            }

            2 -> {
                listOf(
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, AssetType.WATER, 4, 3000),
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_LADDER, AssetType.LADDER, 1, 30),
                    EmergencyRequirement(VehicleType.FIREFIGHTER_TRANSPORTER, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 1, 1)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_WATER, AssetType.WATER, 6, 5400),
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_LADDER, AssetType.LADDER, 2, 40),
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
                    EmergencyRequirement(VehicleType.FIRE_TRUCK_TECHNICAL, null, 4, null),
                    EmergencyRequirement(VehicleType.POLICE_MOTOR_CYCLE, null, 2, null),
                    EmergencyRequirement(VehicleType.POLICE_CAR, null, 4, null),
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
                    EmergencyRequirement(VehicleType.POLICE_CAR, AssetType.CRIMINAL, 4, 4),
                    EmergencyRequirement(VehicleType.K9_POLICE_CAR, null, 1, null),
                    EmergencyRequirement(VehicleType.AMBULANCE, null, 1, null)
                )
            }

            3 -> {
                listOf(
                    EmergencyRequirement(VehicleType.POLICE_CAR, AssetType.CRIMINAL, 6, 8),
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
                    EmergencyRequirement(VehicleType.AMBULANCE, AssetType.PATIENT, 5, 5),
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
        filterAssetsByRequirement(vehicles, emergency.currentRequiredAssets)
        for (v in vehicles) {
            when (v.status) {
                VehicleStatus.AT_BASE -> { // If vehicle is currently at base, calculate drive time from base to emergency
                    val p = Dijkstra.getShortestPathFromVertexToEdge(
                        model.graph,
                        model.getBasebyId(v.baseID).vertexID,
                        emergency.location,
                        v.height
                    )

                    if (!emergency.canReachInTime(p.totalTicksToArrive)) {
                        vehicles.remove(v)
                    }
                }

                VehicleStatus.RETURNING, VehicleStatus.TO_EMERGENCY -> { // If vehicle is currently on the road, calculate drive time from their precise position to emergency
                    val pt = v.positionTracker
                    val p = Dijkstra.getShortestPathFromEdgeToEdge(
                        model.graph, pt.path.vertexPath[pt.currentVertexIndex],
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
            emergency.addAsset(v)
        }
    }

    /**
     * modifies the parameter lists to contain the optimal selection of concrete vehicles to be assigned to an emergency
     * @param vehicles List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByOptimalSolution(
        vehicles: MutableList<Vehicle>,
        requirements: MutableList<EmergencyRequirement>
    ) {
        // TODO
    }

    /**
     * modifies the list of Vehicles to contain vehicles that can be assigned to an emergency
     * @param vehiclesToCheck List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByRequirement(
        vehiclesToCheck: MutableList<Vehicle>,
        requirements: List<EmergencyRequirement>
    ) {
        // TODO
    }
}
