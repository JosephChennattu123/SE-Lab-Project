package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.SecondaryType

/**
 * @param sourceId the source of the connection
 * @param targetId the target of the connection
 * @param weight the weight of the connection
 * @param height the height of the road (tunnel, ...)
 * @param primary the primary type of the road
 * @param secondary the secondaryType of the road
 * @param villageName the name of the village this road belongs to (or the county name)
 * @param roadName the name of the road
 */
data class Connection(
    val sourceId: Int, val targetId: Int, val weight: Int, val height: Int,
    val primary: PrimaryType, val secondary: SecondaryType, val villageName: String, val roadName: String
)
