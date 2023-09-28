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
    val sourceVertex: Vertex,
    val targetVertex: Vertex,
    var properties: RoadProperties,
    var activeEventId: Int?,
    var closed: Boolean
) {

    /**
     * @return the weight of the edge
     */
    fun getWeight(): Int {
        return properties.weight
    }

    /** removes active event */
    fun removeActiveEvent() {
        TODO()
    }

    /** sets the closed boolean to true */
    fun closeRoad() {
        TODO()
    }

    /**
     * @return true if the secondary type is ONE_WAY
     */
    fun isOneWay(): Boolean {
        return properties.secondaryType == SecondaryType.ONE_WAY
    }
}
