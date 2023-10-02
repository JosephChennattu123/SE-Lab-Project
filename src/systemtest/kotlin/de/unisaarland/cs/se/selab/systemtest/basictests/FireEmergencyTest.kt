package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.*
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FireEmergencyTest : SystemTest() {
    override val name = "ExampleTest"

    override val map = "mapFiles/example_map.dot"
    override val assets = "assetsJsons/fire_emergency_assets.json"
    override val scenario = "scenarioJsons/fire_emergency_scenario.json"
    override val maxTicks = 22

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine(logParsingValidationResult("example_map.dot", true))
        assertNextLine(logParsingValidationResult("fire_emergency_assets.json", true))
        assertNextLine(logParsingValidationResult("fire_emergency_scenario.json", true))

        // The Simulation starts with tick 0
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0)) // road closure happens on  2->3,2->4 and 1->4
        assertNextLine(logEventStatus(0, true)) // trigger road closure event
        assertNextLine(logEventStatus(1, true)) // trigger road closure event
        assertNextLine(logEventStatus(2, true)) // trigger road closure event

        // Simulation Ticks and Events
        assertNextLine(logTick(1)) // traffic jam happens on 1->3 with factor 200,becomes basically blocked
        assertNextLine(logEventStatus(3, true)) // trigger traffic jam event
        assertNextLine(logTick(2))
        assertNextLine(logTick(3)) // first emergency happens on road 3->5
        assertNextLine(logEmergencyAssigned(0, 3)) // assigning emergency 0 to base 3

        // Allocating assets to the emergency
        assertNextLine(logAssetAllocated(0, 0, 2))
        assertNextLine(logAssetAllocated(1, 0, 2))
        assertNextLine(logAssetAllocated(2, 0, 2))
        assertNextLine(logAssetAllocated(3, 0, 2))
        // Requesting additional assets
        assertNextLine(logRequest(0, 5, 0)) // requesting assets from base 5 for emergency 0
        assertNextLine(logAssetAllocated(4, 0, 3))
        assertNextLine(logAssetAllocated(5, 0, 3)) // allocating asset 5 to emergency 0
        assertNextLine(logAssetAllocated(12, 0, 3))
        // Requesting more assets
        assertNextLine(logRequest(1, 6, 0)) // requesting assets from base 0 for emergency 0
        assertNextLine(logAssetAllocated(6, 0, 12))
        assertNextLine(logAssetAllocated(7, 0, 12))
        assertNextLine(logRequest(2, 0, 0))
        assertNextLine(logAssetAllocated(8, 0, 12))
        assertNextLine(logAssetAllocated(9, 0, 12))
        assertNextLine(logAssetAllocated(10, 0, 12))
        assertNextLine(logAssetAllocated(11, 0, 12))

        assertNextLine(logTick(4))
        assertNextLine(logTick(5))

        // Asset arrivals
        assertNextLine(logAssetArrived(0, 5))
        assertNextLine(logAssetArrived(1, 5))
        assertNextLine(logAssetArrived(2, 5))
        assertNextLine(logAssetArrived(3, 5))
        assertNextLine(logAssetArrived(4, 5))


        assertNextLine(logTick(6))
        assertNextLine(logAssetArrived(5, 5))
        assertNextLine(logAssetArrived(6, 5))
        assertNextLine(logAssetArrived(12, 5))

        assertNextLine(logTick(7))
        assertNextLine(logTick(8))
        assertNextLine(logTick(9))

        // More asset arrivals


        assertNextLine(logTick(10))

        assertNextLine(logAssetArrived(7, 3))
        assertNextLine(logAssetArrived(8, 3))

        assertNextLine(logTick(11))
        assertNextLine(logTick(12))
        assertNextLine(logTick(13))
        assertNextLine(logTick(14))
        assertNextLine(logTick(15))

        // Asset arrivals from base 0

        assertNextLine(logAssetArrived(7, 3))
        assertNextLine(logAssetArrived(8, 3))
        assertNextLine(logAssetArrived(9, 3))
        assertNextLine(logAssetArrived(10, 3))
        assertNextLine(logAssetArrived(11, 3))


        // All assets have arrived, emergency handling can start
        assertNextLine(logEmergencyHandlingStart(0))

        assertNextLine(logTick(16))
        assertNextLine(logTick(17))
        assertNextLine(logTick(18))

        // Logging the result of emergency handling
        assertNextLine(logEmergencyResult(0, true))

        assertNextLine(logTick(19))

        // Ending events
        assertNextLine(logEventStatus(0, false))
        assertNextLine(logEventStatus(1, false))
        assertNextLine(logEventStatus(2, false))
        assertNextLine(logTick(20))
        assertNextLine(logEventStatus(3, false))
        assertNextLine(logTick(21))
        assertNextLine(logTick(22))

        // The Simulation should end
        assertNextLine(LOG_SIMULATION_ENDED)

        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(1))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(0))
        assertNextLine(logNumberOfResolvedEmergencies(1))

        // end of file is reached
        assertEnd()
    }
}
