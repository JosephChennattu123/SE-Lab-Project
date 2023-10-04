package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.ControlCenter
import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleType
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.util.Logger
import org.everit.json.schema.ValidationException

/**
 * Manages the parsing and validation of files and builds
 * the objects after validation was successful
 * */
class ValidatorManager {

    private var vehicles: List<Vehicle>? = null
    private var bases: List<Base>? = null

    private var graph: Graph? = null
    private var events: List<Event>? = null
    private var emergencies: List<Emergency>? = null
    private var controlCenter: ControlCenter? = null
    private var dotParser: DotParser? = null
    private var jsonParser: JsonParser? = null
    private var basesMap: Map<Int, Base>? = null
    private var vehiclesMap: Map<Int, Vehicle>? = null

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

        var fail = false

        // dotParser.parse()
        if (!validateGraph()) {
            Logger.logParsingValidationResult(dotParser.graphFilePath, false)
            return null
        }
        Logger.logParsingValidationResult(dotParser.graphFilePath, true)

        try {
            jsonParser.validateAssetsSchema()
        } catch (e: ValidationException) {
            // Logger.logParsingValidationResult(jsonParser.assetsFilePath, false)
            Logger.outputLogger.error {
                "${"Error: invalid assets-file detected by schema-validation"} ${e.stackTrace}"
            }
            fail = true
        }
        // jsonParser.parseAssets()
        if (fail || !validateBases(graph as Graph) || !validateVehicles(bases as List<Base>)) {
            Logger.logParsingValidationResult(jsonParser.assetsFilePath, false)
            return null
        }

        Logger.logParsingValidationResult(jsonParser.assetsFilePath, true)
        try {
            jsonParser.validateScenarioSchema()
        } catch (e: ValidationException) {
            // Logger.logParsingValidationResult(jsonParser.emergenciesEventsFilePath, false)
            Logger.outputLogger.error {
                "${"Error: invalid scenario-file detected by schema-validation"} ${e.stackTrace}"
            }
            fail = true
        }
        // jsonParser.parseEmergenciesEvents()
        if (fail || !validateEmergencies(graph as Graph) || !validateEvent(graph as Graph, vehicles as List<Vehicle>)) {
            Logger.logParsingValidationResult(jsonParser.emergenciesEventsFilePath, false)
            return null
        }

        Logger.logParsingValidationResult(jsonParser.emergenciesEventsFilePath, true)

        val model = buildModel(maxTick, basesMap as Map<Int, Base>, vehiclesMap as Map<Int, Vehicle>)

        controlCenter = ControlCenter(model)
        return controlCenter
    }

    private fun buildModel(maxTick: Int?, basesMap: Map<Int, Base>, vehiclesMap: Map<Int, Vehicle>): Model {
        val emergenciesList = emergencies as List<Emergency>
        val eventsList = events as List<Event>

        val vehiclesToBasesMap = (vehicles as List<Vehicle>).associate { Pair(it.vehicleID, it.baseID) }

        val emergenciesMap = emergenciesList.associateBy { it.id }
        val eventsMap = eventsList.associateBy { it.id }

        val tickToEmergencyId: MutableMap<Int, List<Int>> = mutableMapOf()
        emergenciesList.map { it.scheduledTick }.toSet().associateWithTo(tickToEmergencyId) {
            val elements: MutableList<Int> = mutableListOf()
            for (em in emergenciesList) {
                if (em.scheduledTick == it) {
                    elements.add(em.id)
                }
            }
            elements
        }

        val tickToEventId: MutableMap<Int, List<Int>> = mutableMapOf()
        eventsList.map { it.start }.toSet().associateWithTo(tickToEventId) {
            val elements: MutableList<Int> = mutableListOf()
            for (ev in eventsList) {
                if (ev.start == it) {
                    elements.add(ev.id)
                }
            }
            elements
        }
        return Model(
            graph as Graph, maxTick, basesMap, vehiclesMap, vehiclesToBasesMap, emergenciesMap, tickToEmergencyId,
            eventsMap, tickToEventId
        )
    }

    private fun addVehiclesToBases(basesList: List<Base>, vehiclesMap: Map<Int, Vehicle>): Boolean {
        basesList.forEach { base ->
            val vehiclesForBase = vehiclesMap.values.filter { it.baseID == base.baseId }
            vehiclesForBase.forEach { vehicle ->
                if (base.baseType == VehicleType.getBaseType(vehicle.vehicleType)) {
                    base.addVehicle(vehicle.vehicleID)
                } else {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Validate the vehicles
     *
     * @return true if validation was successful
     * */
    private fun validateVehicles(bases: List<Base>): Boolean {
        val vehicleValidator = VehicleValidator(jsonParser as JsonParser)
        this.vehicles = vehicleValidator.validate(bases)
        if (vehicles == null) {
            return false
        }
        val basesList = bases
        basesMap = basesList.associateBy { it.baseId }
        vehiclesMap = (vehicles as List<Vehicle>).associateBy { it.vehicleID }

        if (!addVehiclesToBases(basesList, vehiclesMap as Map<Int, Vehicle>)) {
            return false
        }
        val numVehiclesInBases = basesList.map { it.vehicles.size }
        if (numVehiclesInBases.any { it == 0 }) {
            return false
        }
        return true
    }

    /**
     * Validates the graph
     *
     * @return true if validation was successful
     * */
    private fun validateGraph(): Boolean {
        val graphValidator = GraphValidator()
        this.graph = graphValidator.validate(dotParser as DotParser)
        return graph != null
    }

    /**
     * Validates the bases
     *
     * @return true if validation was successful
     * */
    private fun validateBases(graph: Graph): Boolean {
        val baseValidator = BaseValidator(jsonParser as JsonParser)
        this.bases = baseValidator.validate(graph)
        return bases != null
    }

    /**
     * Validate the emergencies
     *
     * @return true if validation was successful
     * */
    private fun validateEmergencies(graph: Graph): Boolean {
        val emergencyValidator = EmergencyValidator(jsonParser as JsonParser)
        this.emergencies = emergencyValidator.validate(graph)
        return emergencies != null
    }

    /**
     * Validate the events
     *
     * @return true if validation was successful
     * */
    private fun validateEvent(graph: Graph, vehicles: List<Vehicle>): Boolean {
        val eventValidator = EventValidator(jsonParser as JsonParser)
        this.events = eventValidator.validate(graph, vehicles)
        return events != null
    }
}
