package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.SecondaryType

/**
 * @param vertexId the target of the connection
 * @param name the name of the connection(road)
 * @param weight the weight of the connection
 * @param direction the direction of the connection
 */
data class Connection(
    val vertexId: Int, val name: String, val weight: Int, val height: Int, val direction: Direction,
    val primary: PrimaryType, val secondary: SecondaryType, val villageName: String, val roadName: String
)

/**
 * direction of a road / connection:
 */
enum class Direction {
    /**
     * only from source to target of road / connection
     */
    ONEWAY,

    /**
     * both direction
     */
    BOTH
}
