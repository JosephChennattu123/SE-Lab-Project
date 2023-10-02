package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.model.Location

/**
 * The graph for the simulation.
 * It contains the vertices and edges/roads for the map of the county.
 */
class Graph(val vertices: MutableMap<Int, Vertex>) {

    private val countyNames: MutableSet<String> = mutableSetOf()
    private val villageNames: MutableSet<String> = mutableSetOf()
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
     * @param location the location to check
     * @return true if an edge with that location exists
     */
    fun doesLocationExist(location: Location): Boolean {
        val villageMap = villages[location.villageName] ?: return false
        return villageMap[location.roadName] != null
    }

    /**
     * @param source source of edge
     * @param target target of edge
     * @return true if edge does exist
     */
    fun doesEdgeExist(source: Int, target: Int): Boolean {
        val vertex = vertices[source] ?: return false
        return vertex.getEdges(false).any { it.targetVertex.vertexId == target }
    }

    /**
     * @return the set of village names
     */
    fun getVillageNames(): Set<String> {
        return this.villageNames
    }

    /**
     * @return the set of county names
     */
    fun getCountyNames(): Set<String> {
        return this.countyNames
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
        if (properties.secondaryType != SecondaryType.ONE_WAY) {
            source.addIngoingEdge(edge)
            target.addOutgoingEdge(edge)
        }
        villages.getOrPut(properties.villageName) { mutableMapOf() }[properties.roadName] = edge
        if (properties.roadType == PrimaryType.COUNTY) {
            countyNames.add(properties.villageName)
        } else {
            villageNames.add(properties.villageName)
        }
    }
}
