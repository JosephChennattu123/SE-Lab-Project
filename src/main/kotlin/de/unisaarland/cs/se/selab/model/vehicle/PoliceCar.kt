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
    maxAssetCapacity: Int?
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

    /** resets vehicle assets possessed to zero */
    override fun resetAfterBusy() {
        currentNumberOfAssets = maxAssetCapacity
    }
}
