package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.util.Logger

/**
 * Gather statistics phase
 * This phase gets executed once the simulation is over.
 * Collects statistics about the simulation
 */
class GatherStatistics {

    /**
     * @param model the model
     */
    fun execute(model: Model) {
        Logger.logSimulationEnded()

        val receivedEmergencies: Int = if (model.maxTick == null) {
            model.emergencies.size
        } else {
            model.emergencies.filter {
                it.value.scheduledTick < model.maxTick
            }.values.size
        }
        Logger.logStatistics(receivedEmergencies, model.assignedEmergencies.size)
    }
}
