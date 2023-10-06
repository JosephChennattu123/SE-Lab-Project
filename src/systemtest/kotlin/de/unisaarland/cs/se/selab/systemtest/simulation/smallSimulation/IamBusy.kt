package de.unisaarland.cs.se.selab.systemtest.simulation.smallSimulation

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logAssetArrived
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyHandlingStart
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logEventStatus
import de.unisaarland.cs.se.selab.systemtest.logNumberOfFailedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfOngoingEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReceivedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReroutedAssets
import de.unisaarland.cs.se.selab.systemtest.logNumberOfResolvedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logTick

/**
 * In this simulation we have 3 emergencies and one event all of which are happening on different ticks
 * except for one emergency all the other ones fail
 * the event causes a rerouting that causes one asset to arrive later.
 * the simulation ends after all emergencies have ended.
 * */

class IamBusy : SystemTest() {
    override val assets =
        "src/systemtest/resources/assetsJsons/imbusy_assets.json"
    override val map =
        "src/systemtest/resources/mapFiles/imbusy_map.dot"
    override val maxTicks = 20
    override val name = "IamBusy"
    override val scenario =
        "src/systemtest/resources/scenarioJsons/imbusy_scenario.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess("imbusy_map.dot"))
        assertNextLine(InitialisationLogging.logSuccess("imbusy_assets.json"))
        assertNextLine(InitialisationLogging.logSuccess("imbusy_scenario.json"))
        assertNextLine(LOG_SIMULATION_START)
        // tick 0 starts
        assertNextLine(logTick(0))
        // tick 1 starts
        assertNextLine(logTick(1))
        assertNextLine(logEmergencyAssigned(0, 0))
        assertNextLine(logEmergencyAssigned(1, 0))
        assertNextLine(logAssetAllocated(0, 0, 1))
        assertNextLine(logAssetAllocated(1, 0, 1))
        // in the same tick vehicle gets its flag changed from assigned to to_site
        assertNextLine(logEventStatus(0, true))
        // no reroutes should occur
        // tick 2 starts
        assertNextLine(logTick(2))
        // vehicles should drive and arrive
        assertNextLine(logAssetArrived(0, 4))
        assertNextLine(logAssetArrived(1, 4))
        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logEventStatus(0, false))
        // nothing more happens in this round
        // tick 3
        assertNextLine(logTick(3))
        assertNextLine(logEmergencyResult(0, true))
        // vehicles should have been set to returning with the new path
        // tick 4
        assertNextLine(logTick(4))
        // vehicles have driven and arrived
        assertNextLine(logAssetArrived(0, 0))
        assertNextLine(logAssetArrived(1, 0))
        // tick 5
        assertNextLine(logTick(5))
        // 1 second left for vehicle
        // tick 6
        assertNextLine(logTick(6))
        // now vehicle should have been set to AT_Base
        // tick 7
        assertNextLine(logTick(7))
        assertNextLine(logAssetAllocated(0, 1, 1))
        assertNextLine(logAssetAllocated(0, 1, 1))
        // vehicle flag goes from at base to to_site
        // tick 8
        assertNextLine(logTick(8))
        assertNextLine(logAssetArrived(0, 4))
        assertNextLine(logAssetArrived(1, 4))
        assertNextLine(logEmergencyHandlingStart(0))
        // tick 9
        assertNextLine(logTick(9))
        assertNextLine(logEmergencyResult(1, true))
        // simulation ends nothing more to do
        assertNextLine(LOG_SIMULATION_ENDED)
        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(2))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(0))
        assertNextLine(logNumberOfResolvedEmergencies(2))
    }
}
