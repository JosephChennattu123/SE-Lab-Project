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
        Logger.logStatistics(
            model.emergencies.filter {
                it.key < (
                    model.maxTick
                        ?: throw IllegalArgumentException("How could the maxTick be null?!")
                    )
            }.values.size,
            model.assignedEmergencies.size
        )
    }
}
