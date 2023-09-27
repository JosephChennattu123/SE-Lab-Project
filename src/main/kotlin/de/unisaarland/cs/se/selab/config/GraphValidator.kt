package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.map.*

/**
 * Validates the graph
 */
class GraphValidator {

    private var dotParser: DotParser? = null

    private var vertexIds: Set<Int>? = null
    private var villageNames: List<String>? = null
    private var villageToRoads: Map<String, Set<String>>? = null
    private var edges: Map<Int, List<Connection>>? = null

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
            ::validateEdgeConnectsExistingVertices,
            ::validateVerticesConnected,
            ::validateNoSelfLoops,
            ::validateNoDuplicateConnections,
            ::validateRoadNamesUniqueInVillage,
            ::validateSameVertexSameVillageOrCounty,
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
        for (vertexId in vertexIds!!) {
            val vertex = Vertex(vertexId)
            vertices.put(vertexId, vertex)
        }
        for (connections: Map.Entry<Int, List<Connection>> in edges!!) {
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
        val vertexIds: List<Int> = dotParser!!.parseVertexIds()
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
        val edges = dotParser!!.parseEdges()
        val sourceTarget = dotParser!!.parseSourceAndTarget(edges)
        val verticesInEdges: Set<Int> = sourceTarget.values.flatMap { (first, second) -> listOf(first, second) }.toSet()
        return verticesInEdges.containsAll(this.vertexIds!!)
    }

    /**
     * Checks that there is no direct edge from a vertex to itself
     *
     * @return true if there are no self loops at vertices
     */
    private fun validateNoSelfLoops(): Boolean {
        val edges = dotParser!!.parseEdges()
        val soureTarget = dotParser!!.parseSourceAndTarget(edges)
        for (id in this.vertexIds!!) {
            if (soureTarget.containsValue(Pair(id, id))) {
                return false
            }
        }
        return true
    }

    /**
     * Checks that there does not exist more than one edge between any to vertices
     *
     * @return true if no duplicate connections exist between to vertices
     */
    private fun validateNoDuplicateConnections(): Boolean {
        val edges = dotParser!!.parseEdges()
        val sourceTarget = dotParser!!.parseSourceAndTarget(edges)
        for (pair in sourceTarget.values) {
            val v1 = sourceTarget.values.count { (first, second) -> pair.first == first && pair.second == second }
            val v2 = sourceTarget.values.count { (first, second) -> pair.second == first && pair.first == second }
            if (v1 + v2 > 1) {
                return false
            }
        }
        return true
    }

    /**
     * Checks that vertices connected to an edge do exist
     * @return true if edges are connected to existing vertices
     */
    private fun validateEdgeConnectsExistingVertices(): Boolean {
        val edges = dotParser!!.parseEdges()
        val sourceTarget = dotParser!!.parseSourceAndTarget(edges)
        val verticesInEdges: Set<Int> = sourceTarget.values.flatMap { (first, second) -> listOf(first, second) }.toSet()
        return vertexIds!!.containsAll(verticesInEdges)
    }

    /**
     * Check that road names are unique in a village
     *
     * @return true if road names are unique in each village
     */
    private fun validateRoadNamesUniqueInVillage(): Boolean {
        // TODO check if correct
        val edges = dotParser!!.parseEdges()
        val attributes = dotParser!!.parseAttributes(edges)
        val edgeIdToRoadName = dotParser!!.parseRoadName(attributes)
        val edgeIdToVillageName = dotParser!!.parseVillageName(attributes)
        val villageNameToEdgeIds = reverseMap(edgeIdToVillageName)

        val villageNamesSet = edgeIdToVillageName.map { it.value }.toSet()
        val villageToRoadNames: MutableMap<String, Set<String>> = mutableMapOf()
        villageNamesSet.associateWithTo(
            villageToRoadNames
        ) {
            val edgeIds = villageNameToEdgeIds[it] ?: return false
            val villageRoadNames: MutableSet<String> = mutableSetOf()
            for (edgeId in edgeIds) {
                val roadName: String = edgeIdToRoadName[edgeId] ?: return false
                if (!villageRoadNames.contains(roadName)) {
                    villageRoadNames.add(roadName)
                } else {
                    return false
                }
            }
            villageRoadNames
        }
        this.villageToRoads = villageToRoadNames.toMap()
        return true
    }

    /**
     * Checks that all vertices only have direct connections to edges
     * of the same village or the county (countyRoads)
     *
     * @return true if vertices only connected to their village or county
     */
    private fun validateSameVertexSameVillageOrCounty(): Boolean {
        val edgeStrings = dotParser!!.parseEdges()
        val sourceTargetMap = dotParser!!.parseSourceAndTarget(edgeStrings)
        val attributes = dotParser!!.parseAttributes(edgeStrings)
        val villageNames = dotParser!!.parseVillageName(attributes)
        val countyName = dotParser!!.parseCountyName()

        val vertexIdToEdges: MutableMap<Int, Pair<List<Int>, List<Int>>> = mutableMapOf()
        vertexIds!!.associateWithTo(vertexIdToEdges) {

            val outEdges = sourceTargetMap.filter { (_, value) -> value.first == it }.keys.toList()
            val inEdges = sourceTargetMap.filter { (_, value) -> value.second == it }.keys.toList()
            Pair(outEdges, inEdges)
        }
        for ((_, value) in vertexIdToEdges) {
            var vertexVillage: String? = null

            val outgoingEdges = value.first
            val incomingEdges = value.second
            for (outEdge: Int in outgoingEdges) {
                val newVillageName = villageNames[outEdge] ?: return false
                vertexVillage = vertexVillage ?: newVillageName
                if (vertexVillage != newVillageName && newVillageName != countyName) {
                    return false
                }
            }
            for (inEdge: Int in incomingEdges) {
                val newVillageName = villageNames[inEdge] ?: return false
                vertexVillage = vertexVillage ?: newVillageName
                if (vertexVillage != newVillageName && newVillageName != countyName) {
                    return false
                }
            }
        }
        return true
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
        val edgeStrings = dotParser!!.parseEdges()
        val attributes = dotParser!!.parseAttributes(edgeStrings)
        val roadHeights = dotParser!!.parseHeight(attributes)
        return roadHeights.filter { (_, value) -> value < 1 }.isEmpty()
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

    /**
     * Reverses a map.
     * It uses the values as new keys and as values collects all keys that had the same value
     *
     * @param inputMap map from a key to a value
     * @return the reversed map
     */
    private fun <K, V> reverseMap(inputMap: Map<K, V>): Map<V, List<K>> {
        val reversed = mutableMapOf<V, MutableList<K>>()

        for ((key, value) in inputMap) {
            reversed.computeIfAbsent(value) { mutableListOf() }.add(key)
        }

        return reversed.mapValues { it.value.toList() }
    }
}
