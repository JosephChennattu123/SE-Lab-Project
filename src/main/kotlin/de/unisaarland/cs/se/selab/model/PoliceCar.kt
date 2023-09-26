package de.unisaarland.cs.se.selab.model


class PoliceCar(vehicleId: Int, baseId: Int,vehicleType: VehicleType , height: Int,
                staffCapacity: Int, maxAssetCapacity: Int) : Vehicle(vehicleId,baseId,
    vehicleType,height,staffCapacity,maxAssetCapacity) {

    var criminalsPresent: Int? = null

    override fun setBusy(): Boolean{
        TODO()
    }

    override fun handleEmergency(amount: Int): Int {
        TODO()
    }

    fun isFull(): Boolean {
        TODO()
    }
}
