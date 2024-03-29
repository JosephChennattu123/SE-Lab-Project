package de.unisaarland.cs.se.selab.systemtest.simulation.requesting

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logAssetArrived
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyHandlingStart
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logNumberOfFailedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfOngoingEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReceivedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReroutedAssets
import de.unisaarland.cs.se.selab.systemtest.logNumberOfResolvedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logRequest
import de.unisaarland.cs.se.selab.systemtest.logTick

class SuccessfulRequest : SystemTest() {
    override val assets =
        "fullAssets/requesting/assets.json"
    override val map =
        "fullAssets/requesting/map.dot"
    override val maxTicks = 15
    override val name = "SuccessfulRequest"
    override val scenario =
        "fullAssets/requesting/scenario.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess("map.dot"))
        assertNextLine(InitialisationLogging.logSuccess("assets.json"))
        assertNextLine(InitialisationLogging.logSuccess("scenario.json"))
        assertNextLine(LOG_SIMULATION_START)

        assertNextLine(logTick(0))

        assertNextLine(logTick(1))
        assertNextLine(logEmergencyAssigned(0, 1))
        assertNextLine(logAssetAllocated(0, 0, 1)) // motorcycle
        assertNextLine(logAssetAllocated(5, 0, 1)) // police car
        assertNextLine(logAssetAllocated(6, 0, 1)) // police car
        assertNextLine(logAssetAllocated(7, 0, 1)) // police car
        assertNextLine(logAssetAllocated(8, 0, 1)) // police car
        assertNextLine(logAssetAllocated(9, 0, 1)) // police car
        assertNextLine(logAssetAllocated(11, 0, 1)) // motorcycle
        assertNextLine(logAssetAllocated(13, 0, 1)) // k9
        assertNextLine(logAssetAllocated(14, 0, 1)) // k9
        assertNextLine(logAssetAllocated(19, 0, 1)) // police car

        // send request
        assertNextLine(logRequest(1, 0, 0)) // fire
        assertNextLine(logRequest(2, 2, 0)) // hospital

        // request processing.
        assertNextLine(logAssetAllocated(2, 0, 2)) // firetruck
        assertNextLine(logAssetAllocated(1, 0, 1)) // ambulance
        assertNextLine(logAssetAllocated(16, 0, 1)) // ambulance

        assertNextLine(logTick(2))
        assertNextLine(logAssetArrived(0, 2)) // motorcycle
        assertNextLine(logAssetArrived(1, 3)) // ambulance
        assertNextLine(logAssetArrived(5, 2)) // police car
        assertNextLine(logAssetArrived(6, 2)) // police car
        assertNextLine(logAssetArrived(7, 2)) // police car
        assertNextLine(logAssetArrived(8, 2)) // police car
        assertNextLine(logAssetArrived(9, 2)) // police car
        assertNextLine(logAssetArrived(11, 2)) // motorcycle
        assertNextLine(logAssetArrived(13, 2)) // k9
        assertNextLine(logAssetArrived(14, 2)) // k9
        assertNextLine(logAssetArrived(16, 3)) // ambulance
        assertNextLine(logAssetArrived(19, 2)) // police car

        assertNextLine(logTick(3))
        assertNextLine(logAssetArrived(2, 2))
        assertNextLine(logEmergencyHandlingStart(0))

        assertNextLine(logTick(4))
        assertNextLine(logEmergencyResult(0, true))

        assertNextLine(LOG_SIMULATION_ENDED)

        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(1))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(0))
        assertNextLine(logNumberOfResolvedEmergencies(1))
    }
}
