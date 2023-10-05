package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.controller.events.EventFactory
import de.unisaarland.cs.se.selab.controller.events.EventType
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.vehicle.Vehicle

/**
 * Validates the events
 */
class EventValidator(jsonParser: JsonParser) : BasicValidator(jsonParser) {

    override val requiredProperties: List<String> = listOf(ID, EVENT_TYPE, TICK, DURATION)
    override val optionalProperties: List<String> = listOf(
        FACTOR,
        ROAD_TYPES,
        ONE_WAY_STREET,
        SOURCE,
        TARGET,
        VEHICLE_ID
    )

    /**
     * Validates the information for events and creates events.
     *
     * @return the list of events created
     */
    fun validate(graph: Graph, vehicles: List<Vehicle>): List<Event>? {
        val jsonParserObj = jsonParser as JsonParser
        val eventInfos = jsonParserObj.parseEvents()
        eventInfos.forEach { eventInfo ->
            val mandatoryFields: List<String> = when (eventInfo.eventType) {
                EventType.RUSH_HOUR -> listOf(ROAD_TYPES, FACTOR)
                EventType.TRAFFIC_JAM -> listOf(SOURCE, TARGET, FACTOR)
                EventType.CONSTRUCTION_SITE -> listOf(SOURCE, TARGET, FACTOR, ONE_WAY_STREET)
                EventType.ROAD_CLOSURE -> listOf(SOURCE, TARGET)
                EventType.VEHICLE_UNAVAILABLE -> listOf(VEHICLE_ID)
            }
            if (!validateSpecificProperties(eventInfo, optionalProperties, mandatoryFields) ||
                !validateFactor(eventInfo) ||
                !validateEdgeAndVerticesExists(eventInfo, graph)
            ) {
                return null
            }
            if (!validateVehicleExist(eventInfo, vehicles)) return null
        }
        return buildEvents(eventInfos)
    }

    private fun buildEvents(eventInfos: List<EventInfo>): List<Event> {
        return eventInfos.map {
            when (it.eventType) {
                EventType.TRAFFIC_JAM -> EventFactory.createTrafficJam(
                    it.id,
                    it.tick,
                    it.duration,
                    it.source as Int,
                    it.target as Int,
                    it.factor as Int
                )

                EventType.RUSH_HOUR -> EventFactory.createRushHour(
                    it.id,
                    it.tick,
                    it.duration,
                    it.roadTypes as List<PrimaryType>,
                    it.factor as Int
                )

                EventType.CONSTRUCTION_SITE -> EventFactory.createConstructionSite(
                    it.id,
                    it.tick,
                    it.duration,
                    it.source as Int,
                    it.target as Int,
                    it.factor as Int,
                    it.oneWayStreet as Boolean
                )

                EventType.ROAD_CLOSURE -> EventFactory.createRoadClosure(
                    it.id,
                    it.tick,
                    it.duration,
                    it.source as Int,
                    it.target as Int
                )

                EventType.VEHICLE_UNAVAILABLE -> EventFactory.createRepairVehicle(
                    it.id,
                    it.tick,
                    it.duration,
                    it.vehicleId as Int
                )
            }
        }.toList()
    }

    /**
     * Checks that the factors are at least 1
     * If no factor needed returns true
     *
     * @return true if the factors are valid
     */
    private fun validateFactor(eventInfo: EventInfo): Boolean {
        if (eventInfo.eventType in listOf(EventType.RUSH_HOUR, EventType.TRAFFIC_JAM, EventType.CONSTRUCTION_SITE)) {
            if (eventInfo.factor != null) {
                return eventInfo.factor >= 1
            }
            return false
        }
        return true
    }

    /**
     * Check that the corresponding edges and vertices exist
     * If no source and target needed returns true
     *
     * @return true if the edges and vertices exist (road events)
     */
    private fun validateEdgeAndVerticesExists(eventInfo: EventInfo, graph: Graph): Boolean {
        if (eventInfo.eventType in listOf(EventType.TRAFFIC_JAM, EventType.CONSTRUCTION_SITE, EventType.ROAD_CLOSURE)) {
            return graph.doesEdgeExist(eventInfo.source as Int, eventInfo.target as Int)
        }
        return true
    }

    /**
     * Checks that the corresponding vehicles exist
     * If no vehicle needed returns true
     * @return true if the vehicles exist (vehicle events)
     */
    private fun validateVehicleExist(eventInfo: EventInfo, vehicles: List<Vehicle>): Boolean {
        if (eventInfo.eventType == EventType.VEHICLE_UNAVAILABLE) {
            if (eventInfo.vehicleId != null) {
                return vehicles.any { it.vehicleID == eventInfo.vehicleId }
            }
            return false
        }
        return true
    }
}
