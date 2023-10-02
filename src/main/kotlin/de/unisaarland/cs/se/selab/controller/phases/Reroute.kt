package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.VehicleStatus
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
        drivingVehicles.forEach {
            val vPosT = it.positionTracker
            val vPath = vPosT.path.vertexPath
            val oldPath = vPosT.path
            assert(it.emergencyID != null)
            val emergencyID = it.emergencyID as Int
            assert(model.getAssignedEmergencyById(emergencyID) != null)
            val emergency = model.getAssignedEmergencyById(emergencyID) as Emergency
            vPosT.path = Dijkstra.getShortestPathFromEdgeToEdge(
                model.graph,
                vPath[vPosT.currentVertexIndex],
                vPath[vPosT.currentVertexIndex + 1],
                vPosT.positionOnEdge,
                emergency.location,
                it.height
            )
            if (vPosT.path != oldPath) {
                Logger.logAssetRerouted(it.vehicleID)
            }
        }
    }
}
