package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.*
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class SimpleTest : SystemTest() {
    override val name = "SimpleTest"

    override val map = "mapFiles/small_map.dot"
    override val assets = "assetsJsons/simple_assets.json"
    override val scenario = "scenarioJsons/small_scenario.json"
    override val maxTicks = 6

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("small_map.dot", true))
        assertNextLine(logParsingValidationResult("simple_assets.json", true))
        assertNextLine(logParsingValidationResult("small_scenario.json", true))
        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0))
        assertNextLine(logEventStatus(0, true)) //  traffic jam event triggered on 2->3
        assertNextLine(logTick(1))
        assertNextLine(logEmergencyHandlingStart(0)) // emergency starts at tick 1
        assertNextLine(logEmergencyAssigned(0, 0)) // assign the emergency
        assertNextLine(logAssetAllocated(0, 0, 2)) // the ambulance takes 2 ticks to arrive
        assertNextLine(logTick(2))
        assertNextLine(logTick(3))
        assertNextLine(logAssetArrived(0, 3)) // ambulance arrives after 2 ticks
        assertNextLine(logEmergencyHandlingStart(0)) // emergency can start as the ambulance arrived
        assertNextLine(logTick(4))
        assertNextLine(logEmergencyResult(0, success = true)) // emergency takes 1 tick to finish
        assertNextLine(logTick(5))
        assertNextLine(logEventStatus(0, false)) // event ends after
        assertNextLine(logTick(6))
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
