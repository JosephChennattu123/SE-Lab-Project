package de.unisaarland.cs.se.selab.util

import java.io.File

object Logger {

    var outputFile: String? = null

    var numReroutedAssets: Int = 0
    var numFailedEmergencies: Int = 0
    var numResolvedEmergency: Int = 0

    private fun printLog(output: String) {
        if (outputFile == null) {
            println(output)
        } else {
            File(outputFile).writeText(
                output,
                Charsets.UTF_8
            )
        }
    }

    public fun logParsingValidationSuccess(filename: String) {
        printLog(
            "Initialization Info: " +
                filename +
                "successfully parsed and validated"
        )
    }

    public fun logFileInvalid(filename: String) {
        printLog(
            "Initialization Info: " +
                filename +
                "Invalid"
        )
    }

    public fun logTick(tick: Int) {
        printLog("Simulation Tick:  $tick")
    }

    public fun logEmergencyAssigned(id1: Int, id2: Int) {
        printLog("Emergency Assignment" + id1 + "assigned to" + id2)
    }

    public fun logAssetAllocated(assetId: Int, emergencyId: Int, arrivesInt: Int) {
        printLog(
            "Asset Allocation: $assetId allocated to $emergencyId; $arrivesInt\n" +
                "ticks to arrive."
        )
    }

    public fun logAssetReallocated(assetId: Int, emergencyId: Int) {
        printLog("Asset Reallocation: $assetId reallocated to $emergencyId")
//        numReroutedAssets += 1
    }

    public fun logRequest(requestId: Int, targetBaseId: Int, emergencyId: Int) {
        printLog("Asset Request: $requestId sent to $targetBaseId for $emergencyId")
    }

    public fun logRequestFailed(emergencyId: Int) {
        printLog("Request Failed: $emergencyId failed")
    }

    public fun logAssetArrived(assetId: Int, vertexId: Int) {
        printLog("Asset Arrival: $assetId arrived at $vertexId")
    }

    public fun logEmergencyHandlingStart(emergencyId: Int) {
        printLog("Emergency Handling Start: $emergencyId handling started")
    }

    public fun logEmergencyResolve(emergencyId: Int) {
        printLog("Emergency Resolved: $emergencyId resolved")
        numResolvedEmergency += 1
    }

    public fun logEmergencyFailed(emergencyId: Int) {
        printLog("Emergency Failed: $emergencyId failed")
        numFailedEmergencies += 1
    }

    public fun logEventEnded(eventId: Int) {
        printLog("Event Ended: $eventId ended")
    }

    public fun logEventTriggered(eventId: Int) {
        printLog("Event Triggered: $eventId triggered")
    }

    public fun logAssetRerouted(assetsAmount: Int) {
        printLog("Assets Rerouted: $assetsAmount")
        numReroutedAssets += 1
    }

    public fun logSimulationEnded() {
        printLog("Simulation End")
    }

    public fun logNumberOfReroutedAssets() {
        printLog("Simulation Statistics: $numReroutedAssets assets rerouted")
    }

    public fun logNumberOfRecievedEmergencies(recievedEmergenciesAmount: Int) {
        printLog("Simulation Statistics: $recievedEmergenciesAmount received emergencies")
    }

    public fun logNumberOfOngoingEmergencies(ongoingEmergenciesAmount: Int) {
        printLog("Simulation Statistics: $ongoingEmergenciesAmount ongoing emergencies")
    }

    public fun logNumberOfFailedEmergencies() {
        printLog("Simulation Statistics: $numFailedEmergencies failed emergencies")
    }

    public fun logNumberOfResolvedEmergencies() {
        printLog("Simulation Statistics: $numResolvedEmergency resolved emergencies.")
    }
}
