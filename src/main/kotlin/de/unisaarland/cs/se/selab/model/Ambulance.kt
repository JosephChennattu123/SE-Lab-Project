package de.unisaarland.cs.se.selab.model

class Ambulance(vehicleId: Int, baseId: Int, vehicleType: VehicleType, height: Int,
                staffCapacity: Int, maxAssetCapacity: Int)
    : Vehicle(vehicleId,baseId,
    vehicleType,height,staffCapacity,maxAssetCapacity) {

    var patientPresent : Boolean? = null
    var doctorPresent : Boolean? = null

    override fun setBusy() {
        TODO()
    }

    override fun handleEmergency(amount: Int): Int {
        TODO()
    }


    fun isFull(): Boolean {
        TODO()
    }
}
