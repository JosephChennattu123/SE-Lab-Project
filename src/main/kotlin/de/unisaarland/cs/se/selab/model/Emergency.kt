package de.unisaarland.cs.se.selab.model

/**
 * */
class Emergency(
    id: Int, scheduledTick: Int, type: EmergencyType, severity: Int, handeTime: Int, maxDuration: Int,
    location: Location
) {
    /**
     * assigns a Vehicle to this emergency
     * @param v vehicle to be assigned */
    fun addAsset(v: Vehicle) {
        // TODO
    }

    /**
     * adds a Vehicle to the emergency's list of arrived vehicles
     * @param v vehicle to be added*/
    fun assetArrived(v: Vehicle) {
        // TODO
    }

    /**
     * @return whether the emergency can start its handling phase */
    fun canStart(): Boolean {
        TODO()
    }

    /**
     * begin handling the emergency if all assets have arrived */
    fun handle() {
        // TODO
    }

    /**
     * @return if a vehicle can make it to an emergency in a given amount of ticks
     * @param ticks The amount of ticks to check against the remaining time to handle */
    fun canReachInTime(ticks: Int): Boolean {
        TODO()
    }


    /**
     * @return if the emergency has been assigned all of its necessary assets */
    fun isFulfilled(): Boolean {
        TODO()
    }

    /**
     * Set status of this emergency
     *
     * @param s: New status of the emergency*/
    fun changeStatus(s: EmergencyStatus) {
        TODO()
    }

    /**
     * Decrements timeElapsed and handleTime */
    fun decrementTimer() {
        // TODO
    }

    /**
     * checks if emergency has failed or been resolved or should remain ongoing or handling
     * @return if the emergency has reached its end state */
    fun resolveOrFailEmergency(): Boolean {
        TODO()
    }
}

/**
 * Type of the emergency */
enum class EmergencyType {
    FIRE, ACCIDENT, CRIME, MEDICAL
}

/**
 * Status of the emergency */
enum class EmergencyStatus {
    ONGOING, WAITING_FOR_ASSETS, BEING_HANDLED, RESOLVED, FAILED
}
