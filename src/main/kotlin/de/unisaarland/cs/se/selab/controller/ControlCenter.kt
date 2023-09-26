package de.unisaarland.cs.se.selab.controller

import de.unisaarland.cs.se.selab.controller.phases.*
import de.unisaarland.cs.se.selab.model.Model

/**
 * ControlCenter handles the different phases of a tick */
class ControlCenter(
    var model: Model
) {
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
        emergencyDistribution.execute(model)
        assetAllocation.execute(model)
        requestProcessing.execute(model)
        updatePhase.execute(model)
        if(updatePhase.eventOccured) reroute.execute(model)
    }
}
