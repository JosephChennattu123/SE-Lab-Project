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
    val sourceId: Int,
    val targetId: Int,
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
        require(source != null && target != null) { "Source and Target must not be null" }
        val currentEdge: Edge = model.graph.getEdge(source as Int, target as Int) as Edge
        currentEdge.closed = oneway
        factor?.let {
            currentEdge.properties.factor = it
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        require(source != null && target != null) { "Source and Target must not be null" }
        val currentEdge: Edge = model.graph.getEdge(source as Int, target as Int) as Edge
        currentEdge.closed = false
        currentEdge.properties.factor = BASE_FACTOR
    }
}
