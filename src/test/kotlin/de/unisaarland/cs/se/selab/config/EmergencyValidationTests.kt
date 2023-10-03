package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Assertions.assertNull
import kotlin.test.Test

class EmergencyValidationTests {

    // maps.
    private val simpleMap = "src/test/resources/validationResources/maps/simpleValidMap.dot"

    // assets.
    private val simpleAssets = "src/test/resources/validationResources/assets/ThreeBasesThreeVehicles.json"

    // scenarios.
    private val simpleScenario = "src/test/resources/validationResources/scenarios/OneEventOneEmergency.json"
    private val emergencyOnNonExistentRoad =
        "src/test/resources/validationResources/scenarios/EmergencyOnNonExistentRoad.json"

    @Test
    fun emergencyWithIllegalValues() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun emergencyOnNonExistentRoad() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, emergencyOnNonExistentRoad)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }


}