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
        // the number of rerouted assets
        Logger.logNumberOfReroutedAssets(model.numReroutedAssets)
        // the number of received emergencies
        Logger.logNumberOfRecievedEmergencies(model.emergencies.values.size)
        // the number of still ongoing emergencies
        Logger.logNumberOfOngoingEmergencies(model.assignedEmergencies.size)
        // the number of failed emergencies
        Logger.logNumberOfFailedEmergencies(model.numFailedEmergencies)
        // the number of resolved emergencies
        Logger.logNumberOfResolvedEmergencies(model.numResolvedEmergency)
    }
}
