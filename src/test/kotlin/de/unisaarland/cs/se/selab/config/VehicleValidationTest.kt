package de.unisaarland.cs.se.selab.config

import kotlin.test.Test
import kotlin.test.assertNull

class VehicleValidationTest {

    // maps.
    val simpleMap = "src/test/resources/validationResources/maps/simpleValidMap.dot"

    // assets.
    val simpleAssets = "src/test/resources/validationResources/assets/ThreeBasesThreeVehicles.json"
    val vehiclesWithIllegalValues = "src/test/resources/validationResources/assets/vehiclesWithIllegalValues.json"
    val emergencyDoctorWithLadder = "src/test/resources/validationResources/assets/emergencyDoctorWithLadder.json"
    val fireTruckWithCriminals = "src/test/resources/validationResources/assets/fireTruckWithCriminals.json"
    val policeCarWithWater = "src/test/resources/validationResources/assets/policeCarWithWater.json"


    // scenarios.
    val simpleScenario = "src/test/resources/validationResources/scenarios/OneEventOneEmergency.json"

    @Test
    fun vehicleWithIllegalValues() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(vehiclesWithIllegalValues,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun emergencyDoctorWithLadder() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(emergencyDoctorWithLadder,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun FireTruckWithCriminals() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(fireTruckWithCriminals,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun PoliceCarWithWater() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(policeCarWithWater,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }
}