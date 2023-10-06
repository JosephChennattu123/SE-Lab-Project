package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge
import de.unisaarland.cs.se.selab.util.Logger

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
        val currentEdge: Edge = model.graph.getEdge(source as Int, target as Int)
        if (model.roadToPostponedEvents[currentEdge.edgeId] == null ||
            !(model.roadToPostponedEvents[currentEdge.edgeId] as MutableList).contains(id)
        ) {
            Logger.logEventStatus(id, true)
            model.eventOccurred = true
        }
        if (currentEdge.activeEventId == null) {
            currentEdge.properties.factor = this.factor as Int
            currentEdge.activeEventId = id
            status = EventStatus.ACTIVE
            if (model.roadToPostponedEvents[currentEdge.edgeId] != null &&
                (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList).contains(id)
            ) {
                (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList<Int>).remove(id)
            }
        } else {
            status = EventStatus.SCHEDULED
            putInPostponeLists(model.roadToPostponedEvents, currentEdge)
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val currentEdge: Edge = model.graph.getEdge(source as Int, target as Int)
        currentEdge.properties.factor = BASE_FACTOR
        currentEdge.activeEventId = null
        status = EventStatus.FINISHED
        model.eventOccurred = true
        Logger.logEventStatus(id, false)
    }
}

const val BASE_FACTOR = 1
