package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.map.Edge

/**
 * Event superclass.
 * */
abstract class Event(val id: Int, val eventType: EventType, val start: Int, open var duration: Int) {

    var status: EventStatus = EventStatus.NOT_SCHEDULED

    /**
     * Changes weight and or behaviour of roads as well as vehicles.
     * */
    abstract fun applyEffect(model: Model)

    /**
     * Decrements the timer of the event.
     * */
    abstract fun decrementTimer()

    /**
     * changes removes effect of event on the required object
     * */
    abstract fun removeEffect(model: Model)

    /** puts the id of event into the road to postponed event list*/
    fun putInPostponeLists(roadToPostponedEvents: MutableMap<Int, MutableList<Int>>, currentEdge: Edge) {
        if (roadToPostponedEvents[currentEdge.edgeId] == null) {
            val newEventMutableList: MutableList<Int> = mutableListOf()
            newEventMutableList.add(id)
            roadToPostponedEvents[currentEdge.edgeId] = newEventMutableList
        }
        // case in which new event is added to a pre-existing list of Events
        else {
            val roadEventMutableList: MutableList<Int> = roadToPostponedEvents[currentEdge.edgeId]
                as MutableList<Int>
            roadEventMutableList.add(id)
            // apparently not necessary model.vehicleToPostponedEvents[vehicleId] = VehicleEventMutableList
        }
    }
}

/**
 * represents the type of event
 * */
enum class EventType {
    RUSH_HOUR, TRAFFIC_JAM, CONSTRUCTION_SITE, ROAD_CLOSURE, VEHICLE_UNAVAILABLE;

    companion object {
        /**
         * deserializes a string to an EventType
         * @param value the string to be deserialized
         * @return the deserialized EventType
         * */
        fun fromString(value: String): EventType? {
            return when (value) {
                "RUSH_HOUR" -> RUSH_HOUR
                "TRAFFIC_JAM" -> TRAFFIC_JAM
                "CONSTRUCTION_SITE" -> CONSTRUCTION_SITE
                "ROAD_CLOSURE" -> ROAD_CLOSURE
                "VEHICLE_UNAVAILABLE" -> VEHICLE_UNAVAILABLE
                else -> null
            }
        }
    }
}

/**
 * The status of an event.
 * */
enum class EventStatus {
    /**
     * The event is not scheduled yet.
     * */
    NOT_SCHEDULED,

    /**
     * The event is scheduled but not active yet.
     * */
    SCHEDULED,

    /**
     * The event is active and the effect of it is applied.
     * */
    ACTIVE,

    /**
     * The event is finished and the effect of it is not applied anymore.
     * */
    FINISHED,

    /** RUSH HOUR BABY */

    RUSH_HOUR
}
