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

class IAmBusy : SystemTest() {
    override val assets =
        "assetsJsons/imbusy_assets.json"
    override val map =
        "mapFiles/imbusy_map.dot"
    override val maxTicks = 15
    override val name = "IAmBusy"
    override val scenario =
        "scenarioJsons/imbusy_scenario.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess("imbusy_map.dot"))
        assertNextLine(InitialisationLogging.logSuccess("imbusy_assets.json"))
        assertNextLine(InitialisationLogging.logSuccess("imbusy_scenario.json"))
        assertNextLine(LOG_SIMULATION_START)

        // tick 0
        assertNextLine(logTick(0))
        // tick 1
        assertNextLine(logTick(1))
        assertNextLine(logEmergencyAssigned(0, 0))
        assertNextLine(logEmergencyAssigned(1, 0))
        assertNextLine(logAssetAllocated(0, 0, 1))
        assertNextLine(logAssetAllocated(1, 0, 1))
        // vehicles now are set to the flag to_site
        assertNextLine(logEventStatus(0, true))
        // tick 2
        assertNextLine(logTick(2))
        // now the vehicles drive ahead and reach the site
        assertNextLine(logAssetArrived(0, 3))
        assertNextLine(logAssetArrived(1, 3))
        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logEventStatus(0, false))
        // tick 3
        assertNextLine(logTick(3))
        assertNextLine(logEmergencyResult(0, true))
        // now the vehicles are set to returning
        // tick 4
        assertNextLine(logTick(4))
        // vehicles have now driven back to site
        assertNextLine(logAssetArrived(0, 0))
        assertNextLine(logAssetArrived(1, 0))
        // vehicles should also have been set to busy
        // tick 5
        assertNextLine(logTick(5))
        // nothing happens but vehicles are busy for 1 more sec
        // tick 6
        assertNextLine(logTick(6))
        // vehicles should now be set back to base but not allocated in this tick
        // tick 7
        assertNextLine(logTick(7))
        assertNextLine(logAssetAllocated(0, 1, 1))
        assertNextLine(logAssetAllocated(1, 1, 1))
        // vehicles set from at base to site
        // tick 8
        assertNextLine(logTick(8))
        assertNextLine(logAssetArrived(0, 3))
        assertNextLine(logAssetArrived(1, 3))
        assertNextLine(logEmergencyHandlingStart(1))
        // tick 9
        assertNextLine(logTick(9))
        assertNextLine(logEmergencyResult(1, true))
        assertNextLine(LOG_SIMULATION_ENDED)

        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(2))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(0))
        assertNextLine(logNumberOfResolvedEmergencies(2))
    }
}
