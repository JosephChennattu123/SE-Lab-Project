package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.model.Location

/**
 * The graph for the simulation.
 * It contains the vertices and edges/roads for the map of the county.
 */
class Graph {

    val vertices: MutableMap<Int, Vertex> = mutableMapOf()
    val villages: MutableMap<String, MutableMap<String, Edge>> = mutableMapOf()

    /**
     * @return the list of all edges
     * */
    fun getEdges(): List<Edge> {
        TODO()
    }

    /**
     * @param source the id of the source-vertex of the edge
     * @param target the id of the target-vertex of the edge
     * @return the edge for the given source and target
     */
    fun getEdge(source: Int, target: Int): Edge {
        source
        target
        TODO()
    }

    /**
     * @param location the location of the edge
     * @return the edge at the given location
     */
    fun getEdge(location: Location): Edge {
        location
        TODO()
    }

    /**
     * @param source the source-vertex
     * @param target th target-vertex
     * @param properties the properties of the road
     * @param villageName the name of the village of the road
     * @param roadName the name of the road
     */
    fun addEdge(
        source: Vertex,
        target: Vertex,
        properties: RoadProperties,
        villageName: String,
        roadName: String
    ) {
        source
        target
        properties
        villageName
        roadName
        TODO()
    }

    /**
     * @param edgeID the id of an edge
     * @return the edge with the given id
     */
    fun getEdge(edgeID: Int): Edge {
        edgeID
        TODO()
    }
}
