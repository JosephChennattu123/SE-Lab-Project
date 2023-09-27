package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle

/**
 * Asset allocation phase.
 * In this phase assets get allocated and reallocated to the emergencies
 * that are assigned to the bases.
 */
class AssetAllocation {

    /**
     * Allocate and reallocate assets for each emergency.
     * @param model the model
     * */
    fun execute(model: Model) {
        // TODO put real code here, code only so that detekt makes problems
        sortEmergencies(model.getCurrentEmergencies().toMutableList())
        sortVehicles(model.getSortedVehicleList()) // method sortVehicles looks unnecessary
        TODO()
    }

    /**
     * Sort Emergencies firstly by severity and then by id.
     * @param emergencies the emergencies to sort
     * */
    private fun sortEmergencies(emergencies: MutableList<Emergency>) {
        emergencies
        TODO()
    }

    /**
     * Sort Vehicles by id.
     * @param vehicles the vehicles to sort
     * */
    private fun sortVehicles(vehicles: List<Vehicle>) {
        vehicles
        TODO()
    }
}
