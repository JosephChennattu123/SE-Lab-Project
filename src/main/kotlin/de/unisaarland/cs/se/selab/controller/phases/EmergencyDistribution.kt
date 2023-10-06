package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.controller.events.EventStatus
import de.unisaarland.cs.se.selab.controller.events.EventType
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.EmergencyType
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge
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
        val currentEmergencies = model.getEmergenciesOfCurrentTick().sortedBy { it.id }

        // Get the graph from the model.
        val graph = model.graph

        for (emergency in currentEmergencies) {
            // Iterate over emergencies and get the nearest base using one of the Dijkstra methods.
            var nearestBaseVertexId: Int? = null
            val loc = emergency.location
            val edge = model.graph.getEdge(loc)
            val activeEvent = edge.activeEventId?.let { model.getEventById(it) }
            if (activeEvent != null) {
                handleActiveRoadClosureEvent(activeEvent, edge, model)
            }
            if (emergency.type == EmergencyType.CRIME) {
                nearestBaseVertexId = Dijkstra.getNearestBaseVertexIdToEdge(graph, loc, BaseType.POLICE_STATION)
            }
            if (emergency.type == EmergencyType.ACCIDENT || emergency.type == EmergencyType.FIRE) {
                nearestBaseVertexId = Dijkstra.getNearestBaseVertexIdToEdge(graph, loc, BaseType.FIRE_STATION)
            }
            if (emergency.type == EmergencyType.MEDICAL) {
                nearestBaseVertexId = Dijkstra.getNearestBaseVertexIdToEdge(graph, loc, BaseType.HOSPITAL)
            }

            // Assign the emergency ID to the base.
            if (nearestBaseVertexId == null) {
                error("a base should exist at this stage")
            }
            val baseId = model.graph.vertices.getValue(nearestBaseVertexId).baseId
            model.getBaseById(baseId as Int)?.addEmergency(emergency.id)
            emergency.mainBaseID = baseId

            // Add the emergency to the list of active emergencies in the model.
            model.addToAssignedEmergencies(emergency.id)

            // Log the assignment.
            Logger.logEmergencyAssigned(emergency.id, baseId)
        }
    }

    /**used to solve the issue for road Closure on a road to assign emergency */
    private fun handleActiveRoadClosureEvent(activeEvent: Event, edge: Edge, model: Model) {
        if (activeEvent.eventType == EventType.ROAD_CLOSURE) {
            model.roadToPostponedEvents[edge.edgeId]?.let { postponedEventsList ->
                if (postponedEventsList.isNotEmpty()) {
                    postponedEventsList.add(edge.activeEventId as Int)
                    activeEvent.status = EventStatus.SCHEDULED
                    edge.activeEventId = null
                }
            }
        }
    }
}
