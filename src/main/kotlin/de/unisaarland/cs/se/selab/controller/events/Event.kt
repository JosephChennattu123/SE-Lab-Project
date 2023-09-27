package de.unisaarland.cs.se.selab.controller.events

/**
 * Event superclass.
 * */
abstract class Event(val id: Int, val eventType: EventType, val start: Int, val duration: Int) {

    var status: EventStatus = EventStatus.NOT_SCHEDULED

    /**
     * Changes weight and or behaviour of roads as well as vehicles.
     * */
    abstract fun applyEffect(): Unit

    /**
     * Decrements the timer of the event.
     * */
    abstract fun decrementTimer(): Unit
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
    FINISHED
}
