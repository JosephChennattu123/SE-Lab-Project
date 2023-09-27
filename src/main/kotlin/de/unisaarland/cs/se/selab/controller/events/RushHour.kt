package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.map.PrimaryType

class RushHour(id: Int, start: Int, duration: Int, val roadTypes: List<PrimaryType>, factor: Int) :
    RoadEvent(id, EventType.RUSH_HOUR, start, duration) {
    init {
        this.factor = factor
    }

    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }
}
