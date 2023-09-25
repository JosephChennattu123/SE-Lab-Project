package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.Event

/**
 * Validates the events
 */
class EventValidator : BasicValidator() {

    /**
     * Validates the information for events and creates events.
     *
     * @return the list of events created
     */
    fun validate(): List<Event> {
        // TODO
        return listOf()
    }

    /**
     * Checks that the factors are at least 1
     *
     * @return true if the factors are valid
     */
    private fun validateFactor(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that the corresponding edges and vertices exist
     *
     * @return true if the edges and vertices exist (road events)
     */
    private fun validateEdgeAndVerticesExists(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that the corresponding vehicles exist
     * @return true if the vehicles exist (vehicle events)
     */
    private fun validateVehicleExist(): Boolean {
        // TODO
        return false
    }
}