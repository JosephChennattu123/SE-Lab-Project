package de.unisaarland.cs.se.selab.util

import java.io.File

/**
 * The logger is responsible to log the important events of the simulation
 */
object Logger {

    var outputFile: String? = null

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
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param tick the number of the tick
     */
    fun logTick(tick: Int) {
        var output: String = "Simulation Tick: $tick"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /***
     * @param emergencyId the id of the emergency
     * @param baseId the id of the base
     */
    fun logEmergencyAssigned(emergencyId: Int, baseId: Int) {
        var output: String = "Emergency Assignment $emergencyId assigned to $baseId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param assetId the id of the vehicle
     * @param emergencyId the id of the emergency
     * @param arrivesInt the number of ticks it takes the vehicle to arrive
     */
    fun logAssetAllocated(assetId: Int, emergencyId: Int, arrivesInt: Int) {
        var output: String = "Asset Allocation: $assetId allocated to $emergencyId; $arrivesInt\n" +
            "ticks to arrive."
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param assetId the id of the vehicle
     * @param emergencyId the id of the new emergency
     */
    fun logAssetReallocated(assetId: Int, emergencyId: Int) {
        var output: String = "Asset Reallocation: $assetId reallocated to $emergencyId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param requestId the id of the request
     * @param targetBaseId the id of the target base
     * @param emergencyId the id of the emergency
     */
    fun logRequest(requestId: Int, targetBaseId: Int, emergencyId: Int) {
        var output: String = "Asset Request: $requestId sent to $targetBaseId for $emergencyId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param emergencyId the id of the emergency
     */
    fun logRequestFailed(emergencyId: Int) {
        var output: String = "Request Failed: $emergencyId failed"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param assetId the id of the vehicle
     * @param vertexId the id of the vertex
     */
    fun logAssetArrived(assetId: Int, vertexId: Int) {
        var output: String = "Asset Arrival: $assetId arrived at $vertexId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param emergencyId the id of the emergency
     */
    fun logEmergencyHandlingStart(emergencyId: Int) {
        var output: String = "Emergency Handling Start: $emergencyId handling started"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * Logs the result of an emergency.
     * @param emergencyId the id of the emergency
     * @param success true if resolved else failed
     */
    fun logEmergencyResult(emergencyId: Int, success: Boolean) {
        var output: String =
            if (success) "Emergency Resolved: $emergencyId resolved" else "Emergency Failed: $emergencyId failed"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param eventId the id of the event
     */
    fun logEventEnded(eventId: Int) {
        var output: String = "Event Ended: $eventId ended"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param eventId the id of the event
     */
    fun logEventTriggered(eventId: Int) {
        var output: String = "Event Triggered: $eventId triggered"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param assetsAmount the amount of rerouted vehicles
     */
    fun logAssetRerouted(assetsAmount: Int) {
        var output: String = "Assets Rerouted: $assetsAmount"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * Logs that the simulation ended
     */
    fun logSimulationEnded() {
        var output: String = "Simulation End"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param reroutedAssetsAmount the number of rerouted assets
     */
    public fun logNumberOfReroutedAssets(reroutedAssetsAmount: Int) {
        var output: String = "Simulation Statistics: $reroutedAssetsAmount assets rerouted"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param recievedEmergenciesAmount the number of received emergencies (emergency calls)
     */
    public fun logNumberOfRecievedEmergencies(recievedEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $recievedEmergenciesAmount received emergencies"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param ongoingEmergenciesAmount the remaining ongoing emergencies
     */
    public fun logNumberOfOngoingEmergencies(ongoingEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $ongoingEmergenciesAmount ongoing emergencies"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param failedEmergenciesAmount the amount of emergencies that failed
     */
    public fun logNumberOfFailedEmergencies(failedEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $failedEmergenciesAmount failed emergencies"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    /**
     * @param resolvedEmergenciesAmount the amount resolved emergencies
     */
    public fun logNumberOfResolvedEmergencies(resolvedEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $resolvedEmergenciesAmount resolved emergencies."
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }
}
