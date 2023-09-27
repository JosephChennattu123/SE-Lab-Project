package de.unisaarland.cs.se.selab.controller.events

abstract class RoadEvent(id: Int, eventType: EventType, start: Int, duration: Int) :
    Event(id, eventType, start, duration) {
    var source: Int? = null
    var target: Int? = null
    var factor: Int? = null
}
