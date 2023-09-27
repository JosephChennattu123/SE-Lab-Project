package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.PrimaryType
/**
 * @param id
 * @param start
 * @param duration
 * @param roadTypes
 * @param factor
 * */
class RushHour(id: Int, start: Int, duration: Int, val roadTypes: List<PrimaryType>, factor: Int) :
    RoadEvent(id, EventType.RUSH_HOUR, start, duration) {
    init {
        this.factor = factor
    }

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
