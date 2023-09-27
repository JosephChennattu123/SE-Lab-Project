package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.*
import de.unisaarland.cs.se.selab.util.AssetManager
import de.unisaarland.cs.se.selab.util.Dijkstra

class RequestProcessing {

    /**
     * @params:the model of the simulation
     * Executes the request processing phase,which will process all current requests in our model and attempt to allocate the required assets for the emergency of the request
     * */
    fun execute(model: Model): Unit {
        //iterate over each request
        for (req in model.requests) {
            //for each request get emergencyId, baseId and the corresponding concrete objects from model

            val reqEmergency = model.getAssignedEmergencyById(req.emergencyId)
            val base = model.getBaseById(req.mainBaseId)

            // get the list of vehicleIds belonging to the base and then get the list of concrete vehicles from the model
            val vehiclesId = base!!.vehicles
            var vehicles = model.getVehiclesByIds(vehiclesId)

            //filter out the vehicles that are not in base by checking the status
            vehicles = vehicles.filter { it.status == VehicleStatus.AT_BASE }

            //pass the list of vehicle together with the model and emergency to the allocateAssetsToEmergency() method of the AssetManager
            if (reqEmergency != null) {
                AssetManager.allocateAssetsToEmergency(model, reqEmergency, vehicles)
            }

            //check if the emergency requirements have been fulfilled by calling isFulfilled on the emergency
            if (reqEmergency != null) {
                if (reqEmergency.isFulfilled()) {
                    continue

                } else {
                    //get the set of already processed bases from the request and  add the baseId of the the base that we tried to send assets from
                    val mutableSet = mutableSetOf<Int>()
                    mutableSet.addAll(req.processesBases)
                    if (base != null) {
                        mutableSet.add(base.baseId)
                    }
                    //req.processesBases = mutableSet

                    //find the next nearest base using the appropriate dijkstra method with the same basetype
                    var nextNearestBase: Int? = null
                    if (reqEmergency.type == EmergencyType.CRIME) {
                        nextNearestBase = Dijkstra.getNextNearestBase(
                            model.graph,
                            base!!.vertexID,
                            BaseType.POLICE_STATION,
                            mutableSet
                        )
                    }
                    if (reqEmergency.type == EmergencyType.FIRE) {
                        nextNearestBase =
                            Dijkstra.getNextNearestBase(model.graph, base!!.vertexID, BaseType.FIRE_STATION, mutableSet)
                    }
                    if (reqEmergency.type == EmergencyType.MEDICAL || reqEmergency.type == EmergencyType.ACCIDENT) {
                        nextNearestBase =
                            Dijkstra.getNextNearestBase(model.graph, base!!.vertexID, BaseType.HOSPITAL, mutableSet)
                    }
                    if (nextNearestBase == null) {
                        continue
                    }
                    //create a new request
                    var newRequest = Request.createNewRequest(base.baseId, reqEmergency.id, nextNearestBase, mutableSet)

                    //add the request at the end to the list of the request we are currently iterating over
                    model.requests.add(newRequest.requestId, newRequest)


                }


            }
        }


    }
}
