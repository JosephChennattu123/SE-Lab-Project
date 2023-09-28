package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logParsingValidationResult
import de.unisaarland.cs.se.selab.systemtest.logTick

class SimpleTwoTicks : SystemTest() {
    override val name = "ExampleTest"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/example_assets.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 2

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("example_map.dot", true))
        assertNextLine(logParsingValidationResult("example_assets.json", true))
        assertNextLine(logParsingValidationResult("example_scenario.json", true))
        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0))
        // todo
        // The Simulation should end
        assertNextLine(LOG_SIMULATION_ENDED)
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 0 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 0 resolved emergencies.")
        // end of file is reached
        assertEnd()
    }
}
