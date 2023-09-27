package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge

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
        val currentEdge: Edge = model.graph.getEdge(source!!, target!!)
        currentEdge.closed = true
        if (this.factor != null) {
            currentEdge.properties.factor = this.factor!!
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val currentEdge: Edge = model.graph.getEdge(source!!, target!!)
        currentEdge.closed = false
        currentEdge.properties.factor = BASE_FACTOR
    }
}
