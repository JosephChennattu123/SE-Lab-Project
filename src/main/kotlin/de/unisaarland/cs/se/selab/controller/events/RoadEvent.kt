package de.unisaarland.cs.se.selab.controller.events

abstract class RoadEvent(id: Int, eventType: EventType, duration: Int) : Event(id, eventType, duration) {
    val source: Int? = null
    val target: Int? = null
    val factor: Int? = null
}