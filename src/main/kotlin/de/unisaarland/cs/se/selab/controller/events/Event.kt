package de.unisaarland.cs.se.selab.controller.events

abstract class Event(val id:Int, eventType: EventType, val duration: Int) {


    var status: EventStatus = EventStatus.NOT_SCHEDULED

    /**
     * Changes weight and or behaviour of roads as well as vehicles.
     * */
    abstract fun applyEffect(): Unit

    abstract fun decrementTimer(): Unit
}

enum class EventType {
    RUSH_HOUR,
    TRAFFIC_JAM,
    CONSTRUCTION_SITE,
    ROAD_CLOSURE,
    VEHICLE_UNAVAILABLE

}

enum class EventStatus {
    NOT_SCHEDULED,
    SCHEDULED,
    ACTIVE,
    FINISHED
}
