package de.unisaarland.cs.se.selab.systemtest.simulation.smallSimulation

import de.unisaarland.cs.se.selab.systemtest.ASSETS_JSONS_FOLDER
import de.unisaarland.cs.se.selab.systemtest.CONFLICTING_EMERGENCIES_SCENARIO
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_ENDED
import de.unisaarland.cs.se.selab.systemtest.LOG_SIMULATION_START
import de.unisaarland.cs.se.selab.systemtest.MAP_FILES_FOLDER
import de.unisaarland.cs.se.selab.systemtest.MINIMAL_MAP_LINE
import de.unisaarland.cs.se.selab.systemtest.SCENARIO_JSONS_FOLDER
import de.unisaarland.cs.se.selab.systemtest.SystemTestBase
import de.unisaarland.cs.se.selab.systemtest.UNDERSTAFFED_ASSETS
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
import de.unisaarland.cs.se.selab.systemtest.logParsingValidationResult
import de.unisaarland.cs.se.selab.systemtest.logTick

class UnderStaffedAndFail :
    SystemTestBase(
        "UnderStaffedAndFail",
        MAP_FILES_FOLDER,
        MINIMAL_MAP_LINE,
        ASSETS_JSONS_FOLDER,
        UNDERSTAFFED_ASSETS,
        SCENARIO_JSONS_FOLDER,
        CONFLICTING_EMERGENCIES_SCENARIO,
        tick = 6
    ) {
    override suspend fun run() {
        // Parsing and Validation successful
        assertNextLine(logParsingValidationResult(this.mapFileName, true))
        assertNextLine(logParsingValidationResult(this.assetsFileName, true))
        assertNextLine(logParsingValidationResult(this.scenarioFile, true))

        // Simulation starts
        assertNextLine(LOG_SIMULATION_START)
        assertNextLine(logTick(0))

        // tick 1: emergency calls
        assertNextLine(logTick(1))
        // assign emergencies to bases 0 (FIRE_STATION) and 2 (HOSPITAL)
        assertNextLine(logEmergencyAssigned(0, 0)) // FIRE SEVERITY 1
        assertNextLine(logEmergencyAssigned(1, 0)) // ACCIDENT SEVERITY 1
        assertNextLine(logEmergencyAssigned(2, 2)) // MEDICAL SEVERITY 1
        assertNextLine(logEmergencyAssigned(3, 2)) // MEDICAL SEVERITY 2

        assertNextLine(logAssetAllocated(4, 3, 1)) // AMBULANCE from 2 -> (0, 1)
        assertNextLine(logAssetAllocated(5, 3, 1)) // AMBULANCE from 2 -> (0, 1)
        assertNextLine(logAssetAllocated(0, 0, 1)) // FIRE_TRUCK_WATER from 0 -> (1, 2)
        assertNextLine(logAssetAllocated(1, 0, 1)) // FIRE_TRUCK_WATER from 0 -> (1, 2)
        // FIRE_TRUCK_TECHNICAL is not assigned as not enough staff left; and no request as no other fire station
        // EMERGENCY_DOCTOR_CAR not send as not enough staff; and not request as no other hospital

        // tick 2: vehicles arrive
        assertNextLine(logTick(2))
        assertNextLine(logAssetArrived(4, 1)) // AMBULANCE arrives
        assertNextLine(logAssetArrived(5, 1)) // AMBULANCE arrives
        assertNextLine(logAssetArrived(0, 1)) // FIRE_TRUCK_WATER arrives
        assertNextLine(logAssetArrived(1, 1)) // FIRE_TRUCK_WATER arrives

        // can NOT start MEDICAL Severity 2 as EMERGENCY_DOCTOR_CAR could not be sent
        assertNextLine(logEmergencyHandlingStart(0)) // FIRE Severity 1 handling
        assertNextLine(logEmergencyResult(1, false)) // ACCIDENT failed

        // tick 3: -
        assertNextLine(logTick(3))

        // tick 4: FIRE Severity 1 success
        assertNextLine(logTick(4))
        assertNextLine(logEmergencyResult(0, true))

        // tick 5: Simulation ends
        assertNextLine(logTick(5))
        assertNextLine(LOG_SIMULATION_ENDED)

        // Statistics
        assertNextLine(logNumberOfReroutedAssets(0))
        assertNextLine(logNumberOfReceivedEmergencies(4))
        assertNextLine(logNumberOfOngoingEmergencies(2)) // MEDICAL SEVERITY 1 and 2
        assertNextLine(logNumberOfFailedEmergencies(1)) // ACCIDENT
        assertNextLine(logNumberOfResolvedEmergencies(1)) // FIRE
        // end of file is reached
        assertEnd()
    }
}
