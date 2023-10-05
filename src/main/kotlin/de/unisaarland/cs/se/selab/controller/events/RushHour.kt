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

    /**TODO(): add comment.*/
    override fun applyEffect(model: Model) {
        if (!model.currentEvents.contains(id)) {
            var checksIfRushHourIsListedSomewhere = 0
            for (eventList in model.roadToPostponedEvents.values) {
                if (eventList.contains(id)) {
                    checksIfRushHourIsListedSomewhere++
                }
            }
            if (checksIfRushHourIsListedSomewhere == 0) {
                Logger.logEventStatus(id, true)
                model.eventOccurred = true
            }
        }
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
            // Logger.logEventStatus(id, true)
        }

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
