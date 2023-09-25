package de.unisaarland.cs.se.selab.controller.events

class TrafficJam(id: Int, start: Int, duration: Int, sourceId: Int, targetId: Int, factor: Int) :
    RoadEvent(id, EventType.TRAFFIC_JAM, duration) {
    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }
}
