package de.unisaarland.cs.se.selab.systemtest.validation

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

const val VALID_ASSETS = "broken/assets/ThreeBasesThreeVehicles.json"
const val ASSETS = "ThreeBasesThreeVehicles.json"

class MixedEvents : SystemTest() {
    override val assets = VALID_ASSETS
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "mixedEvents"
    override val scenario = "broken/scenarios/mixedEvents.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logSuccess(ASSETS))
        assertNextLine(InitialisationLogging.logFailed("mixedEvents.json"))
        assertEnd()
    }
}

class EmergencyOnNonExistingRoad : SystemTest() {
    override val assets = VALID_ASSETS
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "EmergencyOnNonExistingRoad"
    override val scenario = "broken/scenarios/emergencyOnNonExistentRoad.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logSuccess(ASSETS))
        assertNextLine(InitialisationLogging.logFailed("emergencyOnNonExistentRoad.json"))
        assertEnd()
    }
}

class EventOnNonExistingRoad : SystemTest() {
    override val assets = VALID_ASSETS
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "EventOnNonExistingRoad"
    override val scenario = "broken/scenarios/eventOnNonExistingRoad.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logSuccess(ASSETS))
        assertNextLine(InitialisationLogging.logFailed("eventOnNonExistingRoad.json"))
        assertEnd()
    }
}

class VehicleEventNoVehicle : SystemTest() {
    override val assets = VALID_ASSETS
    override val map: String = VALID_MAP
    override val maxTicks = 1
    override val name = "VehicleEventNoVehicle"
    override val scenario = "broken/scenarios/vehicleEventOnNonExistingVehicle.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess(MAP_NAME))
        assertNextLine(InitialisationLogging.logSuccess(ASSETS))
        assertNextLine(InitialisationLogging.logFailed("vehicleEventOnNonExistingVehicle.json"))
        assertEnd()
    }
}
