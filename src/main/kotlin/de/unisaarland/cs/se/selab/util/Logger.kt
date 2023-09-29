package de.unisaarland.cs.se.selab.util

import java.io.File
import java.io.PrintWriter

/**
 * The logger is responsible to log the important events of the simulation
 */
object Logger {

    var outputFile: String? = null

    private var numReroutedAssets: Int = 0
    private var numFailedEmergencies: Int = 0
    private var numResolvedEmergency: Int = 0

    private var printWriter: PrintWriter? = null

    init {
        printWriter = PrintWriter(System.out)
    }

    private fun printLog(output: String) {
        if (outputFile == null) {
            printWriter?.println(output)
        } else {
            File(outputFile).writeText(
                output,
                Charsets.UTF_8
            )
        }
    }

    /**
     * @param filename the name of the configuration file
     * @param success true if parsing and validation was successful, false otherwise
     */
    fun logParsingValidationResult(filename: String, success: Boolean) {
        val output: String = if (success) {
            "Initialization Info: $filename successfully parsed and validated"
        } else {
            "Initialization Info: $filename successfully parsed and validated"
        }
        printLog(output)
    }

    /**
     * Logs that the simulation start
     * */
    fun logSimulationStart() {
        printLog("Simulation starts")
    }

    /**
     * @param tick the number of the tick
     */
    fun logTick(tick: Int) {
        printLog("Simulation Tick:  $tick")
    }

    /***
     * @param emergencyId the id of the emergency
     * @param baseId the id of the base
     */
    fun logEmergencyAssigned(emergencyId: Int, baseId: Int) {
        printLog("Emergency Assignment $emergencyId assigned to $baseId")
    }

    /**
     * @param assetId the id of the vehicle
     * @param emergencyId the id of the emergency
     * @param arrivesInt the number of ticks it takes the vehicle to arrive
     */
    fun logAssetAllocated(assetId: Int, emergencyId: Int, arrivesInt: Int) {
        printLog(
            "Asset Allocation: $assetId allocated to $emergencyId; $arrivesInt\n" +
                "ticks to arrive."
        )
    }

    /**
     * @param assetId the id of the vehicle
     * @param emergencyId the id of the new emergency
     */
    fun logAssetReallocated(assetId: Int, emergencyId: Int) {
        printLog("Asset Reallocation: $assetId reallocated to $emergencyId")
        // numReroutedAssets += 1
    }

    /**
     * @param requestId the id of the request
     * @param targetBaseId the id of the target base
     * @param emergencyId the id of the emergency
     */
    fun logRequest(requestId: Int, targetBaseId: Int, emergencyId: Int) {
        printLog("Asset Request: $requestId sent to $targetBaseId for $emergencyId")
    }

    /**
     * @param emergencyId the id of the emergency
     */
    fun logRequestFailed(emergencyId: Int) {
        printLog("Request Failed: $emergencyId failed")
    }

    /**
     * @param assetId the id of the vehicle
     * @param vertexId the id of the vertex
     */
    fun logAssetArrived(assetId: Int, vertexId: Int) {
        printLog("Asset Arrival: $assetId arrived at $vertexId")
    }

    /**
     * @param emergencyId the id of the emergency
     */
    fun logEmergencyHandlingStart(emergencyId: Int) {
        printLog("Emergency Handling Start: $emergencyId handling started")
    }

    /**
     * Logs the result of an emergency.
     * @param emergencyId the id of the emergency
     * @param success true if resolved else failed
     */
    fun logEmergencyResult(emergencyId: Int, success: Boolean) {
        if (success) {
            printLog("Emergency Resolved: $emergencyId resolved")
            numResolvedEmergency += 1
        } else {
            printLog("Emergency Failed: $emergencyId failed")
            numFailedEmergencies += 1
        }
    }

    /**
     * @param eventId the id of the event
     * @param triggered true if triggered else false (ended)
     */
    fun logEventStatus(eventId: Int, triggered: Boolean) {
        if (triggered) {
            printLog("Event Triggered: $eventId triggered")
        } else {
            printLog("Event Ended: $eventId ended")
        }
    }

    /**
     * @param assetsAmount the amount of rerouted vehicles
     */
    fun logAssetRerouted(assetsAmount: Int) {
        printLog("Assets Rerouted: $assetsAmount")
        numReroutedAssets += 1
    }

    /**
     * Logs that the simulation ended
     */
    fun logSimulationEnded() {
        printLog("Simulation End")
    }

    /**
     * Logs the statistics at the end
     * @param receivedEmergenciesAmount the number of received emergencies (emergency calls)
     * @param ongoingEmergenciesAmount the remaining ongoing emergencies
     */
    fun logStatistics(receivedEmergenciesAmount: Int, ongoingEmergenciesAmount: Int) {
        printLog("Simulation Statistics: $numReroutedAssets assets rerouted")
        printLog("Simulation Statistics: $receivedEmergenciesAmount received emergencies")
        printLog("Simulation Statistics: $ongoingEmergenciesAmount ongoing emergencies")
        printLog("Simulation Statistics: $numFailedEmergencies failed emergencies")
        printLog("Simulation Statistics: $numResolvedEmergency resolved emergencies.")
    }
}
