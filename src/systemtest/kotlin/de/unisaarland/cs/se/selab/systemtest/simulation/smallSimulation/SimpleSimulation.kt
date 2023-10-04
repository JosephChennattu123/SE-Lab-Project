package de.unisaarland.cs.se.selab.systemtest.simulation.smallSimulation

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logAssetArrived
import de.unisaarland.cs.se.selab.systemtest.logAssetReallocated
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyHandlingStart
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logEventStatus
import de.unisaarland.cs.se.selab.systemtest.logNumberOfFailedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfOngoingEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReceivedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReroutedAssets
import de.unisaarland.cs.se.selab.systemtest.logNumberOfResolvedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logRequest
import de.unisaarland.cs.se.selab.systemtest.logRequestFailed
import de.unisaarland.cs.se.selab.systemtest.logTick

/**
 * In this simulation we have 3 emergencies and one event all of which are happening on different ticks
 * except for one emergency all the other ones fail
 * the event causes a rerouting that causes one asset to arrive later.
 * the simulation ends after all emergencies have ended.
 * */

class SimpleSimulation : SystemTest() {
    override val assets =
        "simpleSimulation/assets.json"
    override val map =
        "simpleSimulation/map.dot"
    override val maxTicks = 1
    override val name = "SimpleSimulation"
    override val scenario =
        "simpleSimulation/scenario.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess("map.dot"))
        assertNextLine(InitialisationLogging.logSuccess("assets.json"))
        assertNextLine(InitialisationLogging.logSuccess("scenario.json"))
        assertNextLine(LOG_SIMULATION_START)

        assertNextLine(logTick(0))

        // fire 1 (id = 0) emergency is triggered.
        assertNextLine(logTick(1))
        // location : two_three
        assertNextLine(logEmergencyAssigned(0, 0))
        // path is 0->1->2
        assertNextLine(logAssetAllocated(0, 0, 3)) // water truck
        assertNextLine(logAssetAllocated(1, 0, 3)) // water truck

        // medical 1 emergency (id = 2) is triggered.
        assertNextLine(logTick(2))
        // location : zero_one
        assertNextLine(logEmergencyAssigned(2, 2))
        // path is
        assertNextLine(logAssetAllocated(3, 2, 2)) // ambulance

        // fire 2 emergency (id = 1) is triggered.
        assertNextLine(logTick(3))
        // location: zero_three
        assertNextLine(logEmergencyAssigned(1, 0))
        assertNextLine(logAssetReallocated(0, 1)) // first water truck
        assertNextLine(logRequest(0, 2, 1))
        // path is 3.
        assertNextLine(logAssetAllocated(4, 1, 1)) // second ambulance
        assertNextLine(logRequestFailed(2))

        assertNextLine(logTick(4))
        assertNextLine(logRequest(0, 2, 2))
        assertNextLine(logAssetArrived(0, 0))
        assertNextLine(logAssetArrived(1, 2))
        assertNextLine(logAssetArrived(3, 1))
        assertNextLine(logAssetArrived(4, 3))
        assertNextLine(logEmergencyHandlingStart(2))
        assertNextLine(logEventStatus(0, true)) // on road 1->2

        assertNextLine(logTick(5))
        assertNextLine(logEmergencyResult(0, false))
        assertNextLine(logEmergencyResult(1, false))
        assertNextLine(logEmergencyResult(2, true))
        assertNextLine(LOG_SIMULATION_ENDED)

        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(3))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(2))
        assertNextLine(logNumberOfResolvedEmergencies(1))
    }
}
