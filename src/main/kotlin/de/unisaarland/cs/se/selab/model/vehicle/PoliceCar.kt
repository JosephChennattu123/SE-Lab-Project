package de.unisaarland.cs.se.selab.model.vehicle

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

    /** sets vehicle status to busy if criminals present and sets busy timer */
    override fun setBusy(): Boolean {
        if (vehicleType == VehicleType.POLICE_CAR) {
            val maxCriminalCapacity = maxAssetCapacity ?: error("criminalCapacity is null")
            return if (currentNumberOfAssets != maxCriminalCapacity) {
                busyTicks = 2
                true
            } else {
                false
            }
        }
        return false
    }

    /**returns whatever still needs to be fulfilled inside the emergency */
    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        if (vehicleType == VehicleType.POLICE_CAR) {
            val criminalSpaces = currentNumberOfAssets ?: error("police car criminal capacity is null")
            val remainingAmount = amount - criminalSpaces
            currentNumberOfAssets = if (criminalSpaces <= amount) 0 else criminalSpaces - amount
            return remainingAmount
        }
        return 0
    }

    /** resets vehicle assets possessed to zero */
    override fun resetAfterBusy() {
        currentNumberOfAssets = maxAssetCapacity
    }
}
