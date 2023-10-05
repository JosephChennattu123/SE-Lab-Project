package de.unisaarland.cs.se.selab.systemtest.simulation.events

import de.unisaarland.cs.se.selab.systemtest.InitialisationLogging
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest
import de.unisaarland.cs.se.selab.systemtest.logAssetAllocated
import de.unisaarland.cs.se.selab.systemtest.logAssetArrived
import de.unisaarland.cs.se.selab.systemtest.logAssetReallocated
import de.unisaarland.cs.se.selab.systemtest.logAssetRerouted
import de.unisaarland.cs.se.selab.systemtest.logEmergencyAssigned
import de.unisaarland.cs.se.selab.systemtest.logEmergencyHandlingStart
import de.unisaarland.cs.se.selab.systemtest.logEmergencyResult
import de.unisaarland.cs.se.selab.systemtest.logEventStatus
import de.unisaarland.cs.se.selab.systemtest.logNumberOfFailedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfOngoingEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReceivedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logNumberOfReroutedAssets
import de.unisaarland.cs.se.selab.systemtest.logNumberOfResolvedEmergencies
import de.unisaarland.cs.se.selab.systemtest.logTick

class VehicleUnavailable : SystemTest() {
    override val assets =
        "fullAssets/vehicleUnavailable/assets.json"
    override val map =
        "fullAssets/vehicleUnavailable/map.dot"
    override val maxTicks = 15
    override val name = "VehicleUnavailable"
    override val scenario =
        "fullAssets/vehicleUnavailable/scenario.json"

    override suspend fun run() {
        assertNextLine(InitialisationLogging.logSuccess("map.dot"))
        assertNextLine(InitialisationLogging.logSuccess("assets.json"))
        assertNextLine(InitialisationLogging.logSuccess("scenario.json"))
        assertNextLine(LOG_SIMULATION_START)

        assertNextLine(logTick(0))

        assertNextLine(logTick(1))
        assertNextLine(logEmergencyAssigned(0, 0)) // fire 1
        assertNextLine(logEmergencyAssigned(2, 2)) // med 1

        assertNextLine(logAssetAllocated(0, 0, 1)) // water truck 1200
        assertNextLine(logAssetAllocated(1, 0, 1)) // water truck 600
        assertNextLine(logAssetAllocated(3, 2, 1)) // ambulance

        assertNextLine(logTick(2))
        assertNextLine(logAssetArrived(0, 1)) // water truck 1200
        assertNextLine(logAssetArrived(1, 1)) // water truck 600
        assertNextLine(logAssetArrived(3, 0)) // ambulance

        assertNextLine(logEmergencyHandlingStart(0))
        assertNextLine(logEmergencyHandlingStart(2))

        // resolve emergencies start events.
        assertNextLine(logTick(3))
        assertNextLine(logEmergencyResult(0, true))
        assertNextLine(logEmergencyResult(2, true))

        // wrong from here on
        assertNextLine(logEventStatus(0, true))
        assertNextLine(logAssetRerouted(3)) // all vehicles are rerouted.

        assertNextLine(logTick(4))
        assertNextLine(logEmergencyAssigned(1, 0)) // fire 1
        assertNextLine(logEmergencyAssigned(3, 2)) // med 2

        assertNextLine(logAssetAllocated(4, 3, 1)) // ambulance already there
        assertNextLine(logAssetAllocated(5, 3, 1)) // emergency doctor car already there
        assertNextLine(logAssetReallocated(1, 1)) // water truck 600

        assertNextLine(logTick(5))
        assertNextLine(logAssetArrived(0, 0)) // water truck 1200 needs to be refilled 2 ticks
        assertNextLine(logAssetArrived(1, 0)) // water truck 600
        assertNextLine(logAssetArrived(3, 3)) // ambulance needs to be unloaded (1 tick) and unavailable (1 tick)
        assertNextLine(logAssetArrived(4, 3)) // ambulance already there
        assertNextLine(logAssetArrived(5, 3)) // emergency doctor car already there
        assertNextLine(logEventStatus(1, true))

        assertNextLine(logTick(6))
        assertNextLine(logTick(7))
        assertNextLine(logTick(8))
        assertNextLine(logEventStatus(0, false))
        assertNextLine(logTick(9))
        assertNextLine(logTick(10))
        assertNextLine(logAssetAllocated(0, 1, 1))
        assertNextLine(logTick(11))
        assertNextLine(logAssetArrived(0, 0))
        assertNextLine(logEmergencyHandlingStart(1))
        assertNextLine(logTick(12))
        assertNextLine(logEmergencyResult(1, true))
        assertNextLine(logTick(13))
        assertNextLine(logAssetArrived(0, 0))
        assertNextLine(logAssetArrived(1, 0))
        assertNextLine(logEmergencyResult(3, false))
        assertNextLine(LOG_SIMULATION_ENDED)

        // Statistics
        assertNextLine(logNumberOfReroutedAssets(3))
        assertNextLine(logNumberOfReceivedEmergencies(4))
        assertNextLine(logNumberOfOngoingEmergencies(0))
        assertNextLine(logNumberOfFailedEmergencies(1))
        assertNextLine(logNumberOfResolvedEmergencies(3))
    }
}
