package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.map.PrimaryType

/** used to create Event  */
object EventFactory {
    /** returns a VehicleEvent */
    fun createRepairVehicle(eventId: Int, vehicleId: Int, start: Int, duration: Int): Event {
        return VehicleEvent(vehicleId, eventId, start, duration)
    }

    /** returns a new TrafficJam */
    fun createTrafficJam(eventId: Int, start: Int, duration: Int, sourceId: Int, targetId: Int, factor: Int): Event {
        return TrafficJam(eventId, start, duration, sourceId, targetId, factor)
    }

    /**returns RushHour */
    fun createRushHour(eventId: Int, start: Int, duration: Int, roadTypes: List<PrimaryType>, factor: Int): Event {
        return RushHour(eventId, start, duration, roadTypes, factor)
    }

    /**returns ConstructionSite event */
    fun createConstructionSite(
        eventId: Int,
        start: Int,
        duration: Int,
        sourceId: Int,
        targetId: Int,
        factor: Int,
        oneway: Boolean
    ): Event {
        return ConstructionSite(eventId, start, duration, sourceId, targetId, factor, oneway)
    }

    /** returns a RoadClosure event */
    fun createRoadClosure(eventId: Int, start: Int, duration: Int, sourceId: Int, targetId: Int): Event {
        return RoadClosure(eventId, start, duration, sourceId, targetId)
    }
}
