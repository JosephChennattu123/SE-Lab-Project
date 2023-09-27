package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Emergency

/**
 * Validates emergencies
 */
class EmergencyValidator : BasicValidator() {
    override var requiredProperties: List<String> =
        listOf("id", "tick", "village", "roadName", "emergencyType", "severity", "handleTime", "maxDuration")

    /**
     * Validates the information for emergencies and creates emergencies.
     *
     * @return the list of emergencies created
     */
    fun validate(): List<Emergency> {
        validateVillagesExist()
        validateRoadExistsInVillageOrCounty()
        validateSeverityBounds()
        validateMaxDurationNotExceedHandleTime()
        TODO()
    }

    /**
     * Checks that the corresponding village (or county) exists
     *
     * @return true if villages are valid
     */
    private fun validateVillagesExist(): Boolean {
        TODO()
    }

    /**
     * Check that the corresponding road exists in the village or county
     *
     * @return true if the roads exist
     */
    private fun validateRoadExistsInVillageOrCounty(): Boolean {
        // TODO
        TODO()
    }

    /**
     * Check that the severity is in bounds
     *
     * @return true if severities are valid
     */
    private fun validateSeverityBounds(): Boolean {
        TODO()
    }

    /**
     * Check that the maxDuration does not exceed the handle time
     * Also checks that the duration and handle time are valid
     *
     * @return true if maxDurations and handleTimes are valid
     */
    private fun validateMaxDurationNotExceedHandleTime(): Boolean {
        TODO()
    }
}
