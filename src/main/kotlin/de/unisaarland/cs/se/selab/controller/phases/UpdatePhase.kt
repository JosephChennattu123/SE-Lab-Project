package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model

import de.unisaarland.cs.se.selab.model.Vehicle


class UpdatePhase {

    var eventOccured : Boolean
    fun execute(m: Model): Unit {
        // todo
    }

    private fun processVehicles(vehicles: List<Vehicle>, emergencies: List<Emergency>): Unit {
        // todo
    }

    private fun processEmergencies(model: Model): Unit {
        // todo
    }

    private fun processActiveEvents(): Unit {
        // todo
    }

    private fun processPostponedEvents(): Unit {
        // todo
    }

    private fun timeUpdate(model: Model): Unit {
        // todo
    }

    private fun printLog(vehicles: List<Vehicle>): Unit {
        // todo
    }

    private fun collectArrivedV(vehicles: List<Vehicle>): List<Vehicle> {
        // todo
        return TODO()
    }
}
