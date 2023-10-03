package de.unisaarland.cs.se.selab.systemtest.validation

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.SystemTestBase

class GraphValidationFailingSemicolonsVertexTest :
    SystemTestBase(
        "GraphValidationFailingSemicolonsVertexTest",
        "mapFiles/broken/",
        "broken_vertices_semicolons.dot",
        "assetsJsons/",
        "example_assets.json",
        "scenarioJsons/",
        "example_scenario.json",
        1
    ) {
    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(this.mapFileName))
        assertEnd()
    }
}
