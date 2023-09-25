package de.unisaarland.cs.se.selab.controller.events

abstract class RoadEvent: Event() {
    val source: Int? = null
    val target: Int? = null
    val factor: Int? = null
}