package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model
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
        val drivingVehicles = model.getSortedVehicleList().filter {
            it.status == VehicleStatus.TO_EMERGENCY || it.status == VehicleStatus.RETURNING
        }
        drivingVehicles.forEach {
            val vPosT = it.positionTracker
            val vPath = vPosT.path.vertexPath
            vPosT.path = Dijkstra.getShortestPathFromEdgeToEdge(
                model.graph,
                vPath[vPosT.currentVertexIndex],
                vPath[vPosT.currentVertexIndex + 1],
                vPosT.positionOnEdge,
                model.getAssignedEmergencyById(it.emergencyID!!)!!.location,
                it.height
            )
        }
    }
}
