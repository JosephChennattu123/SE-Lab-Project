package de.unisaarland.cs.se.selab.controller.events

class VehicleEvent(val vehicleId: Int, id: Int, duration: Int) : Event(id, EventType.VEHICLE_UNAVAILABLE, duration) {

    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }
}