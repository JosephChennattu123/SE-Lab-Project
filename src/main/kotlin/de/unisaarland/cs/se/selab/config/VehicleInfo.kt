package de.unisaarland.cs.se.selab.config

/**
 * Collects the info to construct vehicles
 *
 * @param id the id of the vehicle
 * @param baseId the id of the base this vehicle belongs to
 * @param vehicleType the type of the vehicle
 * @param staffCapacity the amount of staff that fit into that vehicle
 * @param vehicleHeight the height of the vehicle
 */
class VehicleInfo(val id: Int, val baseId: Int, val vehicleType: Int, val staffCapacity: Int, val vehicleHeight: Int) {
    var criminalCapacity: Int? = null
    var ladderLenght: Int? = null
    var waterCapacity: Int? = null
}
