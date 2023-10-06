package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyType
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Request
import de.unisaarland.cs.se.selab.model.vehicle.VehicleStatus
import de.unisaarland.cs.se.selab.util.AssetManager
import de.unisaarland.cs.se.selab.util.Dijkstra

/**
 * The `RequestProcessing` class manages the request processing phase of a simulation.
 * It processes current requests in the simulation model and attempts to allocate the required assets
 * for each emergency request.
 */
class RequestProcessing {

    /**
     * Executes the request processing phase.
     *
     * This method processes all current requests in the simulation model and attempts
     * to allocate the required assets for each emergency request.
     *
     * @param model The model of the simulation.
     */

    fun execute(model: Model) {
        // Iterate over each request.
        val iterator = model.requests.listIterator()

        while (iterator.hasNext()) {
            val req = iterator.next()
            // For each request, retrieve the emergencyId, baseId, and their corresponding
            // concrete objects from the model.
            val reqEmergency = model.getAssignedEmergencyById(req.emergencyId)
            val base = model.getBaseById(req.targetBaseId) ?: error("fuck!")

            // get the list of vehicleIds belonging to the base and then get the list of concrete
            // vehicles from the model
            val vehiclesId = base.vehicles
            // filter out the vehicles that are not in base by checking the status
            val vehicles = model.getVehiclesByIds(vehiclesId)
                .filter { it.status == VehicleStatus.AT_BASE } as MutableList

            // pass the list of vehicle together with the model and emergency
            // to the allocateAssetsToEmergency() method of the AssetManager
            if (reqEmergency != null) {
                AssetManager.allocateAssetsToEmergency(model, reqEmergency, vehicles, false)
            }

            // check if the emergency requirements have been fulfilled by calling isFulfilled on the emergency
            if (reqEmergency != null && !reqEmergency.isFulfilled()) {
                checkEmergencyRequirements(model, reqEmergency, req, base, iterator)
            }
        }
    }

    private fun checkEmergencyRequirements(
        model: Model,
        reqEmergency: Emergency,
        req: Request,
        base: Base,
        iterator: MutableListIterator<Request>
    ) {
        // get the set of already processed bases from the request
        // and  add the baseId of the base that we tried to send assets from
        val excludedBases = mutableSetOf<Int>()
        excludedBases.addAll(req.processedBases)
        excludedBases.add(base.vertexID)
        // req.processesBases = mutableSet

        // find the next nearest base using the appropriate dijkstra method with the same baseType
        var nextNearestBase: Int? = null
        if (reqEmergency.type == EmergencyType.CRIME) {
            nextNearestBase = Dijkstra.getNextNearestBase(
                model.graph,
                base.vertexID,
                BaseType.POLICE_STATION,
                excludedBases
            )
        }
        if (reqEmergency.type == EmergencyType.FIRE) {
            nextNearestBase =
                Dijkstra.getNextNearestBase(model.graph, base.vertexID, BaseType.FIRE_STATION, excludedBases)
        }
        if (reqEmergency.type == EmergencyType.MEDICAL || reqEmergency.type == EmergencyType.ACCIDENT) {
            nextNearestBase =
                Dijkstra.getNextNearestBase(model.graph, base.vertexID, BaseType.HOSPITAL, excludedBases)
        }
        if (nextNearestBase != null) {
            // create a new request
            val newRequest = Request.createNewRequest(
                base.baseId,
                reqEmergency.id,
                model.graph.vertices[nextNearestBase]?.baseId ?: error("bigger fuck"),
                excludedBases
            )

            // add the request at the end to the list of the request we are currently iterating over
            iterator.add(newRequest)
        }
    }
}
