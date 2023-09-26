package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.map.*

/**
 * Validates the graph
 */
class GraphValidator {

    private var dotParser: DotParser? = null

    private var vertexIds: Set<Int> = setOf()
    private var villageNames: List<String> = listOf()
    private var villageToRoads: Map<String, List<String>> = mapOf()
    private var edges: Map<Int, List<Connection>> = mapOf()

    /**
     * Validate the information for the graph and create the graph
     *
     * @param dotParser
     * @return the created graph if the information was valid
     */
    fun validate(dotParser: DotParser): Graph? {
        this.dotParser = dotParser

        val functions = listOf(
            ::validateVertexIds,
            ::validateVerticesConnected,
            ::validateNoSelfLoops,
            ::validateNoDuplicateConnections,
            ::validateEdgeConnectsExistingVertices,
            ::validateSameVertexSameVillageOrCounty,
            ::validateRoadNamesUniqueInVillage,
            ::validateMainStreetExistInVillages,
            ::validateSideStreetExists,
            ::validateNonZeroRoadWeights,
            ::validateMinimumRoadHeight,
            ::validateMaximumTunnelHeight,
            ::validateVillageNameNotCountyName
        )

        for (i in functions) {
            val returnValue = i()
            if (!returnValue) {
                return null
            }
        }
        val graph = Graph()
        val vertices: MutableMap<Int, Vertex> = mutableMapOf()
        for (vertexId in vertexIds) {
            val vertex = Vertex(vertexId)
            vertices.put(vertexId, vertex)
        }
        for (connections: Map.Entry<Int, List<Connection>> in edges) {
            for (edge: Connection in connections.value) {
                val source: Vertex? = vertices.get(connections.key)
                val target: Vertex? = vertices.get(edge.vertexId)
                if (source == null || target == null) {
                    return null
                }
                val property = RoadProperties(edge.primary, edge.secondary, edge.weight, edge.height)
                graph.addEdge(source, target, property, edge.villageName, edge.roadName)
            }
        }
        return graph
    }

    /**
     * Validates the vertexIds.
     * Checks that there are no duplicate vertices and that they are non-negative
     *
     * @return true if the vertices are valid
     */
    private fun validateVertexIds(): Boolean {
        val vertexIds: List<Int> = dotParser!!.vertexIds
        if (vertexIds.map { it >= 0 }.toList().contains(false)) { // no negative elements allowed
            return false
        }
        val vertexIdsSet: Set<Int> = vertexIds.toSet()
        if (vertexIds.count() != vertexIdsSet.count()) { // no duplicate elements allowed
            return false
        }
        this.vertexIds = vertexIdsSet
        return true
    }

    /**
     * Checks that the vertices are each connected to at least another one
     *
     * @return true if all vertices are connected to the graph
     */
    private fun validateVerticesConnected(): Boolean {
        TODO()
    }

    /**
     * Checks that there is no direct edge from a vertex to itself
     *
     * @return true if there are no self loops at vertices
     */
    private fun validateNoSelfLoops(): Boolean {
        TODO()
    }

    /**
     * Checks that there does not exist more than one edge between any to vertices
     *
     * @return true if no duplicate connections exist between to vertices
     */
    private fun validateNoDuplicateConnections(): Boolean {
        TODO()
    }

    /**
     * Checks that vertices connected to and edge do exist
     * @return true if edges are connected to existing vertices
     */
    private fun validateEdgeConnectsExistingVertices(): Boolean {
        TODO()
    }

    /**
     * Checks that all vertices only have direct connections to edges
     * of the same village or the county (countyRoads)
     *
     * @return true if vertices only connected to their village or county
     */
    private fun validateSameVertexSameVillageOrCounty(): Boolean {
        TODO()
    }

    /**
     * Check that road names are unique in a village
     *
     * @return true if road names are unique in each village
     */
    private fun validateRoadNamesUniqueInVillage(): Boolean {
        TODO()
    }

    /**
     * Check that there exists a main street in every village
     *
     * @return true if every village has a main street
     */
    private fun validateMainStreetExistInVillages(): Boolean {
        TODO()
    }

    /**
     * Check that there exists at least one side street
     *
     * @return true if a side street exists
     */
    private fun validateSideStreetExists(): Boolean {
        TODO()
    }

    /**
     * Checks that the weights of road are positive
     *
     * @return true if all roads have positive weight
     */
    private fun validateNonZeroRoadWeights(): Boolean {
        TODO()
    }

    /**
     * Checks that the minimum road height is 1
     * @return true if no road exists with a
     */
    private fun validateMinimumRoadHeight(): Boolean {
        TODO()
    }

    /**
     * Checks that the maximum specified height of a tunnel
     * is not exceeded.
     *
     * @return true if tunnel heights are valid
     */
    private fun validateMaximumTunnelHeight(): Boolean {
        TODO()
    }

    /**
     * Checks that no village name is equal to a county name
     */
    private fun validateVillageNameNotCountyName(): Boolean {
        // TODO newly added (was missing
        TODO()
    }
}
