package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model

/**@param id
 * @param start
 * @param duration
 * @param sourceId
 * @param targetId
 * @param factor
 * @param oneway
 * */
class ConstructionSite(
    id: Int,
    start: Int,
    duration: Int,
    sourceId: Int,
    targetId: Int,
    factor: Int,
    val oneway: Boolean
) :
    RoadEvent(id, EventType.CONSTRUCTION_SITE, start, duration) {
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
