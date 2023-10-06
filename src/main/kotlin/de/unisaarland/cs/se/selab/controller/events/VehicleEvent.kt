package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.vehicle.Vehicle
import de.unisaarland.cs.se.selab.model.vehicle.VehicleStatus
import de.unisaarland.cs.se.selab.util.Logger

/** @param vehicleId
 * @param id
 * @param start
 * @param duration
 */
class VehicleEvent(val vehicleId: Int, id: Int, start: Int, override var duration: Int) :
    Event(id, EventType.VEHICLE_UNAVAILABLE, start, duration) {

    override fun applyEffect(model: Model) {
        requireNotNull(model.getVehicleById(vehicleId)) {
            "vehicle id should not be" +
                " null and vehicle should exist"
        }

        val vehicleObject: Vehicle = model.getVehicleById(vehicleId) as Vehicle
        if (canBeTriggered(model)) {
            status = EventStatus.ACTIVE
            vehicleObject.status = VehicleStatus.UNAVAILABLE
            vehicleObject.isUnavailable = true
            Logger.logEventStatus(id, true)
            // removed from postponed if already scheduled.
            if (status == EventStatus.SCHEDULED) {
                (model.vehicleToPostponedEvents[vehicleId] as MutableList<Event>).remove(
                    model.getEventById(id) as Event
                )
            }
        } else if (status == EventStatus.NOT_SCHEDULED) {
            status = EventStatus.SCHEDULED
            if (model.vehicleToPostponedEvents[vehicleId] == null) {
                val newEventMutableList: MutableList<Event> = mutableListOf()
                val event = model.getEventById(id) as Event
                newEventMutableList.add(event)
                model.vehicleToPostponedEvents[vehicleId] = newEventMutableList
                return
            }
            model.vehicleToPostponedEvents[vehicleId]?.add(model.getEventById(id) as Event)
        }
    }

    private fun canBeTriggered(model: Model): Boolean {
        val otherEventPresent = model.getVehicleById(vehicleId)?.activeEventID != null
        val isAtBase = model.getVehicleById(vehicleId)?.status == VehicleStatus.AT_BASE
        return !otherEventPresent && isAtBase
    }
    override fun decrementTimer() {
        duration--
    }

    override fun removeEffect(model: Model) {
        val vehicleObject: Vehicle = model.getVehicleById(vehicleId) as Vehicle
        if (vehicleObject.status == VehicleStatus.UNAVAILABLE) {
            vehicleObject.status = VehicleStatus.AT_BASE
            vehicleObject.isUnavailable = false
            status = EventStatus.FINISHED
            model.eventOccurred = true
            Logger.logEventStatus(id, false)
        }
    }
}
