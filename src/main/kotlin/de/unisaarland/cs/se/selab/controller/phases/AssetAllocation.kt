package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle

class AssetAllocation {

    /**
     * Allocate and reallocate assets for each emergency.
     * */
    fun execute(m: Model): Unit {
        // todo
    }

    /**
     * Sort Emergencies firstly by severity and then by id.
     * */
    private fun sortEmergencies(emergencies: MutableList<Emergency>): Unit {
        // todo
    }

    /**
     * Sort Vehicles by id.
     * */
    private fun sortVehicles(vehicles: List<Vehicle>): Unit {
        // todo
    }

}