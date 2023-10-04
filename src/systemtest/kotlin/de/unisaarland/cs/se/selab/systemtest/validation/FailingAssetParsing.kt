package de.unisaarland.cs.se.selab.systemtest.validation

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

const val VALID_MAP = "broken/maps/simpleValidMap.dot"
const val MAP_NAME = "simpleValidMap.dot"
const val VALID_SCENARIO = "broken/maps/ThreeBasesThreeVehicles.dot"

class BaseWithNoVertex : SystemTest() {
    override val assets = "broken/assets/baseWithNoVertex.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "BaseWithNoVertex"
    override val scenario = VALID_SCENARIO
    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("baseWithNoVertex.json"))
        assertEnd()
    }
}

class EmergencyDoctorWithLadder : SystemTest() {
    override val assets = "broken/assets/emergencyDoctorWithLadder.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "EmergencyDoctorWithLadder"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("emergencyDoctorWithLadder.json"))
        assertEnd()
    }
}

class FireStationWithAmbulance : SystemTest() {
    override val assets = "broken/assets/fireStationWithAmbulance.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "FireStationWithAmbulance"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("fireStationWithAmbulance.json"))
        assertEnd()
    }
}

class FireTruckWithAmbulance : SystemTest() {
    override val assets = "broken/assets/firetruckWithCriminals.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "FireTruckWithAmbulance"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("firetruckWithCriminals.json"))
        assertEnd()
    }
}

class HospitalWithFireTruck : SystemTest() {
    override val assets = "broken/assets/hospitalWithFireTruck.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "HospitalWithFireTruck"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("hospitalWithFireTruck.json"))
        assertEnd()
    }
}

class MissingDepartmentPolice : SystemTest() {
    override val assets = "broken/assets/noPolice.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "MissingDepartmentPolice"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("noPolice.json"))
        assertEnd()
    }
}

class MissingDepartmentHospital : SystemTest() {
    override val assets = "broken/assets/noHospital.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "MissingDepartmentHospital"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("noHospital.json"))
        assertEnd()
    }
}

class MissingDepartmentFireStation : SystemTest() {
    override val assets = "broken/assets/noFireStation.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "MissingDepartmentFireStation"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("noFireStation.json"))
        assertEnd()
    }
}

class PoliceNoVehicles : SystemTest() {
    override val assets = "broken/assets/noPoliceVehicle.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "PoliceNoVehicles"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("noPoliceVehicle.json"))
        assertEnd()
    }
}

class PoliceCarWithWater : SystemTest() {
    override val assets = "broken/assets/policeCarWithWater.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "PoliceCarWithWater"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("policeCarWithWater.json"))
        assertEnd()
    }
}

class PoliceStationWithAmbulance : SystemTest() {
    override val assets = "broken/assets/PoliceStationWithAmbulance.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "PoliceStationWithAmbulance"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("PoliceStationWithAmbulance.json"))
        assertEnd()
    }
}

class TwoBasesOneVertex : SystemTest() {
    override val assets = "broken/assets/twoBasesOneVertex.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "TwoBasesOneVertex"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("twoBasesOneVertex.json"))
        assertEnd()
    }
}

class VehiclesWithIllegalValues : SystemTest() {
    override val assets = "broken/assets/vehiclesWithIllegalValues.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "VehiclesWithIllegalValues"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("vehiclesWithIllegalValues.json"))
        assertEnd()
    }
}

class MixedVehicles : SystemTest() {
    override val assets = "broken/assets/mixedVehicles.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "MixedVehicles"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("mixedVehicles.json"))
        assertEnd()
    }
}

class MixedBases : SystemTest() {
    override val assets = "broken/assets/mixedBases.json"
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "MixedVehicles"
    override val scenario = VALID_SCENARIO

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logFailed("mixedBases.json"))
        assertEnd()
    }
}
