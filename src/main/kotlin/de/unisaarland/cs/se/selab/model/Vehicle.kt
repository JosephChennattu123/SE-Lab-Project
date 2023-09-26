package de.unisaarland.cs.se.selab.model

import de.unisaarland.cs.se.selab.util.Logger

abstract class Vehicle(
    val vehicleID: Int,
    val baseID : Int,
    val vehicleType : VehicleType,
    val height : Int,
    val staffCapacity : Int,
    val maxAssetCapacity : Int) {

    var emergencyID : Int? = null
    var status: VehicleStatus = VehicleStatus.AT_BASE
    var isUnavailable : Boolean = false
    var activeEventID : Int? = null
    private var busyTicks : Int = 0
    var positionTracker : PositionTracker = PositionTracker()

    fun driveUpdate(): Unit {
        positionTracker.updatePosition()
        if (positionTracker.destinationReached()) {
//            Logger.logAssetArrived(vehicleID, positionTracker.current)
//            if ()
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
}
