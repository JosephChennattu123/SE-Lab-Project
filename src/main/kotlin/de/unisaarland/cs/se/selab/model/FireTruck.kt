package de.unisaarland.cs.se.selab.model

/** @param vehicleId
 * @param baseId
 * @param height
 * @param staffCapacity
 * @param maxAssetCapacity */
class FireTruck(
    vehicleId: Int,
    baseId: Int,
    vehicleType: VehicleType,
    height: Int,
    staffCapacity: Int,
    maxAssetCapacity: Int
) : Vehicle(
    vehicleId,
    baseId,
    vehicleType,
    height,
    staffCapacity,
    maxAssetCapacity
) {

    var waterLevel: Int? = null
    val ladderLength: Int? = null
    val waterCapacity: Int? = null

    override fun setBusy() {
        busyTicks = (waterCapacity as Int - waterLevel as Int) / THREE_HUNDRED
        if (busyTicks != 0) {
            status = VehicleStatus.BUSY
        }
        status = VehicleStatus.AT_BASE
    }

    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        val returnAmount = amount - (waterCapacity as Int - waterLevel as Int)
        return if (returnAmount < 0) {
            waterLevel = waterCapacity + returnAmount
            0
        } else {
            waterLevel = waterCapacity
            returnAmount
        }
    }

    /** checks if vehicle is filled up with water */
    fun isFull(): Boolean {
        return waterCapacity == waterLevel
    }

    /** resets vehicle assets possessed to zero */
    override fun resetAfterBusy() {
        waterLevel = waterCapacity
    }
}

const val THREE_HUNDRED: Int = 300
