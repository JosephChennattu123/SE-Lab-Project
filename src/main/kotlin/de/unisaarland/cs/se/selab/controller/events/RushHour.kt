package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.PrimaryType

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

    override fun applyEffect(model: Model) {
        for (currentEdge in model.graph.getEdges()) {
            for (currentPrimaryType in roadTypes) {
                if (currentEdge.properties.roadType == currentPrimaryType) {
                    currentEdge.properties.factor = factor!!
                }
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
                }
            }
        }
    }
}
