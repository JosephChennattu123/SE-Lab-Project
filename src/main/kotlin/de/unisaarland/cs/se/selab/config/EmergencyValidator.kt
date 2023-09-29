package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Location
import de.unisaarland.cs.se.selab.model.map.Graph

private const val SEVERITY_MIN = 1
private const val SEVERITY_MAX = 3

/**
 * Validates emergencies
 */
class EmergencyValidator(jsonParser: JsonParser) : BasicValidator(jsonParser) {
    override var requiredProperties: List<String> =
        listOf("id", "tick", "village", "roadName", "emergencyType", "severity", "handleTime", "maxDuration")

    /**
     * Validates the information for emergencies and creates emergencies.
     *
     * @return the list of emergencies created
     */
    fun validate(graph: Graph): List<Emergency>? {
        val jsonParserObj = jsonParser as JsonParser
        val emergencyInfos = jsonParserObj.parseEmergencies()

        if (!emergencyInfos.none { it.tick < 0 }) return null

        emergencyInfos.forEach { emergencyInfo ->
            if (!validateVillagesExist(emergencyInfo, graph) ||
                !validateRoadExistsInVillageOrCounty(emergencyInfo, graph)
            ) {
                return null
            }
            if (!validateSeverityBounds(emergencyInfo)) return null
            if (!validateMaxDurationNotExceedHandleTime(emergencyInfo)) return null
        }
        return emergencyInfos.map {
            val location = Location(it.village, it.roadName)
            Emergency(it.id, it.tick, it.emergencyType, it.severity, it.handleTime, it.maxDuration, location)
        }.toList()
    }

    /**
     * Checks that the corresponding village (or county) exists
     *
     * @return true if villages are valid
     */
    private fun validateVillagesExist(emergencyInfo: EmergencyInfo, graph: Graph): Boolean {
        return graph.getVillageNames().union(graph.getCountyNames()).contains(emergencyInfo.village)
    }

    /**
     * Check that the corresponding road exists in the village or county
     *
     * @return true if the roads exist
     */
    private fun validateRoadExistsInVillageOrCounty(emergencyInfo: EmergencyInfo, graph: Graph): Boolean {
        val emergencyLocation = Location(emergencyInfo.village, emergencyInfo.roadName)
        return graph.doesLocationExist(emergencyLocation)
    }

    /**
     * Check that the severity is in bounds
     *
     * @return true if severities are valid
     */
    private fun validateSeverityBounds(emergencyInfo: EmergencyInfo): Boolean {
        return emergencyInfo.severity in SEVERITY_MIN..SEVERITY_MAX
    }

    /**
     * Check that the maxDuration does not exceed the handle time
     * Also checks that the duration and handle time are valid
     *
     * @return true if maxDurations and handleTimes are valid
     */
    private fun validateMaxDurationNotExceedHandleTime(emergencyInfo: EmergencyInfo): Boolean {
        return emergencyInfo.handleTime > 0 && emergencyInfo.maxDuration > emergencyInfo.handleTime
    }
}
