package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.util.Logger

/**
 * @param id
 * @param start
 * @param duration
 * @param roadTypes
 * @param factor
 * */
class RushHour(id: Int, start: Int, duration: Int, val roadTypes: List<PrimaryType>, factor: Int) :
    RoadEvent(id, EventType.RUSH_HOUR, start, duration) {
    private var appliedOnRoad = false

    init {
        this.factor = factor
    }

    /**TODO(): add comment.*/
    override fun applyEffect(model: Model) {
        // check if event can be triggered.
        if (status == EventStatus.NOT_SCHEDULED) {
            Logger.logEventStatus(id, true)
            status = EventStatus.SCHEDULED
            // status = EventStatus.ACTIVE
        }
        // check if event can be activated.
        model.graph.getEdges().forEach { currentEdge ->
            if (roadTypes.contains(currentEdge.properties.roadType)) {
                handleEventOnEdge(currentEdge, model)
            }
        }
        //
        if (appliedOnRoad) {
            model.eventOccurred = true
            status = EventStatus.ACTIVE
            appliedOnRoad = false
        }
    }

    private fun handleEventOnEdge(currentEdge: Edge, model: Model) {
        if (currentEdge.activeEventId == null) {
            applyEventToEdge(currentEdge, model)
            appliedOnRoad = true
        } else if (currentEdge.activeEventId != id) {
            putInPostponeLists(model.roadToPostponedEvents, currentEdge)
        }
    }

    private fun applyEventToEdge(currentEdge: Edge, model: Model) {
        requireNotNull(factor) { "Factor should not be null" }
        currentEdge.properties.factor = factor as Int
        currentEdge.activeEventId = id

        model.roadToPostponedEvents[currentEdge.edgeId]?.let { postponedEventsList ->
            if (postponedEventsList.contains(id)) {
                postponedEventsList.remove(id)
            }
        }
    }

    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        for (currentEdge in model.graph.getEdges()) {
            for (currentPrimaryType in roadTypes) {
                if (currentEdge.properties.roadType == currentPrimaryType &&
                    currentEdge.activeEventId == id
                ) {
                    currentEdge.properties.factor = BASE_FACTOR
                    currentEdge.activeEventId = null
                }
                if (model.roadToPostponedEvents[currentEdge.edgeId] != null &&
                    (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList<Int>).contains(id)
                ) {
                    (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList<Int>).remove(id)
                }
            }
        }
        model.eventOccurred = true
        status = EventStatus.FINISHED
        Logger.logEventStatus(id, false)
    }
}
