package de.unisaarland.cs.se.selab.util

import java.io.File

object Logger {

    var outputFile: String? = null

    public fun logParsingValidationSuccess(filename: String) {
        var output: String =
            "Initialization Info: " +
                    filename +
                    "successfully parsed and validated"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logFileInvalid(filename: String) {
        var output: String =
            "Initialization Info: " +
                    filename +
                    "Invalid"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logTick(tick: Int) {
        var output: String = "Simulation Tick:  $tick"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEmergencyAssigned(id1: Int, id2: Int) {
        var output: String = "Emergency Assignment" + id1 + "assigned to" + id2
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logAssetAllocated(assetId: Int, emergencyId: Int, arrivesInt: Int) {
        var output: String = "Asset Allocation: $assetId allocated to $emergencyId; $arrivesInt\n" +
                "ticks to arrive."
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logAssetReallocated(assetId: Int, emergencyId: Int) {
        var output: String = "Asset Reallocation: $assetId reallocated to $emergencyId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logRequest(requestId: Int, targetBaseId: Int, emergencyId: Int) {
        var output: String = "Asset Request: $requestId sent to $targetBaseId for $emergencyId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logRequestFailed(emergencyId: Int) {
        var output: String = "Request Failed: $emergencyId failed"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logAssetArrived(assetId: Int, vertexId: Int) {
        var output: String = "Asset Arrival: $assetId arrived at $vertexId"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEmergencyHandlingStart(emergencyId: Int) {
        var output: String = "Emergency Handling Start: $emergencyId handling started"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEmergencyResolve(emergencyId: Int) {
        var output: String = "Emergency Resolved: $emergencyId resolved"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEmergencyFailed(emergencyId: Int) {
        var output: String = "Emergency Failed: $emergencyId failed"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEventEnded(eventId: Int) {
        var output: String = "Event Ended: $eventId ended"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEventTriggered(eventId: Int) {
        var output: String = "Event Triggered: $eventId triggered"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logAssetRerouted(assetsAmount: Int) {
        var output: String = "Assets Rerouted: $assetsAmount"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logSimulationEnded() {
        var output: String = "Simulation End"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logNumberOfReroutedAssets(reroutedAssetsAmount: Int) {
        var output: String = "Simulation Statistics: $reroutedAssetsAmount assets rerouted"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logNumberOfRecievedEmergencies(recievedEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $recievedEmergenciesAmount received emergencies"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logNumberOfOngoingEmergencies(ongoingEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $ongoingEmergenciesAmount ongoing emergencies"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logNumberOfFailedEmergencies(failedEmergenciesAmount: Int) {
        var output: String = "Simulation Statistics: $failedEmergenciesAmount failed emergencies"
        if (outputFile == null) {
            println(output)
            return
        }
        val file = File(outputFile)
        file.writeText(output, Charsets.UTF_8)
    }

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
