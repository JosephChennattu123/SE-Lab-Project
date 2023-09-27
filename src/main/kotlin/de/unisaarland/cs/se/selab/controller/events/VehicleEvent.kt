package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus

/** @param vehicleId
 * @param id
 * @param start
 * @param duration
 */
class VehicleEvent(val vehicleId: Int, id: Int, start: Int, duration: Int) :
    Event(id, EventType.VEHICLE_UNAVAILABLE, start, duration) {
    override fun applyEffect(model: Model) {
        val vehicleObject: Vehicle = model.getVehicleById(vehicleId)!!
        if (vehicleObject.status == VehicleStatus.AT_BASE) {
            vehicleObject.status = VehicleStatus.UNAVAILABLE
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val vehicleObject: Vehicle = model.getVehicleById(vehicleId)!!
        if (vehicleObject.status == VehicleStatus.UNAVAILABLE) {
            vehicleObject.status = VehicleStatus.AT_BASE
        }
    }
}
