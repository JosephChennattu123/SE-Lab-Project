package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus
import de.unisaarland.cs.se.selab.util.Dijkstra

class Reroute {
    /**
     * Iterates through all vehicles in the model and calculates new paths for all currently moving vehicles
     * @param model The model
     * */
    fun execute(model: Model) {
        for (v : Vehicle in model.getSortedVehicleList()) {
            if (v.status == VehicleStatus.TO_EMERGENCY || v.status == VehicleStatus.RETURNING) {
                v.positionTracker.path = Dijkstra.getShortestPathFromVertexToEdge()
            }
        }
    }
}
