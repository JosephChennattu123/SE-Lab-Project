package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.*
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class TestReroute : SystemTest() {
    override val name = "TestReroute"

    override val map = "mapFiles/example_map_modified.dot"
    override val assets = "assetsJsons/reroute_assets.json"
    override val scenario = "scenarioJsons/reroute_scenario.json"
    override val maxTicks = 14

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("example_map_modified.dot", true))
        assertNextLine(logParsingValidationResult("reroute_assets.json", true))
        assertNextLine(logParsingValidationResult("reroute_scenario.json", true))

        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0))

        assertNextLine(logTick(1))
        assertNextLine(logEmergencyAssigned(0, 0))
        assertNextLine(logAssetAllocated(0, 0, 2))
        assertNextLine(logTick(2))
        assertNextLine(logTick(3))
        assertNextLine(logEmergencyAssigned(1, 0))

        assertNextLine(logAssetAllocated(1, 1, 1))
        assertNextLine(logAssetAllocated(2, 1, 1))
        assertNextLine(logAssetReallocated(0, 1))
        assertNextLine(logTick(4))
//        assertNextLine(logAssetArrived(0, 4))
        assertNextLine(logAssetArrived(0, 4))
        assertNextLine(logAssetArrived(1, 1))
        assertNextLine(logAssetArrived(2, 1))
        assertNextLine(logEmergencyHandlingStart(1))
        assertNextLine(logTick(5))
        assertNextLine(logTick(6))
        assertNextLine(logEmergencyResult(1, true))

        assertNextLine(logTick(7))
        assertNextLine(logAssetArrived(0, 1))
        assertNextLine(logAssetArrived(1, 1))
        assertNextLine(logAssetArrived(2, 1))
        assertNextLine(logTick(8))

        assertNextLine(logTick(9))
        assertNextLine(logAssetAllocated(0, 0, 2))

        assertNextLine(logTick(10))
        assertNextLine(logTick(11))
        assertNextLine(logAssetArrived(0, 7))
        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logTick(12))
        assertNextLine(logTick(13))

        assertNextLine(logEmergencyResult(0, true))

        assertNextLine(LOG_SIMULATION_ENDED)
        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(2))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(0))
        assertNextLine(logNumberOfResolvedEmergencies(2))
        // end of file is reached
        assertEnd()
    }
}
