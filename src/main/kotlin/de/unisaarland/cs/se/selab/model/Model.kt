package de.unisaarland.cs.se.selab.model

import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.model.map.Graph

/**@param graph
 * @param maxTick
 * @param vehicles
 * @param vehicleToBase
 * @param emergencies
 * */

class Model(
    val graph: Graph,
    val maxTick: Int?, // optional command-line argument
    val bases: Map<Int, Base>,
    val vehicles: Map<Int, Vehicle>,
    val vehicleToBase: Map<Int, Int>,
    val emergencies: Map<Int, Emergency>,
    val tickToEmergencyId: Map<Int, List<Int>>,
    val events: Map<Int, Event>,
    var tickToEventId: Map<Int, List<Int>>
) {
    var currentTick: Int = 0
    var assignedEmergencies: MutableList<Int> = mutableListOf()
    var currentEvents: MutableList<Int> = mutableListOf()
    var roadToPostponedEvents: MutableMap<Int, MutableList<Event>> = mutableMapOf()
    var vehicleToPostponedEvents: MutableMap<Int, MutableList<Event>> = mutableMapOf()
    var requests: MutableList<Request> = mutableListOf()
    var numReroutedAssets: Int = 0
    var numFailedEmergencies: Int = 0
    var numResolvedEmergency: Int = 0

    /** returns emergency object with respect to its id */
    fun getAssignedEmergencyById(emId: Int): Emergency? {
        return emergencies[emId]
    }

    /** returns a list of Emergency objects when given a list of emergency ids*/
    fun getAssignedEmergenciesByIds(emIds: List<Int>): List<Emergency> {
        return emIds.mapNotNull { emergencies[it] }
    }

    /** adds a new emergency to the list of current assigned emergencies */
    fun addToAssignedEmergencies(emId: Int) {
        assignedEmergencies.add(emId)
    }

    /** removed an emergency from the list of assigned emergencies */
    fun removeFromAssignedEmergencies(emId: Int) {
        assignedEmergencies.remove(emId)
    }

    /** returns list of started Emergencies. To people implementing/testing Emergency Distribution
     *  please use this function to fetch the
     * emergency started in this tick*/
    fun getCurrentEmergencies(): List<Emergency> {
        val listOfStartedEmergencies: List<Int> = tickToEmergencyId[currentTick] ?: listOf()
        return listOfStartedEmergencies.mapNotNull { emergencies[it] }
    }

    /** Returns list of assigned emergencies. To people implementing/testing phases apart from
     * Emergency Distribution please use this function to retrieve all active emergencies. Logic
     * is that during emergency distribution will add the activated emergency into the assigned
     * list immediately
     * */

    fun getAssignedEmergenciesObjects(): List<Emergency> {
        return assignedEmergencies.mapNotNull { emergencies[it] }
    }

    /** returns a singular base for a base id */
    fun getBaseById(baseId: Int): Base? {
        return bases[baseId]
    }

    /**
     * @return all the vehicles sorted by id.
     * */
    fun getSortedVehicleList(): List<Vehicle> {
        return vehicles.entries.sortedBy { it.key }.map { it.value }.toList()
    }

    /** returns a single vehicle for an id */
    fun getVehicleById(vId: Int): Vehicle? {
        return vehicles[vId]
    }

    /** returns a list of vehicles for a list of vehicle ids */
    fun getVehiclesByIds(vIds: List<Int>): List<Vehicle> {
        return vIds.mapNotNull { vehicles[it] }
    }

    /** @returns list of all current events */
    fun getCurrentEvents(): List<Event> {
        return currentEvents.mapNotNull { events[it] }
    }

    /** @returns all events */
    fun getEvents(): List<Event> {
        return events.keys.mapNotNull { events[it] }
    }

    /** returns singular event for an event id */
    fun getEventById(evId: Int): Event? {
        return events[evId]
    }

    /** returns a list of events in return for a list of event ids */
    fun getEventsByIds(evIds: List<Int>): List<Event> {
        return evIds.mapNotNull { events[it] }
    }

    /** removes an event from list of current events */
    fun removeFromCurrentEvents(evId: Int) {
        currentEvents.remove(evId)
    }

    /** add roadEvent to a map of road id to postponed events */
    fun addRoadEvent(roadId: Int, eventId: Event) {
        val roadEventsPost: MutableList<Event> = roadToPostponedEvents[roadId] ?: mutableListOf()
        roadEventsPost.add(eventId)
        roadToPostponedEvents[roadId] = roadEventsPost
    }

    /** add vehicleEvent to a map of vehicle id to postponed events */
    fun addVehicleEvent(vehicleId: Int, eventId: Event) {
        val vehicleEventsPost: MutableList<Event> =
            vehicleToPostponedEvents[vehicleId] ?: mutableListOf()
        vehicleEventsPost.add(eventId)
        vehicleToPostponedEvents[vehicleId] = vehicleEventsPost
    }

    /** add request to the list of Request */
    fun addRequest(request: Request) {
        requests.add(request)
    }

    /** increment ticks */
    fun incrementTick() {
        currentTick++
    }
}
