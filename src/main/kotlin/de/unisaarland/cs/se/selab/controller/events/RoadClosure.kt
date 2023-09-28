package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge

/** @param id
 * @param start
 * @param duration
 * @param sourceId
 * @param targetId
 */

class RoadClosure(id: Int, start: Int, duration: Int, sourceId: Int, targetId: Int) :
    RoadEvent(id, EventType.ROAD_CLOSURE, start, duration) {
    init {
        this.source = sourceId
        this.target = targetId
    }

    override fun applyEffect(model: Model) {
        require(source != null && target != null) { "source or target should not be null" }
        val currentEdge1: Edge? = model.graph.getEdge(source as Int, target as Int)
        val currentEdge2: Edge? = model.graph.getEdge(target as Int, source as Int)
        if (currentEdge1 != null) {
            currentEdge1.closed = true
        }
        if (currentEdge2 != null) {
            currentEdge2.closed = true
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val currentEdge1: Edge? = model.graph.getEdge(source as Int, target as Int)
        val currentEdge2: Edge? = model.graph.getEdge(target as Int, source as Int)
        if (currentEdge1 != null) {
            currentEdge1.closed = false
        }
        if (currentEdge2 != null) {
            currentEdge2.closed = false
        }
    }
}
