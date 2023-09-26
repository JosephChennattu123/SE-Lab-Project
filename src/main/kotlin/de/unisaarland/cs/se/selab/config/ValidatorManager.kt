package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.ControlCenter

/**
 * Manages the parsing and validation of files and builds
 * the objects after validation was successful
 * */
class ValidatorManager {
    /**
     * The entrypoint for validation.
     * Calls the parsers and concrete validators and builds the Emcc
     *
     * @param dotParser the parser for the dot-file
     * @param jsonParser the parser for the json files
     * @return Emcc the EMCC-object
     * */
    fun validate(dotParser: DotParser, jsonParser: JsonParser): ControlCenter? {
        TODO()
    }

    /**
     * Validate the vehicles
     *
     * @return true if validation was successful
     * */
    fun validateVehicles(): Boolean {
        TODO()
    }

    /**
     * Validates the graph
     *
     * @return true if validation was successful
     * */
    fun validateGraph(): Boolean {
        TODO()
    }

    /**
     * Validates the bases
     *
     * @return true if validation was successful
     * */
    fun validateBases(): Boolean {
        TODO()
    }

    /**
     * Validate the emergencies
     *
     * @return true if validation was successful
     * */
    fun validateEmergencies(): Boolean {
        TODO()
    }

    /**
     * Validate the events
     *
     * @return true if validation was successful
     * */
    fun validateEvent(): Boolean {
        TODO()
    }
}