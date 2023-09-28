package de.unisaarland.cs.se.selab.controller

import de.unisaarland.cs.se.selab.controller.phases.AssetAllocation
import de.unisaarland.cs.se.selab.controller.phases.EmergencyDistribution
import de.unisaarland.cs.se.selab.controller.phases.GatherStatistics
import de.unisaarland.cs.se.selab.controller.phases.RequestProcessing
import de.unisaarland.cs.se.selab.controller.phases.Reroute
import de.unisaarland.cs.se.selab.controller.phases.UpdatePhase
import de.unisaarland.cs.se.selab.model.Model

/**
<<<<<<<<< Temporary merge branch 1
 * ControlCenter handles the different phases of a tick */
class ControlCenter(
    var model: Model
) {
=========
 * The ControlCenter runs the simulation.
 *
 * @param model contains the data to simulate
 */
class ControlCenter(val model: Model) {
>>>>>>>>> Temporary merge branch 2
    var emergencyDistribution = EmergencyDistribution()
    var assetAllocation = AssetAllocation()
    var requestProcessing = RequestProcessing()
    var updatePhase = UpdatePhase()
    var reroute = Reroute()
    var gatherStatistics = GatherStatistics()

<<<<<<<<< Temporary merge branch 1

=========
>>>>>>>>> Temporary merge branch 2
    /***
     * Runs the simulation for a fixed amount of ticks, then gathers statistics */
    fun simulate(): Boolean {
<<<<<<<<< Temporary merge branch 1
        while(model.currentTick < model.maxTick ) {
            tick()
        }
        gatherStatistics.execute(model)
=========
>>>>>>>>> Temporary merge branch 2
        TODO()
    }

    /***
     * Progresses the simulation by 1 tick */
    fun tick() {
<<<<<<<<< Temporary merge branch 1
        emergencyDistribution.execute(model)
        assetAllocation.execute(model)
        requestProcessing.execute(model)
        updatePhase.execute(model)
        if(updatePhase.eventOccured) reroute.execute(model)
        model.incrementTick()
=========
        TODO()
>>>>>>>>> Temporary merge branch 2
    }
}
