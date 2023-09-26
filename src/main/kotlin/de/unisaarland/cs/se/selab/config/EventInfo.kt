package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.EventType
import de.unisaarland.cs.se.selab.model.map.PrimaryType

/**
 * Collects the info to construct events
 *
 * @param id the id of event
 * @param tick the tick in which the event is scheduled
 * @param type the type of the event
 * @param duration the duration of the event
 */
class EventInfo(val id: Int, val tick: Int, val type: EventType, val duration: Int) {
    var roadTypes: List<PrimaryType>? = null
    var factor: Int? = null
    var oneWayStreet: Boolean? = null
    var source: Int? = null
    var target: Int? = null
    var vehicleId: Int? = null
}
