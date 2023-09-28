package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.EmergencyType
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.util.Dijkstra
import de.unisaarland.cs.se.selab.util.Logger

/**
 * The class handles the  distribution of emergencies to the nearest bases
 * depending on the type of emergency and their locations in the simulation model.
 */

class EmergencyDistribution {

    /**
     * Executes the emergency distribution process.
     *
     * This method retrieves the current emergencies for the current simulation tick, determines the nearest
     * base for each emergency type, assigns the emergency to the nearest base, adds it to the active emergencies
     * in the model, and logs the assignment.
     *
     * @param model The simulation model containing emergencies, bases, and the graph.
     */
    fun execute(model: Model) {
        // Get emergencies for the current tick.
        val currentEmergencies = model.getCurrentEmergencies()

        // Get the graph from the model.
        val graph = model.graph

        for (e in currentEmergencies) {
            // Iterate over emergencies and get the nearest base using one of the Dijkstra methods.
            var nearestBaseId: Int? = 0
            val loc = e.location

            if (e.type == EmergencyType.CRIME) {
                nearestBaseId = Dijkstra.getNearestBaseToEdge(graph, loc, BaseType.POLICE_STATION)
            }
            if (e.type == EmergencyType.FIRE) {
                nearestBaseId = Dijkstra.getNearestBaseToEdge(graph, loc, BaseType.FIRE_STATION)
            }
            if (e.type == EmergencyType.ACCIDENT || e.type == EmergencyType.MEDICAL) {
                nearestBaseId = Dijkstra.getNearestBaseToEdge(graph, loc, BaseType.HOSPITAL)
            }

            // Assign the emergency ID to the base.
            model.getBaseById(nearestBaseId as Int)?.addEmergency(e.id)

            // Add the emergency to the list of active emergencies in the model.
            model.addToAssignedEmergencies(nearestBaseId)

            // Log the assignment.
            Logger.logEmergencyAssigned(e.id, nearestBaseId)
        }
    }
}
