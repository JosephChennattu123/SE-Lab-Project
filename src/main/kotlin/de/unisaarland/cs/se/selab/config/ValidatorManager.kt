package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.ControlCenter
import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.map.Edge
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.Vertex

/**
 * Manages the parsing and validation of files and builds
 * the objects after validation was successful
 * */
class ValidatorManager {

    private var vehicles: List<Vehicle> = listOf()
    private var bases: List<Base> = listOf()
    private var vertices: List<Vertex> = listOf()
    private var edges: List<Edge> = listOf()
    private var graph: Graph? = null
    private var events: List<Event> = listOf()
    private var emergencies: List<Emergency> = listOf()
    private var controlCenter: ControlCenter? = null

    /**
     * The entrypoint for validation.
     * Calls the parsers and concrete validators and builds the Emcc
     *
     * @param dotParser the parser for the dot-file
     * @param jsonParser the parser for the json files
     * @return the ControlCenter-object
     * */
    fun validate(dotParser: DotParser, jsonParser: JsonParser): ControlCenter? {
        dotParser.parse()
        val graphValidator = GraphValidator()
        this.graph = graphValidator.validate(dotParser)

        jsonParser.parseAssets()
        val baseValidator = BaseValidator()
        this.bases = baseValidator.validate()
        val vehicleValidator = VehicleValidator()
        this.vehicles = vehicleValidator.validate()

        jsonParser.parseEmergenciesEvents()
        val emergencyValidator = EmergencyValidator()
        this.emergencies = emergencyValidator.validate()

        val eventValidator = EventValidator()
        this.events = eventValidator.validate()

        controlCenter = ControlCenter()
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
