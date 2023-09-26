package de.unisaarland.cs.se.selab.model

open class Vehicle(
    val vehicleID: Int,
    val baseID : Int,
    val vehicleType : VehicleType,
    var height : Int,
    var capacity : Int) {

    var emergencyID : Int? = null
    var status: VehicleStatus = VehicleStatus.AT_BASE
    var isUnavailable : Boolean = false
    var activeEventID : Int? = null
    var busyTicks : Int = 0
    var positionTracker : PositionTracker = PositionTracker()

    fun driveUpdate(): Unit {
        //TODO
    }

    open fun handleEmergency(amount: Int): Int {
        //TODO
        return 0
    }

    fun setNewPath(): Boolean {
        //TODO
        return false
    }

    fun setAtBase(): Boolean {
        //TODO
        return false
    }

    fun decreaseBusyTicks() {
        //TODO
    }

    fun getCurrentVertexID(): Int {
        //TODO
        return 0
    }

    fun getNextVertexID(): Int? {
        //TODO
        return null
    }

    fun getDistanceOnEdge(): Int {
        //TODO
        return 0
    }
}
