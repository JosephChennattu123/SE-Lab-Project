package de.unisaarland.cs.se.selab.model

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

    var patientPresent: Boolean? = null
    var doctorPresent: Boolean? = null

    override fun setBusy() {
        if (patientPresent == true) status = VehicleStatus.BUSY
    }

/** Essentially handle emergency */
    override fun handleEmergency(amount: Int): Int {
        status = VehicleStatus.HANDLING
        if (patientPresent != null) {
            if (amount > 0) {
                if (patientPresent == false
                ) {
                    patientPresent = true
                }
                return amount - 1
            }
            return 0
        }
        return amount
    }

    /** checks if vehicle is filled up with patients */
    fun isFull(): Boolean {
        return if (patientPresent == null) {
            false
        } else {
            patientPresent as Boolean
        }
    }

    /** resets vehicle assets possessed to zero */
    override fun resetAfterBusy() {
        patientPresent = false
    }
}
