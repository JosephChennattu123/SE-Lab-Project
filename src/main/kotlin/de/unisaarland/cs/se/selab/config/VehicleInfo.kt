package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.VehicleType

/**
 * Collects the info to construct vehicles
 *
 * @param id the id of the vehicle
 * @param baseId the id of the base this vehicle belongs to
 * @param vehicleType the type of the vehicle
 * @param staffCapacity the amount of staff that fit into that vehicle
 * @param vehicleHeight the height of the vehicle
 */
data class VehicleInfo(
    val id: Int,
    val baseId: Int,
    val vehicleType: VehicleType,
    val staffCapacity: Int,
    val vehicleHeight: Int,
    val criminalCapacity: Int?,
    val ladderLength: Int?,
    val waterCapacity: Int?
)
