package de.unisaarland.cs.se.selab.controller.events

import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleStatus
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
        // applies effect if status of vehicle AT_BASE or else adds to list of postponed events
        if (vehicleObject.status == VehicleStatus.AT_BASE) {
            vehicleObject.status = VehicleStatus.UNAVAILABLE
            status = EventStatus.ACTIVE
            model.currentEvents.add(id)
            if (model.vehicleToPostponedEvents[vehicleId] != null &&
                (model.vehicleToPostponedEvents[vehicleId] as MutableList).isNotEmpty()
            ) {
                (model.vehicleToPostponedEvents[vehicleId] as MutableList<Event>).remove(
                    model.getEventById(id) as Event
                )
            }
            Logger.logEventStatus(id, true)
            vehicleObject.isUnavailable = true
        } else {
            // case in which first postponed event for a vehicle
            if (model.vehicleToPostponedEvents[vehicleId] == null) {
                val newEventMutableList: MutableList<Event> = mutableListOf()
                val event = model.getEventById(id) as Event
                newEventMutableList.add(event)
                model.vehicleToPostponedEvents[vehicleId] = newEventMutableList
                status = EventStatus.SCHEDULED
            }
            // case in which new event is added to a pre-existing list of Events
            else {
                val vehicleEventMutableList: MutableList<Event> = model.vehicleToPostponedEvents[vehicleId]
                    as MutableList<Event>
                vehicleEventMutableList.add(model.getEventById(id) as Event)
                // apparently not necessary model.vehicleToPostponedEvents[vehicleId] = VehicleEventMutableList
            }
        }
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
            model.currentEvents.remove(id)
            Logger.logEventStatus(id, true)
        }
    }
}
