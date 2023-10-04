package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.*
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class SimpleTest : SystemTest() {
    override val name = "SimpleTest"

    override val map = "mapFiles/small_map.dot"
    override val assets = "assetsJsons/simple_assets.json"
    override val scenario = "scenarioJsons/small_scenario.json"
    override val maxTicks = 5

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("example_map_modified.dot", true))
        assertNextLine(logParsingValidationResult("reroute_assets.json", true))
        assertNextLine(logParsingValidationResult("reroute_scenario.json", true))
        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0))
        assertNextLine(logEventStatus(0, true))
        assertNextLine(logTick(1))
        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logEmergencyAssigned(0, 0))
        assertNextLine(logAssetAllocated(0, 0, 2))
        assertNextLine(logTick(2))
        assertNextLine(logTick(3))
        assertNextLine(logAssetArrived(0, 3))
        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logTick(4))
        assertNextLine(logEmergencyResult(0, success = true))
        assertNextLine(logTick(5))
        assertNextLine(logEventStatus(0, false))

        // The Simulation should end
        assertNextLine(LOG_SIMULATION_ENDED)
        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(1))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(0))
        assertNextLine(logNumberOfResolvedEmergencies(1))
        // end of file is reached
        assertEnd()
    }
}
