package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RerouteTest : SystemTest() {
    override val name = "ExampleTest"

    override val map = "mapFiles/example_map_modified.dot"
    override val assets = "assetsJsons/reroute_assets.json"
    override val scenario = "scenarioJsons/reroute_scenario.json"
    override val maxTicks = 10

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("example_map_modified.dot", true))
        assertNextLine(logParsingValidationResult("reroute_assets.json", true))
        assertNextLine(logParsingValidationResult("reroute_scenario.json", true))
        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
    }
}
