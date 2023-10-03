package de.unisaarland.cs.se.selab.config

import kotlin.test.Test
import kotlin.test.assertNull

class EventValidationTests {
    // maps.
    private val simpleMap = "src/test/resources/validationResources/maps/simpleValidMap.dot"

    // assets.
    private val simpleAssets = "src/test/resources/validationResources/assets/ThreeBasesThreeVehicles.json"

    // scenarios.
    private val eventWithIllegalValues = "src/test/resources/validationResources/scenarios/eventWithIllegalValues.json"
    private val eventOnNonExistingRoad = "src/test/resources/validationResources/scenarios/eventOnNonExistingRoad.json"
    private val vehicleEventOnNonExistingVehicle =
        "src/test/resources/validationResources/scenarios/vehicleEventOnNonExistingVehicle.json"
    private val rushHourWithOneWayField =
        "src/test/resources/validationResources/scenarios/rushHourWithOneWayField.json"
    private val constructionWithVehicleId =
        "src/test/resources/validationResources/scenarios/constructionWithVehicleId.json"
    private val vehicleEventWithRoadType =
        "src/test/resources/validationResources/scenarios/vehicleEventWithRoadType.json"

    @Test
    fun eventWithIllegalValues() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, eventWithIllegalValues)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun eventOnNonExistingRoad() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, eventOnNonExistingRoad)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun eventOnNonExistingVehicle() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, vehicleEventOnNonExistingVehicle)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun oneWayRushHour() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, rushHourWithOneWayField)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun trafficJamWithVehicleId() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, constructionWithVehicleId)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }

    @Test
    fun vehicleEventWithRoadType() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(simpleAssets, vehicleEventWithRoadType)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser, jsonParserTest, null))
    }
}
