package de.unisaarland.cs.se.selab.config

/**
 * Parses the json-files
 * @param assetsFilePath path to the file for assets (bases and vehicles)
 * @param emergenciesEventsFilePath path to the file for emergencies and events
 * */
class JsonParser(assetsFilePath: String, emergenciesEventsFilePath: String) {
    /**
     * parses the assets (bases and vehicles)
     *
     * @param assetsFilePath the file name that contains the information to be parsed
     * @return true if parsing was successful
     */
    fun parseAssets(assetsFilePath: String): Boolean {
        // TODO
        return false
    }

    /**
     * parses the emergencies and events
     *
     * @param emergenciesEventsFilePath the file name that contains the information to be parsed
     * @return true if parsing was successful
     */
    fun parseEmergenciesEvents(emergenciesEventsFilePath: String): Boolean {
        // TODO
        return false
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