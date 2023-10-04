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

/** decreases number of free spaces by one and returns the amount - 1.*/
    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        if (currentNumberOfAssets != null && currentNumberOfAssets == 1) {
            currentNumberOfAssets?.minus(1)
            return amount - 1
        }
        return amount
    }

    /** resets vehicle assets possessed to starting state. */
    override fun resetAfterBusy() {
        currentNumberOfAssets = maxAssetCapacity
    }
}
