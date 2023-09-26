package de.unisaarland.cs.se.selab.util

class Logger {


    public fun logParsingValidationSuccess(filename:String) {

    }
    public fun logFileInvalid(filename: String){

    }
    public fun logTick(tick:Int){

    }
    public fun logEmergencyAssigned(id1:Int,id2:Int)
    {
        var output : String = "Emergency Assignment" + id1 + "assigned to" + id2
        println(output)
    }
    public fun logAssetAllocated(assetId:Int,emergencyId:Int,arrivesInt: Int)
    {

    }
    public fun logAssetReallocated(assetId:Int,emergencyId:Int)
    {

    }
    public fun logRequest(requestId:Int,targetBaseId:Int,emergencyId: Int)
    {

    }
    public fun logRequestFailed(emergencyId: Int)
    {

    }
    public fun logAssetArrived(assetId: Int,vertexId:Int)
    {

    }
    public fun logEmergencyHandlingStart(emergencyId: Int)
    {

    }
    public fun logEmergencyResolve(emergencyId: Int)
    {

    }
    public fun logEmergencyFailed(emergencyId: Int)
    {

    }

    public fun logEventEnded(eventId:Int)
    {

    }
    public fun logEventTriggered(eventId:Int)
    {

    }
    public fun logAssetRerouted(assetsAmount: Int){

    }
    public fun logSimulationEnded()
    {

    }
    public fun logNumberOfReroutedAssets(reroutedAssetsAmount : Int){

    }
    public fun logNumberOfRecievedEmergencies(recievedEmergenciesAmount : Int){

    }
    public fun logNumberOfOngoingEmergencies(ongoingEmergenciesAmount : Int){

    }
    public fun logNumberOfFailedEmergencies(failedEmergenciesAmount : Int){

    }
    public fun logNumberOfResolvedEmergencies(resolvedEmergenciesAmount : Int){

    }




}