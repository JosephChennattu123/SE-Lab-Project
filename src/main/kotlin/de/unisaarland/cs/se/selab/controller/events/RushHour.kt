package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.map.PrimaryType

class RushHour(id: Int, start: Int, duration: Int, roadTypes: List<PrimaryType>, factor: Int) :
    RoadEvent(id, EventType.RUSH_HOUR, duration) {
    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }
}
