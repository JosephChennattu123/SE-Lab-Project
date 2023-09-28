package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model

/**@param id
 * @param start
 * @param duration
 * @param sourceId
 * @param targetId
 * @param factor
 */
class TrafficJam(id: Int, start: Int, duration: Int, sourceId: Int, targetId: Int, factor: Int) :
    RoadEvent(id, EventType.TRAFFIC_JAM, start, duration) {
    init {
        this.source = sourceId
        this.target = targetId
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
