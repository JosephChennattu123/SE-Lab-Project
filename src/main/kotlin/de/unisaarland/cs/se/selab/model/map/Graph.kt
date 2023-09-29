package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.model.Location

/**
 * The graph for the simulation.
 * It contains the vertices and edges/roads for the map of the county.
 */
class Graph(val vertices: MutableMap<Int, Vertex>) {

    private val villages: MutableMap<String, MutableMap<String, Edge>> = mutableMapOf()

    /**
     * @return the list of all edges
     * */
    fun getEdges(): List<Edge> {
        return villages.values.flatMap { it.values }
    }

    /**
     * @param source the id of the source-vertex of the edge
     * @param target the id of the target-vertex of the edge
     * @return the edge for the given source and target
     */
    fun getEdge(source: Int, target: Int): Edge {
        vertices[source]?.getEdges(false)
            ?.find { it.targetVertex.vertexId == target }
            ?.let { return it }
        error("Edge not found: $source -> $target")
    }

    /**
     * @param location the location of the edge
     * @return the edge at the given location
     */
    fun getEdge(location: Location): Edge {
        villages[location.villageName]?.get(location.roadName)?.let { return it }
        error("Edge not found: $location")
    }

    /**
     * creates and connects vertices via an edge.
     * @param source the source-vertex
     * @param target th target-vertex
     * @param properties the properties of the road
     */
    fun addEdge(
        source: Vertex,
        target: Vertex,
        properties: RoadProperties
    ) {
        val edge = Edge.createNewEdge(source, target, properties)
        source.addOutgoingEdge(edge)
        target.addIngoingEdge(edge)
        villages.getOrPut(properties.villageName) { mutableMapOf() }[properties.roadName] = edge
    }
}
