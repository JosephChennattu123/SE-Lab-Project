package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.ControlCenter
import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.util.Logger

/**
 * Manages the parsing and validation of files and builds
 * the objects after validation was successful
 * */
class ValidatorManager {

    private var vehicles: List<Vehicle>? = null
    private var bases: List<Base>? = null

    // private var vertices: List<Vertex>? = null
    // private var edges: List<Edge>? = null
    private var graph: Graph? = null
    private var events: List<Event>? = null
    private var emergencies: List<Emergency>? = null
    private var controlCenter: ControlCenter? = null
    private var dotParser: DotParser? = null
    private var jsonParser: JsonParser? = null

    /**
     * The entrypoint for validation.
     * Calls the parsers and concrete validators and builds the ControlCenter
     *
     * @param dotParser the parser for the dot-file
     * @param jsonParser the parser for the json files
     * @param maxTick the maximum amount of ticks the simulation will run (originates from the command-line arguments)
     * @return the ControlCenter-object
     * */
    fun validate(dotParser: DotParser, jsonParser: JsonParser, maxTick: Int?): ControlCenter? {
        // TODO
        this.dotParser = dotParser
        this.jsonParser = jsonParser

        // dotParser.parse()
        if (!validateGraph()) {
            Logger.logFileInvalid(dotParser.graphFilePath)
            return null
        }
        Logger.logParsingValidationSuccess(dotParser.graphFilePath)

        // jsonParser.parseAssets()
        if (!validateBases() || !validateVehicles()) {
            Logger.logFileInvalid(jsonParser.assetsFilePath)
            return null
        }
        Logger.logParsingValidationSuccess(jsonParser.assetsFilePath)

        // jsonParser.parseEmergenciesEvents()
        if (!validateEmergencies() || !validateEvent()) {
            Logger.logFileInvalid(jsonParser.emergenciesEventsFilePath)
            return null
        }
        Logger.logParsingValidationSuccess(jsonParser.emergenciesEventsFilePath)

        val model = buildModel(maxTick)

        controlCenter = ControlCenter(model)
        return controlCenter
    }

    private fun buildModel(maxTick: Int?): Model {
        val basesMap = bases!!.associateBy { it.baseId }
        val vehiclesMap = vehicles!!.associateBy { it.vehicleID }
        val emergenciesMap = emergencies!!.associateBy { it.id }
        val eventsMap = events!!.associateBy { it.id }

        val vehiclesToBasesMap = vehicles!!.associate { Pair(it.vehicleID, it.baseID) }
        val tickToEmergencyId: MutableMap<Int, List<Int>> = mutableMapOf()

        emergencies!!.map { it.scheduledTick }.toSet().associateWithTo(tickToEmergencyId) {
            val elements: MutableList<Int> = mutableListOf()
            for (em in emergencies!!) {
                if (em.scheduledTick == it) {
                    elements.add(em.id)
                }
            }
            elements
        }

        val tickToEventId: MutableMap<Int, List<Int>> = mutableMapOf()
        events!!.map { it.start }.toSet().associateWithTo(tickToEmergencyId) {
            val elements: MutableList<Int> = mutableListOf()
            for (ev in events!!) {
                if (ev.start == it) {
                    elements.add(ev.id)
                }
            }
            elements
        }
        return Model(
            graph!!, maxTick, basesMap, vehiclesMap, vehiclesToBasesMap, emergenciesMap, tickToEmergencyId,
            eventsMap, tickToEventId
        )
    }

    /**
     * Validate the vehicles
     *
     * @return true if validation was successful
     * */
    private fun validateVehicles(): Boolean {
        val vehicleValidator = VehicleValidator()
        this.vehicles = vehicleValidator.validate()
        return vehicles != null
    }

    /**
     * Validates the graph
     *
     * @return true if validation was successful
     * */
    private fun validateGraph(): Boolean {
        val graphValidator = GraphValidator()
        this.graph = graphValidator.validate(dotParser!!)
        return graph != null
    }

    /**
     * Validates the bases
     *
     * @return true if validation was successful
     * */
    private fun validateBases(): Boolean {
        val baseValidator = BaseValidator()
        this.bases = baseValidator.validate()
        TODO()
    }

    /**
     * Validate the emergencies
     *
     * @return true if validation was successful
     * */
    private fun validateEmergencies(): Boolean {
        val emergencyValidator = EmergencyValidator()
        this.emergencies = emergencyValidator.validate()
        TODO()
    }

    /**
     * Validate the events
     *
     * @return true if validation was successful
     * */
    private fun validateEvent(): Boolean {
        val eventValidator = EventValidator()
        this.events = eventValidator.validate()
        TODO()
    }
}
