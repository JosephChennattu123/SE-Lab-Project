package de.unisaarland.cs.se.selab.controller

import de.unisaarland.cs.se.selab.controller.phases.AssetAllocation
import de.unisaarland.cs.se.selab.controller.phases.EmergencyDistribution
import de.unisaarland.cs.se.selab.controller.phases.GatherStatistics
import de.unisaarland.cs.se.selab.controller.phases.RequestProcessing
import de.unisaarland.cs.se.selab.controller.phases.Reroute
import de.unisaarland.cs.se.selab.controller.phases.UpdatePhase
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
     * Runs the simulation for a fixed amount of ticks, then gathers statistics */
    fun simulate(): Boolean {
        while (model.maxTick == null || model.currentTick < model.maxTick) {
            tick()
        }
        gatherStatistics.execute(model)
        TODO()
    }

    /***
     * Progresses the simulation by 1 tick */
    fun tick() {
        emergencyDistribution.execute(model)
        assetAllocation.execute(model)
        requestProcessing.execute(model)
        updatePhase.execute(model)
        if (updatePhase.eventOccurred) reroute.execute(model)
        model.incrementTick()
    }
}
