package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.Event

/**
 * Validates the events
 */
class EventValidator(jsonParser: JsonParser) : BasicValidator(jsonParser) {

    override var requiredProperties: List<String> = listOf("id", "type", "tick", "duration")

    /**
     * Validates the information for events and creates events.
     *
     * @return the list of events created
     */
    fun validate(): List<Event>? {
        validateFactor()
        validateEdgeAndVerticesExists()
        validateVehicleExist()
        TODO()
    }

    /**
     * Checks that the factors are at least 1
     *
     * @return true if the factors are valid
     */
    private fun validateFactor(): Boolean {
        TODO()
    }

    /**
     * Check that the corresponding edges and vertices exist
     *
     * @return true if the edges and vertices exist (road events)
     */
    private fun validateEdgeAndVerticesExists(): Boolean {
        TODO()
    }

    /**
     * Checks that the corresponding vehicles exist
     * @return true if the vehicles exist (vehicle events)
     */
    private fun validateVehicleExist(): Boolean {
        TODO()
    }
}
