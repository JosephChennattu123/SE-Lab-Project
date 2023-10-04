package de.unisaarland.cs.se.selab.systemtest

/**
 * The auxiliary functions here can help to output the text of the log in all cases.
 */

/**
 * Logs that the simulation starts or ends
 */
const val LOG_SIMULATION_START: String = "Simulation starts"
const val LOG_SIMULATION_ENDED: String = "Simulation End"

/**
 * @param filename the name of the configuration file
 * @param success true if parsing and validation was successful, false otherwise
 */
fun logParsingValidationResult(filename: String, success: Boolean): String {
    return if (success) {
        "Initialization Info: $filename successfully parsed and validated"
    } else {
        "Initialization Info: $filename successfully parsed and validated"
    }
}

/**
 * @param tick the number of the tick
 */
fun logTick(tick: Int): String {
    return "Simulation Tick:  $tick"
}

/***
 * @param emergencyId the id of the emergency
 * @param baseId the id of the base
 */
fun logEmergencyAssigned(emergencyId: Int, baseId: Int): String {
    return "Emergency Assignment: $emergencyId assigned to $baseId"
}

/**
 * @param assetId the id of the vehicle
 * @param emergencyId the id of the emergency
 * @param arrivesInt the number of ticks it takes the vehicle to arrive
 */
fun logAssetAllocated(assetId: Int, emergencyId: Int, arrivesInt: Int): String {
    return "Asset Allocation: $assetId allocated to $emergencyId; $arrivesInt\n" +
        "ticks to arrive."
}

/**
 * @param assetId the id of the vehicle
 * @param emergencyId the id of the new emergency
 */
fun logAssetReallocated(assetId: Int, emergencyId: Int): String {
    return "Asset Reallocation: $assetId reallocated to $emergencyId"
}

/**
 * @param requestId the id of the request
 * @param targetBaseId the id of the target base
 * @param emergencyId the id of the emergency
 */
fun logRequest(requestId: Int, targetBaseId: Int, emergencyId: Int): String {
    return "Asset Request: $requestId sent to $targetBaseId for $emergencyId"
}

/**
 * @param emergencyId the id of the emergency
 */
fun logRequestFailed(emergencyId: Int): String {
    return "Request Failed: $emergencyId failed"
}

/**
 * @param assetId the id of the vehicle
 * @param vertexId the id of the vertex
 */
fun logAssetArrived(assetId: Int, vertexId: Int): String {
    return "Asset Arrival: $assetId arrived at $vertexId"
}

/**
 * @param emergencyId the id of the emergency
 */
fun logEmergencyHandlingStart(emergencyId: Int): String {
    return "Emergency Handling Start: $emergencyId handling started"
}

/**
 * Logs the result of an emergency.
 * @param emergencyId the id of the emergency
 * @param success true if resolved else failed
 */
fun logEmergencyResult(emergencyId: Int, success: Boolean): String {
    return if (success) {
        "Emergency Resolved: $emergencyId resolved"
    } else {
        "Emergency Failed: $emergencyId failed"
    }
}

/**
 * @param eventId the id of the event
 * @param triggered true if triggered else false (ended)
 */
fun logEventStatus(eventId: Int, triggered: Boolean): String {
    return if (triggered) {
        "Event Triggered: $eventId triggered"
    } else {
        "Event Ended: $eventId ended"
    }
}

/**
 * @param assetsAmount the amount of rerouted vehicles
 */
fun logAssetRerouted(assetsAmount: Int): String {
    return "Assets Rerouted: $assetsAmount"
}

/**
 * @param reroutedAssetsAmount the number of rerouted assets
 */
fun logNumberOfReroutedAssets(numReroutedAssets: Int): String {
    return "Simulation Statistics: $numReroutedAssets assets rerouted"
}

/**
 * @param receivedEmergenciesAmount the number of received emergencies (emergency calls)
 */
fun logNumberOfReceivedEmergencies(receivedEmergenciesAmount: Int): String {
    return "Simulation Statistics: $receivedEmergenciesAmount received emergencies"
}

/**
 * @param ongoingEmergenciesAmount the remaining ongoing emergencies
 */
fun logNumberOfOngoingEmergencies(ongoingEmergenciesAmount: Int): String {
    return "Simulation Statistics: $ongoingEmergenciesAmount ongoing emergencies"
}

/**
 * Logs the number of total failed emergencies
 */
fun logNumberOfFailedEmergencies(numFailedEmergencies: Int): String {
    return "Simulation Statistics: $numFailedEmergencies failed emergencies"
}

/**
 * Logs the number of total resolved emergencies
 */
fun logNumberOfResolvedEmergencies(numResolvedEmergency: Int): String {
    return "Simulation Statistics: $numResolvedEmergency resolved emergencies."
}
