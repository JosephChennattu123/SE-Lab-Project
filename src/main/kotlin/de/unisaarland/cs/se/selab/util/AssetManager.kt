package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyRequirement
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle

/**
 * Returns requirements for emergencies and handles the allocation of assets to emergencies */
object AssetManager {
    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements */
    fun getFireRequirements(severity: Int): List<EmergencyRequirement> {
        severity
        TODO()
    }

    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements */
    fun getMedicalRequirements(severity: Int): List<EmergencyRequirement> {
        severity
        TODO()
    }

    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements */
    fun getAccidentRequirements(severity: Int): List<EmergencyRequirement> {
        severity
        TODO()
    }

    /**
     * @param severity Severity of the emergency
     * @return List of EmergencyRequirements */
    fun getCrimeRequirements(severity: Int): List<EmergencyRequirement> {
        severity
        TODO()
    }

    /**
     * @param model The model
     * @param emergency The emergency to allocate to
     * @param vehicles The list of vehicles to assign to the emergency */
    fun allocateAssetsToEmergency(model: Model, emergency: Emergency, vehicles: List<Vehicle>) {
        model
        emergency
        val vehiclesToCheck = vehicles.toMutableList()
        filterAssetsByRequirement(vehiclesToCheck.toMutableList(), emergency.currentRequiredAssets)
        filterAssetsByOptimalSolution(vehiclesToCheck, emergency.currentRequiredAssets)
        TODO()
    }

    /**
     * modifies the parameter lists to contain the optimal selection of concrete vehicles to be assigned to an emergency
     * @param vehicles List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByOptimalSolution(
        vehicles: MutableList<Vehicle>,
        requirements: MutableList<EmergencyRequirement>
    ) {
        vehicles
        requirements
        TODO()
    }

    /**
     * modifies the list of Vehicles to contain vehicles that can be assigned to an emergency
     * @param vehiclesToCheck List of vehicles to check
     * @param requirements List of requirements to check the list of vehicles against */
    private fun filterAssetsByRequirement(
        vehiclesToCheck: MutableList<Vehicle>,
        requirements: List<EmergencyRequirement>
    ) {
        vehiclesToCheck
        requirements
        TODO()
    }
}
