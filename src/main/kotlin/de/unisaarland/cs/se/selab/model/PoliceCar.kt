package de.unisaarland.cs.se.selab.model

/**@param vehicleId
 * @param baseId
 * @param height
 * @param staffCapacity
 * @param maxAssetCapacity */

class PoliceCar(
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
    var k9Present: Boolean? = null
    val criminalCapacity: Int = 0
    var criminalsPresent: Int? = 0

    /** sets vehicle status to busy if criminals present and sets busy timer */
    override fun setBusy() {
        if (criminalsPresent != null && criminalsPresent as Int > 0) {
            status = VehicleStatus.BUSY
            busyTicks = 2
        }
    }

    /**returns whatever still needs to be fulfilled inside the emergency */
    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        val returnAmount = amount - (criminalCapacity - criminalsPresent as Int)
        return if (returnAmount < 0) {
            criminalsPresent = criminalCapacity + returnAmount
            0
        } else {
            criminalsPresent = criminalCapacity
            returnAmount
        }
    }

    /** checks if vehicle is filled up with criminals */
    fun isFull(): Boolean {
        return criminalCapacity == criminalsPresent
    }

    /** resets vehicle assets possessed to zero */
    override fun resetAfterBusy() {
        criminalsPresent = 0
    }
}
