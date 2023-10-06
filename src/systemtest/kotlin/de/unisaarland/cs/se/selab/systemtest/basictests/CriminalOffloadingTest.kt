package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logAssetArrived
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyHandlingStart
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logParsingValidationResult
import de.unisaarland.cs.se.selab.systemtest.logTick

/**
 * Police Car receives Criminal from Emergency and offloads it back at Base
 * */
class CriminalOffloadingTest : SystemTest() {
    override val name: String = "CriminalOffloadingTest"
    override val assets: String = "assetsJsons/CriminalOffloadAssets.json"
    override val map: String = "mapFiles/OneLongStreet.dot"
    override val scenario: String = "scenarioJsons/CriminalOffloadScenario.json"
    override val maxTicks: Int = 20
    override suspend fun run() {
        assertNextLine(logParsingValidationResult("OneLongStreet.dot", true))
        assertNextLine(logParsingValidationResult("CriminalOffloadAssets.json", true))
        assertNextLine(logParsingValidationResult("CriminalOffloadScenario.json", true))

        assertNextLine(LOG_SIMULATION_START)

        assertNextLine(logTick(0))

        assertNextLine(logTick(1))

        assertNextLine(logEmergencyAssigned(0, 0))
        assertNextLine(logAssetAllocated(0, 0, 2))

        assertNextLine(logTick(2))

        assertNextLine(logTick(3))

        assertNextLine(logAssetArrived(0, 2))
        assertNextLine(logEmergencyHandlingStart(0))

        // emergency ends and police car sets course back to base

        assertNextLine(logTick(4))

        assertNextLine(logEmergencyResult(0, true))

        assertNextLine(logTick(5))

        assertNextLine(logTick(6))
        // PoliceEmergency1 gets assigned to base 0

        // PoliceCar arrives back at base
        assertNextLine(logAssetArrived(0, 0))
        // for the next 2 ticks, PoliceCar0 is unavailable because it is offloading criminals

        assertNextLine(logTick(7))

        assertNextLine(logEmergencyAssigned(1, 0))
        // PoliceEmergency1 starts but cannot allocate PoliceCar0 because it is busy, no requests can be sent either

        assertNextLine(logTick(8))

        // PoliceEmergency1 fails
        assertNextLine(logEmergencyResult(1, false))

        assertNextLine(LOG_SIMULATION_ENDED)
    }
}
