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
    override fun setBusy(): Boolean {
        if (vehicleType == VehicleType.FIRE_TRUCK_WATER) {
            val maxWaterLevel = maxAssetCapacity ?: error("max water capacity is null")
            val currentWaterLevel = currentNumberOfAssets ?: error("current water level is null")
            return if (currentNumberOfAssets != maxWaterLevel) {
                busyTicks = (maxWaterLevel - currentWaterLevel) / WATER_REFILL_RATE
                true
            } else {
                false
            }
        }
        return false
    }

    /**
     * In the case of Water_Firetrucks decreases the amount of water
     * by amount and returns the amount - waterLevel.
     * Otherwise, returns 0.
     * */
    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        if (vehicleType == VehicleType.FIRE_TRUCK_WATER) {
            val water = currentNumberOfAssets ?: error("currentNumberOfAssets is null")
            val remainingAmount = amount - water
            currentNumberOfAssets = if (water <= amount) 0 else water - amount
            return remainingAmount
        }
        return 0
    }

    /** resets vehicle assets possessed to zero */
    override fun resetAfterBusy() {
        currentNumberOfAssets = maxAssetCapacity
    }
}

const val WATER_REFILL_RATE: Int = 300
