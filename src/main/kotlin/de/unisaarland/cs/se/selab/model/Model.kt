package de.unisaarland.cs.se.selab.model

import de.unisaarland.cs.se.selab.controller.events.Event

class Model {
    fun getAssignedEmergencyById(emId: Int): Emergency {
        // TODO
        TODO()
    }

    fun getAssignedEmergenciesByIds(emIds: List<Int>): List<Emergency> {
        //TODO
        return listOf()
    }

    fun addToAssignedEmergencies(emId: Int): Unit {
        //TODO
    }

    fun removeFromAssignedEmergencies(emId: Int): Unit {
        //TODO
    }

    fun getCurrentEmergencies(): List<Emergency> {
        //TODO
        return listOf()
    }

    fun getAssignedEmergencies(): List<Emergency> {
        //TODO
        return listOf()
    }

    fun getBasebyId(id: Int): Base {
        //TODO
        TODO()
    }

    fun getAllVehicles(): Map<Int, Vehicle> {
        //TODO
        TODO()
    }

    fun getVehicleById(vId: Int): Vehicle {
        //TODO
        TODO()
    }

    fun getVehiclesByIds(vIds: List<Int>): List<Vehicle> {
        //TODO
        return listOf()
    }

    fun getCurrentEvents(): List<Event> {
        //TODO
        return listOf()
    }

    fun getEvents(): List<Event> {
        //TODO
        return listOf()
    }

    fun getEventById(evId: Int): Event {
        //TODO
        TODO()
    }

    fun getEventsByIds(evIds: List<Int>): List<Event> {
        //TODO
        return listOf()
    }

    fun removeFromCurrentEvents(evId: Int) {
        //TODO
    }

    fun addRoadEvent(roadId: Int, eventId: Event) {
        //TODO
    }

    fun addVehicleEvent(vehicleId: Int, eventId: Event) {
        //TODO
    }

    fun addRequest(request: Request) {
        //TODO
    }

    fun incrementTick() {
        //TODO
    }
}