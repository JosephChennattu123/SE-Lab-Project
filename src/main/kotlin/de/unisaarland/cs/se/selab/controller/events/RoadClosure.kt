package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.util.Logger

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
        val currentEdge1: Edge = model.graph.getEdge(source as Int, target as Int)
        var currentEdge2: Edge? = null
        if (currentEdge1.properties.secondaryType != SecondaryType.ONE_WAY) {
            currentEdge2 = model.graph.getEdge(source as Int, target as Int)
        }
        if (model.roadToPostponedEvents[currentEdge1.edgeId] == null ||
            !(model.roadToPostponedEvents[currentEdge1.edgeId] as MutableList).contains(id)
        ) {
            Logger.logEventStatus(id, true)
            model.eventOccurred = true
        }
        if (currentEdge1.activeEventId == null && currentEdge1.activeEmergencyId == null) {
            currentEdge1.closed = true
            if (currentEdge2 != null) {
                currentEdge2.closed = true
            }

            // model.triggeredEventIds.add(id)
            if (model.roadToPostponedEvents[currentEdge1.edgeId] != null &&
                (model.roadToPostponedEvents[currentEdge1.edgeId] as MutableList).contains(id)
            ) {
                (model.roadToPostponedEvents[currentEdge1.edgeId] as MutableList<Int>).remove(id)
            }
            status = EventStatus.ACTIVE
        } else {
            status = EventStatus.SCHEDULED
            putInPostponeLists(model.roadToPostponedEvents, currentEdge1)
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val currentEdge1: Edge = model.graph.getEdge(source as Int, target as Int)
        var currentEdge2: Edge? = null
        if (currentEdge1.properties.secondaryType != SecondaryType.ONE_WAY) {
            currentEdge2 = model.graph.getEdge(target as Int, source as Int)
        }
        currentEdge1.closed = false
        if (currentEdge2 != null) {
            currentEdge2.closed = false
        }
        // model.triggeredEventIds.remove(id)
        model.eventOccurred = true
        Logger.logEventStatus(id, false)
    }
}
