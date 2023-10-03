package de.unisaarland.cs.se.selab.config

import kotlin.test.Test
import kotlin.test.assertNull

class BaseValidationTests {

    // maps.
    val simpleMap = "src/test/resources/validationResources/maps/simpleValidMap.dot"

    // assets.
    val baseWithNoVertex = "src/test/resources/validationResources/assets/baseWithNoVertex.json"
    val twoBasesOneVertex = "src/test/resources/validationResources/assets/twoBasesOneVertex.json"
    val noPolice = "src/test/resources/validationResources/assets/noPolice.json"
    val baseWithNoVehicles = "src/test/resources/validationResources/assets/noPoliceVehicle.json"
    val policeWithAmbulance = "src/test/resources/validationResources/assets/policeStationWithAmbulance.json"
    val fireStationWithAmbulance = "src/test/resources/validationResources/assets/fireStationWithAmbulance.json"
    val hospitalWithFireTruck = "src/test/resources/validationResources/assets/hospitalWithFireTruck.json"
    val policeStationWithAmbulance = "src/test/resources/validationResources/assets/policeStationWithAmbulance.json"

    // scenarios.
    val simpleScenario = "src/test/resources/validationResources/scenarios/OneEventOneEmergency.json"



    @Test
    fun baseWithNoVertex() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(baseWithNoVertex,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun twoBasesOneVertex() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(twoBasesOneVertex,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun notAllDepartmentsPresent(){
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(noPolice,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun baseWithNoVehicle() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(baseWithNoVehicles,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun illegalPoliceAssets() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(policeWithAmbulance,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun illegalHospitalAssets() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(hospitalWithFireTruck,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

    @Test
    fun illegalFireDepartmentAssets() {
        val dotParser = DotParser(simpleMap)
        val jsonParserTest = JsonParser(fireStationWithAmbulance,simpleScenario)
        val validator = ValidatorManager()
        assertNull(validator.validate(dotParser,jsonParserTest,null))
    }

}