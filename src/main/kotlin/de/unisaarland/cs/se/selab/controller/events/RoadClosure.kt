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
        val currentEdge: Edge = model.graph.getEdge(source!!, target!!)
        currentEdge.closed = true
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val currentEdge: Edge = model.graph.getEdge(source!!, target!!)
        currentEdge.closed = false
    }
}
