package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.EmergencyType

/**
 * Collects the info to construct emergencies
 *
 * @param id the id of the emergency
 * @param tick the tick in which the emergency starts
 * @param village the name of the village where the emergency happens
 * @param roadName the name of the road where the emergency happens
 * @param emergencyType the type of the emergency
 * @param severity the severity of the emergency
 * @param handleTime the amount of ticks it takes to resolve the emergency
 * @param maxDuration the maximum duration in ticks before an emergency fails
 */
data class EmergencyInfo(
    val id: Int,
    val tick: Int,
    val village: String,
    val roadName: String,
    val emergencyType: EmergencyType,
    val severity: Int,
    val handleTime: Int,
    val maxDuration: Int
)
