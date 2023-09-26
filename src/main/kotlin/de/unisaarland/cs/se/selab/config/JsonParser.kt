package de.unisaarland.cs.se.selab.config

/**
 * Parses the json-files
 * @param assetsFilePath path to the file for assets (bases and vehicles)
 * @param emergenciesEventsFilePath path to the file for emergencies and events
 * */
class JsonParser(assetsFilePath: String, emergenciesEventsFilePath: String) {

    var bases: List<BaseInfo> = listOf()
    var vehicles: List<VehicleInfo> = listOf()
    var events: List<EventInfo> = listOf()
    var emergencies: List<EmergencyInfo> = listOf()
    var failed: Boolean = false
    /**
     * parses the assets (bases and vehicles)
     *
     * @return true if parsing was successful
     */
    fun parseAssets(): Boolean {
        TODO()
    }

    /**
     * parses the emergencies and events
     *
     * @return true if parsing was successful
     */
    fun parseEmergenciesEvents(): Boolean {
        TODO()
    }

    /**
     * parses the bases
     */
    fun parseBases() {

    }

    /**
     * parses the vehicles
     */
    fun parseVehicles() {

    }

    /**
     * parses the emergencies
     */
    fun parseEmergencies() {

    }

    /**
     * parses the events
     */
    fun parseEvents() {

    }
}
