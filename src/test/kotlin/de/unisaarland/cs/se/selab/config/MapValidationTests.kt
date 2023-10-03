package de.unisaarland.cs.se.selab.config

import kotlin.test.Test
import kotlin.test.assertNull

class MapValidationTests {
    // maps.
    val smallMap = "src/test/resources/validationResources/maps/mapWith2Nodes.dot"
    val mapWithLoop = "src/test/resources/validationResources/maps/mapWithLoop.dot"
    val mapWithTwoEdgesSameSourceAndTarget =
        "src/test/resources/validationResources/maps/twoEdgesBetweenTwoVertices.dot"
    val vertexInManyVillages = "src/test/resources/validationResources/maps/ingoingEdgesDifferentVillages.dot"
    val noMainStreets = "src/test/resources/validationResources/maps/noMainStreets.dot"
    val illegalRoads = "src/test/resources/validationResources/maps/roadsWithIllegalValues.dot"

    // assets.
    val simpleAssets = "src/test/resources/validationResources/assets/ThreeBasesThreeVehicles.json"

    // scenarios.
    val simpleScenario = "src/test/resources/validationResources/scenarios/OneEventOneEmergency.json"

    @Test
    fun edgeIsLoop() {
        val dotParser = DotParser(mapWithLoop)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun twoEdgesSameSourceAndTarget() {
        val dotParser = DotParser(mapWithTwoEdgesSameSourceAndTarget)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun oneVertexDifferentVillages() {
        val dotParser = DotParser(vertexInManyVillages)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun noMainStreetsInVillage() {
        val dotParser = DotParser(noMainStreets)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun illegalRoadDimensions() {
        val dotParser = DotParser(illegalRoads)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun mapTooSmall() {
        val dotParser = DotParser(smallMap)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }
}
