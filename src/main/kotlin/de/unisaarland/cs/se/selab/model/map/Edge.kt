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
    var activeEventId: Int? = null,
    var closed: Boolean = false,
    var activeEmergencyId: Int? = null
) {

    companion object {
        private var currentAvailableEdgeId = 0

        /**
         * Creates new edge object with a unique id.
         * */
        fun createNewEdge(
            sourceVertex: Vertex,
            targetVertex: Vertex,
            properties: RoadProperties,
            activeEventId: Int? = null,
            closed: Boolean = false,
            activeEmergencyId: Int? = null
        ): Edge {
            val newEdge = Edge(
                currentAvailableEdgeId,
                sourceVertex,
                targetVertex,
                properties,
                activeEventId,
                closed,
                activeEmergencyId

            )
            currentAvailableEdgeId++
            return newEdge
        }
    }

    /**
     * @return the weight of the edge
     */
    fun getWeight(): Int {
        return properties.getWeight()
    }

    /** removes active event */
    fun removeActiveEvent() {
        closed = false
        activeEventId = null
    }

    /** sets the closed boolean to true */
    fun closeRoad() {
        closed = true
    }

    /**
     * @return true if the secondary type is ONE_WAY meaning there is no edge in the opposite
     * direction
     */
    fun isOneWay(): Boolean {
        return properties.secondaryType == SecondaryType.ONE_WAY
    }
}
