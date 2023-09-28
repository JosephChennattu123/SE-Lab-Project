package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge

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
        require(source != null && target != null && factor != null) { "Source,Target or Factor should not be null" }
        val currentEdge: Edge = model.graph.getEdge(source as Int, target as Int) as Edge
        currentEdge.properties.factor = this.factor as Int
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val currentEdge: Edge = model.graph.getEdge(source as Int, target as Int) as Edge
        currentEdge.properties.factor = BASE_FACTOR
    }
}
const val BASE_FACTOR = 1
