package de.unisaarland.cs.se.selab.controller

import de.unisaarland.cs.se.selab.controller.phases.*
import de.unisaarland.cs.se.selab.model.Model

/**
 * The ControlCenter runs the simulation.
 *
 * @param model contains the data to simulate
 */
class ControlCenter(val model: Model) {
    var emergencyDistribution = EmergencyDistribution()
    var assetAllocation = AssetAllocation()
    var requestProcessing = RequestProcessing()
    var updatePhase = UpdatePhase()
    var reroute = Reroute()
    var gatherStatistics = GatherStatistics()

    /***
     * Runs the simulation until termination */
    fun simulate(): Boolean {
        TODO()
    }

    /***
     * Progresses the simulation by 1 tick */
    fun tick() {
        TODO()
    }
}
