package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logEventStatus
import de.unisaarland.cs.se.selab.systemtest.logNumberOfFailedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfOngoingEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReceivedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReroutedAssets
import de.unisaarland.cs.se.selab.systemtest.logNumberOfResolvedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logParsingValidationResult
import de.unisaarland.cs.se.selab.systemtest.logTick

class SimpleMapEmergencyTimeout : SystemTest() {
    override val name = "SimpleMapEmergencyTimeout"

    override val map = "mapFiles/small_map1.dot"
    override val assets = "assetsJsons/emergencyAssignment_assets.json"
    override val scenario = "scenarioJsons/emergencyAssignment_scenario.json"
    override val maxTicks = 3

    /**
     * Emergency gets assigned, vehicle begins driving, then event prolongs drive time so much that
     * the emergency fails before the vehicle can reach it
     * */
    override suspend fun run() {
        assertNextLine(logParsingValidationResult("small_map1.dot", true))
        assertNextLine(logParsingValidationResult("emergencyAssignment_assets.json", true))
        assertNextLine(logParsingValidationResult("emergencyAssignment_scenario.json", true))

        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(1))

        assertNextLine(logEmergencyAssigned(0, 0))

        assertNextLine(logAssetAllocated(0, 0, 1))

        assertNextLine(logTick(2))

        assertNextLine(logEventStatus(0, true))

        assertNextLine(logTick(3))

        assertNextLine(logTick(4))

        assertNextLine(logTick(5))

        assertNextLine(logTick(6))

        assertNextLine(logTick(7))

        assertNextLine(logEmergencyResult(0, false))

        assertNextLine(LOG_SIMULATION_ENDED)

        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(0))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(1))
        assertNextLine(logNumberOfResolvedEmergencies(0))
    }
}
