package de.unisaarland.cs.se.selab.controller.events

class RoadClosure(id: Int, start: Int, duration: Int, sourceId: Int, targetId: Int) :
    RoadEvent(id, EventType.ROAD_CLOSURE, start, duration) {
    init {
        this.source = sourceId
        this.target = targetId
    }

    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }
}
