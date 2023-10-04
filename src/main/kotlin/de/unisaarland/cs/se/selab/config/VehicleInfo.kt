package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.vehicle.VehicleType

const val BASE_ID = "baseId"
const val VEHICLE_TYPE = "vehicleType"
const val STAFF_CAPACITY = "staffCapacity"
const val VEHICLE_HEIGHT = "vehicleHeight"
const val CRIMINAL_CAPACITY = "criminalCapacity"
const val LADDER_LENGTH = "ladderLength"
const val WATER_CAPACITY = "waterCapacity"

/**
 * Collects the info to construct vehicles
 *
 * @param id the id of the vehicle
 * @param baseId the id of the base this vehicle belongs to
 * @param vehicleType the type of the vehicle
 * @param staffCapacity the amount of staff that fit into that vehicle
 * @param vehicleHeight the height of the vehicle
 */
class VehicleInfo(
    id: Int,
    val baseId: Int,
    val vehicleType: VehicleType,
    val staffCapacity: Int,
    val vehicleHeight: Int,
    val criminalCapacity: Int?,
    val ladderLength: Int?,
    val waterCapacity: Int?
) : BasicInfo(id) {
    override val infoMap: Map<String, Any?> =
        mapOf(
            ID to id,
            BASE_ID to baseId,
            VEHICLE_TYPE to vehicleType,
            STAFF_CAPACITY to staffCapacity,
            VEHICLE_HEIGHT to vehicleHeight,
            CRIMINAL_CAPACITY to criminalCapacity,
            LADDER_LENGTH to ladderLength,
            WATER_CAPACITY to waterCapacity
        )
}
