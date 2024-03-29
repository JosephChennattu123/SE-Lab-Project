package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.EventType
import de.unisaarland.cs.se.selab.model.map.PrimaryType

/**
 * Collects the info to construct events
 *
 * @param id the id of the event
 * @param tick the tick when the event happens
 * @param eventType the type of the event
 * @param duration the duration of the event
 * @param roadTypes the type of the road where the event happens
 * @param factor the factor that will be applied to the road
 * @param oneWayStreet whether the road should be set to one way
 * @param source the source vertex of the event
 * @param target the target vertex of the event
 * @property vehicleId the id of the vehicle that is involved in the event
 */
class EventInfo(
    id: Int,
    val tick: Int,
    val eventType: EventType,
    val duration: Int,
    val roadTypes: List<PrimaryType>?,
    val factor: Int?,
    val oneWayStreet: Boolean?,
    val source: Int?,
    val target: Int?,
) : BasicInfo(id) {
    var vehicleId: Int? = null

    override val infoMap: MutableMap<String, Any?> =
        mutableMapOf(
            ID to id,
            TICK to tick,
            EVENT_TYPE to eventType,
            DURATION to duration,
            ROAD_TYPES to roadTypes,
            VEHICLE_ID to vehicleId,
            FACTOR to factor,
            ONE_WAY_STREET to oneWayStreet,
            SOURCE to source,
            TARGET to target
        )
}
