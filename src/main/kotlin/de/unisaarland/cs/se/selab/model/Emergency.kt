package de.unisaarland.cs.se.selab.model

/**
 * Emergencies have to be assigned to bases and handled by vehicles, they can fail or resolve
 * @param id ID of the emergency
 * @param scheduledTick round in which the emergency begins
 * @param type Type of the emergency
 * @param severity Severity of the emergency, dictates requirements
 * @param handleTime amount of rounds needed to resolve the emergency
 * @param maxDuration amount of rounds until the emergency fails
 * @param location Precise location of the emergency
 * @property canRequest true if at least one allocation or reallocation was not fail*/
class Emergency(
    val id: Int,
    val scheduledTick: Int,
    val type: EmergencyType,
    val severity: Int = 3,
    var handleTime: Int,
    val maxDuration: Int,
    val location: Location
) {
    var timeElapsed: Int = 0
    var status: EmergencyStatus = EmergencyStatus.ONGOING

    var canRequest: Boolean = false
    var baseRequirements: List<EmergencyRequirement> = emptyList()
    val currentRequirements: MutableList<EmergencyRequirement> = mutableListOf()
    val assignedVehicleIDs: MutableList<Int> = mutableListOf()
    val availableVehicleIDs: MutableList<Int> = mutableListOf()
    var mainBaseID: Int? = null

    /**
     * assigns a Vehicle to this emergency
     * @param v vehicle to be assigned */
    fun addAsset(v: Vehicle) {
        assignedVehicleIDs.add(v.vehicleID)
    }

    /**
     * adds a Vehicle to the emergency's list of arrived vehicles
     * @param v vehicle to be added*/
    fun assetArrived(v: Vehicle) {
        availableVehicleIDs.add(v.vehicleID)
    }

    /**
     * @return whether the emergency can start its handling phase */
    fun canStart(): Boolean {
        assignedVehicleIDs.sort()
        availableVehicleIDs.sort()
        return assignedVehicleIDs == availableVehicleIDs && isFulfilled()
    }

    /**
     * Updates the number of available assets for each vehicle after emergency starts being handled */
    fun handle(model: Model) {
        model
        availableVehicleIDs.sort()
//        val policeVehicles = model.getVehiclesByIds(availableVehicleIDs) // List of all police vehicles
//            .filter { VehicleType.getBaseType(it.vehicleType) == BaseType.POLICE_STATION }
//        val hospitalVehicles = model.getVehiclesByIds(availableVehicleIDs)
//            // List of all hospital vehicles
//            .filter { VehicleType.getBaseType(it.vehicleType) == BaseType.HOSPITAL }
//        val fireVehicles = model.getVehiclesByIds(availableVehicleIDs) // List of all fire vehicles
//            .filter { VehicleType.getBaseType(it.vehicleType) == BaseType.FIRE_STATION }
//        var criminalReq: Int = currentRequirements.first { it.assetType == AssetType.CRIMINAL }.amountOfAsset ?: 0
//        // finds the assetAmount of the requirement that corresponds to police vehicles
//        var patientReq: Int = currentRequirements.first { it.assetType == AssetType.PATIENT }.amountOfAsset ?: 0
//        // finds the assetAmount of the requirement that corresponds to ambulances
//        var waterReq: Int = currentRequirements.first { it.assetType == AssetType.WATER }.amountOfAsset ?: 0
//        // finds the assetAmount of the requirement that corresponds to firetrucks
//        policeVehicles.forEach {
//            criminalReq = it.handleEmergency(criminalReq)
//        }
//        hospitalVehicles.forEach {
//            patientReq = it.handleEmergency(patientReq)
//        }
//        fireVehicles.forEach {
//            waterReq = it.handleEmergency(waterReq)
//        }
    }

    /**
     * @return if a vehicle can make it to an emergency in a given amount of ticks
     * @param ticks The amount of ticks to check against the remaining time to handle */
    fun canReachInTime(ticks: Int): Boolean {
        return maxDuration - (timeElapsed + handleTime) > ticks
    }

    /**
     * @return if the emergency has been assigned all of its necessary assets */
    fun isFulfilled(): Boolean {
        return currentRequirements.isEmpty()
    }

    /**
     * Set status of this emergency
     *
     * @param s: New status of the emergency*/
    fun changeStatus(s: EmergencyStatus) {
        status = s
    }

    /**
     * Increments timeElapsed and decrements handleTime */
    fun decrementTimer() {
        timeElapsed++
        if (status == EmergencyStatus.BEING_HANDLED) handleTime--
    }

    /**
     * checks if emergency has failed or been resolved or should remain ongoing or handling
     * @return if the emergency has reached its end state */
    fun resolveOrFailEmergency(): Boolean {
        if (timeElapsed == maxDuration) {
            changeStatus(EmergencyStatus.FAILED)
            return true
        }
        if (handleTime <= 0) {
            changeStatus(EmergencyStatus.RESOLVED)
            return true
        }
        return false
    }
}

/**
 * Type of the emergency */
enum class EmergencyType {
    FIRE, ACCIDENT, CRIME, MEDICAL;

    companion object {
        /**
         * @param value possibly a value of this enum
         * @return a EmergencyType if the string matched a value else null
         */
        fun fromString(value: String): EmergencyType? {
            return when (value) {
                "FIRE" -> FIRE
                "ACCIDENT" -> ACCIDENT
                "CRIME" -> CRIME
                "MEDICAL" -> MEDICAL
                else -> null
            }
        }
    }
}

/**
 * Status of the emergency */
enum class EmergencyStatus {
    ONGOING, WAITING_FOR_ASSETS, BEING_HANDLED, RESOLVED, FAILED
}
