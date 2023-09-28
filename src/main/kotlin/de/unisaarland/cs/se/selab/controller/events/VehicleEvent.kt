package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model

/** @param vehicleId
 * @param id
 * @param start
 * @param duration
 */
class VehicleEvent(val vehicleId: Int, id: Int, start: Int, duration: Int) :
    Event(id, EventType.VEHICLE_UNAVAILABLE, start, duration) {
    override fun applyEffect(model: Model) {
        TODO("Not implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }

    override fun removeEffect(model: Model) {
        TODO("Not yet implemented")
    }
}
