package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logAssetArrived
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyHandlingStart
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logNumberOfFailedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfOngoingEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReceivedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReroutedAssets
import de.unisaarland.cs.se.selab.systemtest.logNumberOfResolvedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logParsingValidationResult
import de.unisaarland.cs.se.selab.systemtest.logTick
/**
 * scenario:
 * Emergency Location:
 * 2 -> 3 [village=Saarbruecken; name=Eisenbahnstrasse; heightLimit=8; weight = 6;
 * primaryType=sideStreet; secondaryType=none;];
 * Emergency requirement:
 * 2 Firetrucks with water, 1200l water
 * */
class OneEmergency : SystemTest() {
    override val name = "OneEmergency"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/oneEmergency_scenario.json"
    override val maxTicks = 5
    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("example_map.dot", true))
        assertNextLine(logParsingValidationResult("example_assets.json", true))
        assertNextLine(logParsingValidationResult("oneEmergency_scenario.json", true))
        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0))
        assertNextLine(logTick(1))
        assertNextLine(logEmergencyAssigned(0, 0))
        // allocate asset 18 & 22 (height 2), no reallocate, no request
        assertNextLine(logAssetAllocated(18, 0, 0))
        assertNextLine(logAssetAllocated(22, 0, 0))
        assertNextLine(logTick(2))
        assertNextLine(logAssetArrived(18, 2))
        assertNextLine(logAssetArrived(22, 2))
        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logTick(3))
        assertNextLine(logEmergencyResult(0, true))
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
