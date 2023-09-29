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
    init {
        this.factor = factor
    }

    /** override fun applyEffect(model: Model) {
     for (currentEdge in model.graph.getEdges()) {
     if (roadTypes.contains(currentEdge.properties.roadType)) {
     if (currentEdge.activeEventId == null) {
     requireNotNull(factor) { "Factor should not be null" }
     currentEdge.properties.factor = factor as Int
     currentEdge.activeEventId = id
     if (!model.currentEvents.contains(id)) {
     model.currentEvents.add(id)
     }
     if (model.roadToPostponedEvents[currentEdge.edgeId] != null &&
     (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList).isNotEmpty()
     ) {
     (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList<Int>).remove(id)
     }
     } else {
     status = EventStatus.RUSH_HOUR
     putInPostponeLists(model.roadToPostponedEvents, currentEdge)
     }
     }
     }
     }
     */
    override fun applyEffect(model: Model) {
        model.graph.getEdges().forEach { currentEdge ->
            if (roadTypes.contains(currentEdge.properties.roadType)) {
                handleEventOnEdge(currentEdge, model)
            }
        }
    }

    private fun handleEventOnEdge(currentEdge: Edge, model: Model) {
        if (currentEdge.activeEventId == null) {
            applyEventToEdge(currentEdge, model)
        } else {
            status = EventStatus.RUSH_HOUR
            putInPostponeLists(model.roadToPostponedEvents, currentEdge)
        }
    }

    private fun applyEventToEdge(currentEdge: Edge, model: Model) {
        requireNotNull(factor) { "Factor should not be null" }
        currentEdge.properties.factor = factor as Int
        currentEdge.activeEventId = id

        if (id !in model.currentEvents) {
            model.currentEvents.add(id)
            Logger.logEventStatus(id, true)
        }

        model.roadToPostponedEvents[currentEdge.edgeId]?.let { postponedEventsList ->
            if (postponedEventsList.isNotEmpty()) {
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
                if (currentEdge.properties.roadType == currentPrimaryType) {
                    currentEdge.properties.factor = BASE_FACTOR
                    currentEdge.activeEventId = null
                    model.currentEvents.remove(id)
                    status = EventStatus.FINISHED
                    (model.roadToPostponedEvents[currentEdge.edgeId] as MutableList<Int>).remove(id)
                    Logger.logEventStatus(id, false)
                }
            }
        }
    }
}
