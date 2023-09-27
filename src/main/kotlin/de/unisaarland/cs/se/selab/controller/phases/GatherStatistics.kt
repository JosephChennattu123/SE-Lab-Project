package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.util.Logger

class GatherStatistics {
    fun execute(model: Model) {
        Logger.logSimulationEnded()
        // the number of rerouted assets
        Logger.logNumberOfReroutedAssets()
        // the number of received emergencies
        Logger.logNumberOfRecievedEmergencies(model.emergencies.values.size)
        // the number of still ongoing emergencies
        Logger.logNumberOfOngoingEmergencies(model.assignedEmergencies.size)
        // the number of failed emergencies
        Logger.logNumberOfFailedEmergencies()
        // the number of resolved emergencies
        Logger.logNumberOfResolvedEmergencies()
    }
}
