package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.controller.events.EventType
import de.unisaarland.cs.se.selab.getSchema
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.EmergencyType
import de.unisaarland.cs.se.selab.model.VehicleType
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Parses the json-files
 * @param assetsFilePath path to the file for assets (bases and vehicles)
 * @param emergenciesEventsFilePath path to the file for emergencies and events
 * */
class JsonParser(val assetsFilePath: String, val emergenciesEventsFilePath: String) {

    private val assetJson = JSONObject(File(assetsFilePath))
    private val scenarioJson = JSONObject(File(emergenciesEventsFilePath))

    private val assetSchema = getSchema(this::class.java, "assets.schema")
    private val scenarioSchema = getSchema(this::class.java, "simulation.schema")

    var bases: List<BaseInfo> = emptyList()
    var vehicles: List<VehicleInfo> = emptyList()
    var events: List<EventInfo> = emptyList()
    var emergencies: List<EmergencyInfo> = emptyList()

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
        assetSchema?.validate(base)
        val id = base.getInt(cfgId)
        val type = base.getString(cfgBaseType)
        val staff = base.getInt(cfgStaff)
        val doctors = base.getInt(cfgDoctors)
        val dogs = base.getInt(cfgDogs)
        val location = base.getInt(cfgLocation)
        bases + BaseInfo(id, BaseType.fromString(type) as BaseType, staff, doctors, dogs, location)
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
        assetSchema?.validate(vehicle)
        val id = vehicle.getInt(cfgId)
        val type = vehicle.getString(cfgVehicleType)
        val baseID = vehicle.getInt(cfgBaseId)
        val staffCapacity = vehicle.getInt(cfgStaffCapacity)
        val height = vehicle.getInt(cfgHeight)
        val criminalCapacity = vehicle.getInt(cfgCriminalCapacity)
        val ladderLength = vehicle.getInt(cfgLadderLength)
        val waterCapacity = vehicle.getInt(cfgWaterCapacity)
        vehicles + VehicleInfo(
            id,
            baseID,
            VehicleType.fromString(type) as VehicleType,
            staffCapacity,
            height,
            criminalCapacity,
            ladderLength,
            waterCapacity
        )
    }

    /**
     * parses the emergencies
     */
    fun parseEmergencies(): List<EmergencyInfo> {
        val emergencies: JSONArray = scenarioJson.getJSONArray(cfgEmergencies)
        for (i in 0 until emergencies.length()) {
            val emergency: JSONObject = emergencies.getJSONObject(i)
            parseEmergency(emergency)
        }
        return this.emergencies
    }

    private fun parseEmergency(emergency: JSONObject) {
        scenarioSchema?.validate(emergency)
        val id = emergency.getInt(cfgId)
        val tick = emergency.getInt(cfgTick)
        val village = emergency.getString(cfgVillage)
        val roadName = emergency.getString(cfgRoadName)
        val emergencyType = EmergencyType.fromString(emergency.getString(cfgEmergencyType))
        val severity = emergency.getInt(cfgSeverity)
        val handleTime = emergency.getInt(cfgHandleTime)
        val maxDuration = emergency.getInt(cfgMaxDuration)
        emergencies + EmergencyInfo(
            id,
            tick,
            village,
            roadName,
            emergencyType as EmergencyType,
            severity,
            handleTime,
            maxDuration
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
        scenarioSchema?.validate(event)
        val id = event.getInt(cfgId)
        val tick = event.getInt(cfgTick)
        val eventType = EventType.fromString(event.getString(cfgEventType))
        val duration = event.getInt(cfgDuration)
        val roadType = PrimaryType.fromString(event.getString(cfgRoadType))
        val factor = event.getInt(cfgFactor)
        val oneWayStreet = event.getBoolean(cfgOneWayStreet)
        val source = event.getInt(cfgSource)
        val target = event.getInt(cfgTarget)
        val vehicleID = event.getInt(cfgVehicleId)
        val eventInfo = EventInfo(
            id,
            tick,
            eventType as EventType,
            duration,
            roadType as PrimaryType,
            factor,
            oneWayStreet,
            source,
            target,
        )
        eventInfo.vehicleId = vehicleID
    }

    private val cfgId: String = "id"

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
    private val cfgHeight: String = "height"
    private val cfgCriminalCapacity: String = "criminalCapacity"
    private val cfgLadderLength: String = "ladderLength"
    private val cfgWaterCapacity: String = "waterCapacity"

    // emergency related keys.
    private val cfgEmergencies: String = "emergencies"
    private val cfgTick: String = "tick"
    private val cfgVillage: String = "village"
    private val cfgRoadName: String = "roadName"
    private val cfgEmergencyType: String = "emergencyType"
    private val cfgSeverity: String = "severity"
    private val cfgHandleTime: String = "handleTime"
    private val cfgMaxDuration: String = "maxDuration"

    // event related keys.
    private val cfgEvents: String = "events"
    private val cfgEventType: String = "type"
    private val cfgDuration: String = "duration"
    private val cfgRoadType: String = "roadType"
    private val cfgFactor: String = "factor"
    private val cfgOneWayStreet: String = "oneWayStreet"
    private val cfgSource: String = "source"
    private val cfgTarget: String = "target"
    private val cfgVehicleId: String = "vehicleID"
}
