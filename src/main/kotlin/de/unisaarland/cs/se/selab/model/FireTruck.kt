package de.unisaarland.cs.se.selab.model

class FireTruck(vehicleId: Int, baseId: Int,vehicleType: VehicleType , height: Int,
                staffCapacity: Int, maxAssetCapacity: Int)
    : Vehicle(vehicleId,baseId,
    vehicleType,height,staffCapacity,maxAssetCapacity) {

    var waterLevel: Int? = null
    var ladderLength: Int? = null

    fun setBusy(): Boolean {
        //todo
        return true
    }

    override fun handleEmergency(amount: Int): Int {
        //todo
        return -1
    }

    fun refill(): Unit {
        //todo

    }

    fun isFull(): Boolean {
        //todo
        return true
    }
}
