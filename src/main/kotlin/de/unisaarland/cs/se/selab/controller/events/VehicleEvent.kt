package de.unisaarland.cs.se.selab.controller.events

class VehicleEvent(val vehicleId: Int, id: Int, start: Int, duration: Int) :
    Event(id, EventType.VEHICLE_UNAVAILABLE, start, duration) {

    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }
}
