package de.unisaarland.cs.se.selab.model

class Model {
    fun getAssignedEmergencyById(emId: Int): Emergency {

        //TODO

    }

    fun getAssignedEmergenciesByIds(emIds: List<Int>): List<Emergency> {
        //TODO
    }

    fun addToAssignedEmergencies(emId: Int): Unit {
        //TODO
    }

    fun removeFromAssignedEmergencies(emId: Int): Unit {
        //TODO
    }

    fun getCurrentEmergencies(): List<Emergency> {
        //TODO
    }

    fun getAssignedEmergencies(): List<Emergency> {
        //TODO
    }

    fun getBasebyId(id: Int): Base {
        //TODO
    }

    fun getAllVehicles(): Map<Int, Vehicle> {
        //TODO
    }

    fun getVehicleById(vId: Int): Vehicle {
        //TODO
    }

    fun getVehiclesByIds(vIds: List<Int>): List<Vehicle> {
        //TODO
    }

    fun getCurrentEvents(): List<Event> {
        //TODO
    }

    fun getEvents(): List<Event> {
        //TODO
    }

    fun getEventById(evId: Int): Event {
        //TODO
    }

    fun getEventsByIds(evIds: List<Int>): List<Event> {
        //TODO
    }

    fun removeFromCurrentEvents(evId: Int): Unit {
        //TODO
    }

    fun addRoadEvent(roadId: Int, eventId: Event): unit {
        //TODO
    }

    fun addVehicleEvent(vehicleId: Int, eventId: Event): unit {
        //TODO
    }

    fun addRequest(request: Request): Unit {
        //TODO
    }

    fun incrementTick(): Unit {
        //TODO
    }
}