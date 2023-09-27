package de.unisaarland.cs.se.selab.model.map

/**
 * @param edgeId
 * @param sourceVertex
 * @param targetVertex
 * @param properties
 * @param activeEventId
 * @param closed
 * */
class Edge(
    val edgeId: Int,
    val sourceVertex: Int,
    val targetVertex: Int,
    var properties: RoadProperties,
    var activeEventId: Int?,
    var closed: Boolean
) {
/** removes active event */
    fun removeActiveEvent() {
        TODO()
    }

/** sets the closed boolean to true */
    fun closeRoad() {
        TODO()
    }
}
