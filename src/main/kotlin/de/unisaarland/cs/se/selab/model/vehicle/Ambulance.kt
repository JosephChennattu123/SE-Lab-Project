package de.unisaarland.cs.se.selab.model.vehicle

/** @param vehicleId
 * @param baseId
 * @param height
 * @param staffCapacity
 * @param maxAssetCapacity */
class Ambulance(
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

    /**
     * for this vehicle currentNumberOfAssets represents the number of free spaces for patients.
     * */
    override fun setBusy(): Boolean {
        if (currentNumberOfAssets == 0) {
            busyTicks = 1
            return true
        }
        return false
    }

    /** resets vehicle assets possessed to starting state. */
    override fun resetAfterBusy() {
        currentNumberOfAssets = maxAssetCapacity
    }
}
