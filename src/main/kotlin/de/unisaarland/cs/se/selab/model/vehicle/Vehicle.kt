package de.unisaarland.cs.se.selab.model.vehicle

import de.unisaarland.cs.se.selab.util.Logger

/**@param vehicleID
 * @param baseID
 * @param vehicleType
 * @param height
 * @param staffCapacity
 * @param maxAssetCapacity*/
abstract class Vehicle(
    val vehicleID: Int,
    val baseID: Int,
    val vehicleType: VehicleType,
    val height: Int,
    val staffCapacity: Int,
    val maxAssetCapacity: Int?
) {
    var currentNumberOfAssets: Int? = maxAssetCapacity
    var emergencyID: Int? = null
    var status: VehicleStatus = VehicleStatus.AT_BASE
    var isUnavailable: Boolean = false
    var activeEventID: Int? = null
    var busyTicks: Int = 0
    var positionTracker: PositionTracker = PositionTracker()

    /**
     * update the position of vehicle, send log if it arrives, and service vehicles that require
     */
    fun driveUpdate() {
        // implemented in the update branch
        positionTracker.updatePosition()
        if (positionTracker.destinationReached()) {
            val destinationVertexID = positionTracker.getDestination()
            if (destinationVertexID != null) {
                Logger.logAssetArrived(vehicleID, destinationVertexID)
            } else {
                error("destinationVertexID is null")
            }
            status = if (status == VehicleStatus.RETURNING) {
                if (setBusy()) {
                    VehicleStatus.BUSY
                } else {
                    VehicleStatus.AT_BASE
                }
            } else {
                VehicleStatus.WAITING
            }
        }
    }

    /**
     * used to handle emergencies and returns the amount of "criminals"/"water" that still needs to
     * be handled
     */
    fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        if (vehicleType == VehicleType.FIRE_TRUCK_WATER || vehicleType == VehicleType.AMBULANCE ||
            vehicleType == VehicleType.POLICE_CAR
        ) {
            val curNum = currentNumberOfAssets as Int
            var remainingAmount = amount
            if (amount >= curNum) {
                remainingAmount -= curNum
                currentNumberOfAssets = 0
            } else {
                currentNumberOfAssets = curNum - amount
                remainingAmount = 0
            }
            return remainingAmount
        }
        return amount
    }

    /** sets new a new path. returns true if new path has to be set and false if not */
    fun setNewPath(path: Path): Boolean {
        return positionTracker.assignPath(path)
    }

    /** changes status of vehicle to TO_BASE but checks first if destinationReached */
    fun setAtBase(): Boolean {
        if (positionTracker.destinationReached()) {
            status = VehicleStatus.AT_BASE
            return true
        }
        return false
    }

    /**
     * checks if vehicle can be reallocated if it is returning or on the way to emergency
     * and is not unavailable and has some or null assets.
     * */
    fun canBeReallocated(): Boolean {
        val isStatusCorrect = status == VehicleStatus.TO_EMERGENCY || status == VehicleStatus.RETURNING
        return isStatusCorrect && !isUnavailable && (currentNumberOfAssets ?: 1) > 0
    }

    /**
     * Decrease busyTicks, if status is Busy
     * @return true: busyTicks == 0
     */
    fun decreaseBusyTicks(): Boolean {
        assert(status == VehicleStatus.BUSY && busyTicks != 0)
        busyTicks -= 1
        if (busyTicks == 0) {
            resetAfterBusy()
            return true
        }

        return false
    }

    /** returns current vertex id */
    fun getCurrentVertexID(): Int? {
        return positionTracker.getCurrentVertex()
    }

    /** returns the next vertex about to be reached */
    fun getNextVertexID(): Int? {
        return positionTracker.getNextVertex()
    }

    /** returns the current distance on edge */
    fun getDistanceOnEdge(): Int? {
        return positionTracker.positionOnEdge
    }

    /** sets the status of vehicle to busy and sets busy timer */
    abstract fun setBusy(): Boolean

    /** resets vehicle properties after busyTimer is 0 */
    abstract fun resetAfterBusy()

    /**
     * returns the id of the destination vertex.
     * */
    fun getDestination(): Int? {
        return positionTracker.getDestination()
    }
}
