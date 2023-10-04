package de.unisaarland.cs.se.selab.model.vehicle

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

    override fun setBusy(): Boolean {
        busyTicks = (waterCapacity as Int - waterLevel as Int) / THREE_HUNDRED
        return busyTicks != 0
    }

    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        if (waterCapacity != null) {
            val returnAmount = amount - (waterCapacity - waterLevel as Int)
            return if (returnAmount < 0) {
                waterLevel = waterCapacity + returnAmount
                0
            } else {
                waterLevel = waterCapacity
                returnAmount
            }
        }
        return amount
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
