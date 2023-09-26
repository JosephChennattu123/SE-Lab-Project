package de.unisaarland.cs.se.selab.util
import java.io.File
object Logger {

    val filename: String? = null


    public fun logParsingValidationSuccess(filename:String) {
        var output : String =
            "Initialization Info: " +
                    filename +
                    "successfully parsed and validated"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)

    }
    public fun logFileInvalid(filename: String){
        var output : String =
            "Initialization Info: "+
                    filename +
                "Invalid"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logTick(tick:Int){
        var output : String = "Simulation Tick:  $tick"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logEmergencyAssigned(id1:Int,id2:Int)
    {
        var output : String = "Emergency Assignment" + id1 + "assigned to" + id2
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logAssetAllocated(assetId:Int,emergencyId:Int,arrivesInt: Int)
    {
        var output : String = "Asset Allocation: $assetId allocated to $emergencyId; $arrivesInt\n" +
                "ticks to arrive."
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logAssetReallocated(assetId:Int,emergencyId:Int)
    {
        var output : String = "Asset Reallocation: $assetId reallocated to $emergencyId"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logRequest(requestId:Int,targetBaseId:Int,emergencyId: Int)
    {
        var output : String = "Asset Request: $requestId sent to $targetBaseId for $emergencyId"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logRequestFailed(emergencyId: Int)
    {
        var output : String = "Request Failed: $emergencyId failed"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logAssetArrived(assetId: Int,vertexId:Int)
    {
        var output: String = "Asset Arrival: $assetId arrived at $vertexId"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logEmergencyHandlingStart(emergencyId: Int)
    {
        var output: String = "Emergency Handling Start: $emergencyId handling started"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logEmergencyResolve(emergencyId: Int)
    {
        var output: String = "Emergency Resolved: $emergencyId resolved"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logEmergencyFailed(emergencyId: Int)
    {
        var output : String = "Emergency Failed: $emergencyId failed"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }

    public fun logEventEnded(eventId:Int)
    {
        var output : String = "Event Ended: $eventId ended"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logEventTriggered(eventId:Int)
    {
        var output : String = "Event Triggered: $eventId triggered"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logAssetRerouted(assetsAmount: Int){
        var output : String = "Assets Rerouted: $assetsAmount"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logSimulationEnded()
    {
        var output : String = "Simulation End"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logNumberOfReroutedAssets(reroutedAssetsAmount : Int){
        var output : String = "Simulation Statistics: $reroutedAssetsAmount assets rerouted"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logNumberOfRecievedEmergencies(recievedEmergenciesAmount : Int){
        var output : String = "Simulation Statistics: $recievedEmergenciesAmount received emergencies"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logNumberOfOngoingEmergencies(ongoingEmergenciesAmount : Int){
        var output : String = "Simulation Statistics: $ongoingEmergenciesAmount ongoing emergencies"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logNumberOfFailedEmergencies(failedEmergenciesAmount : Int){
        var output : String = "Simulation Statistics: $failedEmergenciesAmount failed emergencies"
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }
    public fun logNumberOfResolvedEmergencies(resolvedEmergenciesAmount : Int){
        var output : String = "Simulation Statistics: $resolvedEmergenciesAmount resolved emergencies."
        if(filename == null)
        {
            println(output)
            return
        }
        val file = File(filename)
        file.writeText(output, Charsets.UTF_8)
    }




}