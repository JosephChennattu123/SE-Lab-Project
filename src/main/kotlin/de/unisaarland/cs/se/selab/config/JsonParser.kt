package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.EventType
import de.unisaarland.cs.se.selab.getSchema
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.EmergencyType
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.vehicle.VehicleType
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Parses the json-files
 * @param assetsFilePath path to the file for assets (bases and vehicles)
 * @param emergenciesEventsFilePath path to the file for emergencies and events
 * */
class JsonParser(val assetsFilePath: String, val emergenciesEventsFilePath: String) {

    private val assetJson = JSONObject(File(assetsFilePath).readText())
    private val scenarioJson = JSONObject(File(emergenciesEventsFilePath).readText())

    private val assetSchema = getSchema(this::class.java, "assets.schema")
    private val scenarioSchema = getSchema(this::class.java, "simulation.schema")

    private val bases: MutableList<BaseInfo> = mutableListOf()
    private val vehicles: MutableList<VehicleInfo> = mutableListOf()
    private val events: MutableList<EventInfo> = mutableListOf()
    private val emergencies: MutableList<EmergencyInfo> = mutableListOf()

    /**
     * Runs the assets schema validation
     */
    fun validateAssetsSchema() {
        assetSchema?.validate(assetJson)
    }

    /**
     * Runs the scenario schema validation
     */
    fun validateScenarioSchema() {
        scenarioSchema?.validate(scenarioJson)
    }

    /**
     * parses the bases
     */
    fun parseBases(): List<BaseInfo> {
        val bases: JSONArray = assetJson.getJSONArray(cfgBases)
        for (i in 0 until bases.length()) {
            val base: JSONObject = bases.getJSONObject(i)
            parseBase(base)
        }
        return this.bases
    }

    private fun parseBase(base: JSONObject) {
        val id = base.getInt(ID)
        val type = base.getString(cfgBaseType)
        val staff = base.getInt(cfgStaff)
        val doctors = base.opt(cfgDoctors) as Int?
        val dogs = base.opt(cfgDogs) as Int?
        val location = base.getInt(cfgLocation)
        bases.add(BaseInfo(id, BaseType.fromString(type) as BaseType, location, staff, doctors, dogs))
    }

    /**
     * parses the vehicles
     */
    fun parseVehicles(): List<VehicleInfo> {
        val vehicles: JSONArray = assetJson.getJSONArray(cfgVehicles)
        for (i in 0 until vehicles.length()) {
            val vehicle: JSONObject = vehicles.getJSONObject(i)
            parseVehicle(vehicle)
        }
        return this.vehicles
    }

    private fun parseVehicle(vehicle: JSONObject) {
        val id = vehicle.getInt(ID)
        val type = vehicle.getString(cfgVehicleType)
        val baseID = vehicle.getInt(cfgBaseId)
        val staffCapacity = vehicle.getInt(cfgStaffCapacity)
        val height = vehicle.getInt(cfgHeight)
        val criminalCapacity = vehicle.opt(cfgCriminalCapacity) as Int?
        val ladderLength = vehicle.opt(cfgLadderLength) as Int?
        val waterCapacity = vehicle.opt(cfgWaterCapacity) as Int?
        vehicles.add(
            VehicleInfo(
                id,
                baseID,
                VehicleType.fromString(type) as VehicleType,
                staffCapacity,
                height,
                criminalCapacity,
                ladderLength,
                waterCapacity
            )
        )
    }

    /**
     * parses the emergencies
     */
    fun parseEmergencies(): List<EmergencyInfo> {
        val emergencies: JSONArray = scenarioJson.getJSONArray(LABEL_EMERGENCY)
        for (i in 0 until emergencies.length()) {
            val emergency: JSONObject = emergencies.getJSONObject(i)
            parseEmergency(emergency)
        }
        return this.emergencies
    }

    private fun parseEmergency(emergency: JSONObject) {
        val id = emergency.getInt(ID)
        val tick = emergency.getInt(TICK)
        val village = emergency.getString(VILLAGE)
        val roadName = emergency.getString(JSON_LABEL_ROAD_NAME)
        val emergencyType = EmergencyType.fromString(emergency.getString(EMERGENCY_TYPE))
        val severity = emergency.getInt(SEVERITY)
        val handleTime = emergency.getInt(HANDLE_TIME)
        val maxDuration = emergency.getInt(MAX_DURATION)
        emergencies.add(
            EmergencyInfo(
                id,
                tick,
                village,
                roadName,
                emergencyType as EmergencyType,
                severity,
                handleTime,
                maxDuration
            )
        )
    }

    /**
     * parses the events
     */
    fun parseEvents(): List<EventInfo> {
        val events: JSONArray = scenarioJson.getJSONArray(cfgEvents)
        for (i in 0 until events.length()) {
            val event: JSONObject = events.getJSONObject(i)
            parseEvent(event)
        }
        return this.events
    }

    private fun parseEvent(event: JSONObject) {
        val id = event.getInt(ID)
        val tick = event.getInt(TICK)
        val eventType = EventType.fromString(event.getString(cfgEventType))
        val duration = event.getInt(cfgDuration)

        var roadTypes: List<PrimaryType>? = null
        val roadTypesArray = event.opt(cfgRoadType)
        if (roadTypesArray != null) {
            val roadTypesCollect: MutableList<PrimaryType> = mutableListOf()
            for (obj in roadTypesArray as JSONArray) {
                roadTypesCollect.add(PrimaryType.fromString(obj as String) as PrimaryType)
            }
            if (roadTypesCollect.isNotEmpty()) {
                roadTypes = roadTypesCollect.toSet().toList()
            }
        }

        val factor = event.opt(cfgFactor) as Int?
        val oneWayStreet = event.opt(cfgOneWayStreet) as Boolean?
        val source = event.opt(cfgSource) as Int?
        val target = event.opt(cfgTarget) as Int?
        val vehicleID = event.opt(cfgVehicleId) as Int?
        val eventInfo = EventInfo(
            id,
            tick,
            eventType as EventType,
            duration,
            roadTypes,
            factor,
            oneWayStreet,
            source,
            target,
        )
        eventInfo.vehicleId = vehicleID
        eventInfo.infoMap[VEHICLE_ID] = vehicleID
        events.add(eventInfo)
    }

    // private val cfgId: String = "id"

    // base related keys.
    private val cfgBases: String = "bases"
    private val cfgBaseType: String = "baseType"
    private val cfgStaff: String = "staff"
    private val cfgDoctors: String = "doctors"
    private val cfgDogs: String = "dogs"
    private val cfgLocation: String = "location"

    // vehicle related keys.
    private val cfgVehicles: String = "vehicles"
    private val cfgVehicleType: String = "vehicleType"
    private val cfgBaseId: String = "baseID"
    private val cfgStaffCapacity: String = "staffCapacity"
    private val cfgHeight: String = "vehicleHeight"
    private val cfgCriminalCapacity: String = "criminalCapacity"
    private val cfgLadderLength: String = "ladderLength"
    private val cfgWaterCapacity: String = "waterCapacity"

    // event related keys.
    private val cfgEvents: String = "events"
    private val cfgEventType: String = "type"
    private val cfgDuration: String = "duration"
    private val cfgRoadType: String = "roadTypes"
    private val cfgFactor: String = "factor"
    private val cfgOneWayStreet: String = "oneWayStreet"
    private val cfgSource: String = "source"
    private val cfgTarget: String = "target"
    private val cfgVehicleId: String = "vehicleID"
}
