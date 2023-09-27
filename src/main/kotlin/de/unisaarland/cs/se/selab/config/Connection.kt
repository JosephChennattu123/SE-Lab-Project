package de.unisaarland.cs.se.selab.config

/**
 * @param vertexId the target of the connection
 * @param name the name of the connection(road)
 * @param weight the weight of the connection
 * @param direction the direction of the connection
 */
data class Connection(
    val vertexId: Int, val name: String,
    val weight: Int, val direction: Direction
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
