package de.unisaarland.cs.se.selab.model
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
    val maxAssetCapacity: Int
) {
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
        positionTracker.updatePosition()
        if (positionTracker.destinationReached()) {
            val destinationVertexID = positionTracker.getDestination()
//            Logger.logAssetArrived(vehicleID, destinationVertexID)
            if (destinationVertexID == baseID) setBusy()
        }
    }

    /**
     * used to handle emergencies and returns the amount of "criminals"/"water" that still needs to
     * be handled
     */
    abstract fun handleEmergency(amount: Int): Int

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
    fun getCurrentVertexID(): Int {
        return positionTracker.getCurrentVertex()
    }

    /** returns the next vertex about to be reached */
    fun getNextVertexID(): Int {
        return positionTracker.getNextVertex()
    }

    /** returns the current distance on edge */
    fun getDistanceOnEdge(): Int {
        return positionTracker.positionOnEdge
    }

    /** sets the status of vehicle to busy and sets busy timer */
    open fun setBusy() {}

    /** resets vehicle properties after busyTimer is 0 */
    open fun resetAfterBusy() {}
}
