package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.vehicle.VehicleStatus
import de.unisaarland.cs.se.selab.util.Dijkstra
import de.unisaarland.cs.se.selab.util.Logger

/**
 * Reroute Phase.
 * Checks if rerouting of vehicles is needed when events could have occurred and
 * reroutes them if necessary.
 */
class Reroute {
    /**
     * Iterates through all vehicles in the model and calculates new paths for all currently moving vehicles
     * @param model The model
     * */
    fun execute(model: Model) {
        val drivingVehicles = model.getSortedVehicleList().filter {
            it.status == VehicleStatus.TO_EMERGENCY || it.status == VehicleStatus.RETURNING
        }
        var numberOfReroutedVehicles = 0
        for (vehicle in drivingVehicles) {
            val currentVertex = vehicle.getCurrentVertexID() ?: error("currentVertex is null")
            val nextVertex = vehicle.getNextVertexID() ?: error("nextVertex is null")
            val currentDistance = vehicle.getDistanceOnEdge() ?: error("currentDistance is null")
            val destination = vehicle.getDestination() ?: error("destination is null")
            if (vehicle.setNewPath(
                    Dijkstra.getShortestPathFromEdgeToVertex(
                            model.graph,
                            currentVertex,
                            nextVertex,
                            destination,
                            currentDistance,
                            vehicle.height
                        )
                )
            ) {
                // Logger.logAssetRerouted(vehicle.vehicleID)
                numberOfReroutedVehicles++
            }
        }
        if (numberOfReroutedVehicles > 0) {
            Logger.logAssetRerouted(numberOfReroutedVehicles)
        }
    }
}
