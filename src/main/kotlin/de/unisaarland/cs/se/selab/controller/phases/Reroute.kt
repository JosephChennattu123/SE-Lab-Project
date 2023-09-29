package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus
import de.unisaarland.cs.se.selab.util.Dijkstra

/***
 * Reroute calculates new paths for all vehicles that are current on a road after an event has occurred
 */
class Reroute {
    /**
     * Iterates through all vehicles in the model and calculates new paths for all currently moving vehicles
     * @param model The model
     * */
    fun execute(model: Model) {
        for (v: Vehicle in model.getSortedVehicleList()) {
            if (v.status == VehicleStatus.TO_EMERGENCY || v.status == VehicleStatus.RETURNING) {
                val vPath = v.positionTracker.path.vertexPath
                v.positionTracker.path = Dijkstra.getShortestPathFromEdgeToEdge(
                    model.graph,
                    vPath[v.positionTracker.currentVertexIndex],
                    vPath[v.positionTracker.currentVertexIndex + 1],
                    v.positionTracker.positionOnEdge,
                    model.getAssignedEmergencyById(v.emergencyID!!)!!.location,
                    v.height
                )
            }
        }
    }
}
