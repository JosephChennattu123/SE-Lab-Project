package de.unisaarland.cs.se.selab.model

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
    private var busyTicks: Int = 0
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

    abstract fun handleEmergency(amount: Int): Int

    fun setNewPath(): Boolean {
        TODO()
    }

    fun setAtBase(): Boolean {
        TODO()
    }

    /**
     * Decrease busyTicks, if status is Busy
     * @return true: busyTicks == 0
     */
    fun decreaseBusyTicks(): Boolean {
        assert(status == VehicleStatus.BUSY && busyTicks != 0)
        busyTicks -= 1
        if (busyTicks == 0) return true
        return false
    }

    fun getCurrentVertexID(): Int {
        TODO()
    }

    fun getNextVertexID(): Int? {
        TODO()
    }

    fun getDistanceOnEdge(): Int {
        TODO()
    }

    open fun setBusy() {}
}
