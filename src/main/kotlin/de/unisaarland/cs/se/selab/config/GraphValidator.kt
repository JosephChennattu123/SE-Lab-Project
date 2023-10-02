package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.RoadProperties
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.model.map.Vertex

private const val COUNTY_ROAD = "countyRoad"

/**
 * Validates the graph
 */
class GraphValidator {

    private var dotParser: DotParser? = null

    private var vertexIds: Set<Int>? = null

    private var villageToRoads: Map<String, Set<Pair<String, String>>>? = null
    private var edges: Map<Int, List<Connection>>? = null

    /**
     * Validate the information for the graph and create the graph
     *
     * @param dotParser
     * @return the created graph if the information was valid
     */
    fun validate(dotParser: DotParser): Graph? {
        this.dotParser = dotParser

        if (!doValidation()) return null

        if (vertexIds == null) {
            return null
        }

        val edgeStrings = dotParser.parseEdges()
        val sourceTargetMap = dotParser.parseSourceAndTarget(edgeStrings)
        val attributes = dotParser.parseAttributes(edgeStrings)
        val villageNames = dotParser.parseVillageName(attributes)
        val roadNames = dotParser.parseRoadName(attributes)
        val weights = dotParser.parseWeight(attributes)
        val heights = dotParser.parseHeight(attributes)
        val primaryTypes = dotParser.parsePrimaryType(attributes)
        val secondaryTypes = dotParser.parseSecondaryType(attributes)

        val edgeRange = 0 until edgeStrings.count()
        val edgeIds = edgeRange.toList()

        val allEdges: MutableMap<Int, Connection> = mutableMapOf()
        edgeIds.associateWithTo(allEdges) {
            val (source, target) = sourceTargetMap.getValue(it)
            val weight = weights.getValue(it)
            val height = heights.getValue(it)
            val primaryType = PrimaryType.fromString(primaryTypes.getValue(it))
            val secondaryType = SecondaryType.fromString(secondaryTypes.getValue(it))
            val villageName = villageNames.getValue(it)
            val roadName = roadNames.getValue(it)
            Connection(
                source,
                target,
                weight,
                height,
                primaryType as PrimaryType,
                secondaryType as SecondaryType,
                villageName,
                roadName
            )
        }

        edges = mutableMapOf()
        (vertexIds as Set<Int>).associateWithTo(edges as MutableMap<Int, List<Connection>>) {
            allEdges.filter { (_, value) -> value.sourceId == it }.values.toList()
        }

        return buildGraph()
    }

    private fun doValidation(): Boolean {
        val functions = listOf(
            ::validateVertexIds,
            ::validateEdgeConnectsExistingVertices,
            ::validateVerticesConnected,
            ::validateNoSelfLoops,
            ::validateNoDuplicateConnections,
            ::validateVillageNameNotCountyName,
            ::validateRoadNamesUniqueInVillage,
            ::validateVertexConnectedToSingleVillage,
            ::validateMainStreetExistInVillages,
            ::validateSideStreetExists,
            ::validateRoadWeightHeight,
            ::validateMaximumTunnelHeight
        )

        for (i in functions) {
            val returnValue = i()
            if (!returnValue) {
                return false
            }
        }
        return true
    }

    private fun buildGraph(): Graph? {
        val vertices: MutableMap<Int, Vertex> = mutableMapOf()
        for (vertexId in vertexIds as Set<Int>) {
            val vertex = Vertex(vertexId, null, null)
            vertices[vertexId] = vertex
        }
        val graph = Graph(vertices)
        for (connections: Map.Entry<Int, List<Connection>> in edges as Map<Int, List<Connection>>) {
            for (edge: Connection in connections.value) {
                val source: Vertex? = vertices[edge.sourceId]
                val target: Vertex? = vertices[edge.targetId]
                if (source == null || target == null) {
                    return null
                }
                val property = RoadProperties(
                    edge.primary,
                    edge.secondary,
                    edge.villageName,
                    edge.roadName,
                    edge.weight,
                    edge.height
                )
                graph.addEdge(source, target, property)
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
        val vertexIds: List<Int> = (dotParser as DotParser).parseVertexIds()
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
        val dotParserObj = dotParser as DotParser
        val edgesStrings = dotParserObj.parseEdges()
        val sourceTarget = dotParserObj.parseSourceAndTarget(edgesStrings)
        val verticesInEdges: Set<Int> = sourceTarget.values.flatMap { (first, second) -> listOf(first, second) }.toSet()
        return verticesInEdges.containsAll(this.vertexIds as Set<Int>)
    }

    /**
     * Checks that there is no direct edge from a vertex to itself
     *
     * @return true if there are no self loops at vertices
     */
    private fun validateNoSelfLoops(): Boolean {
        val dotParserObj = dotParser as DotParser
        val edgesStrings = dotParserObj.parseEdges()
        val sourceTarget = dotParserObj.parseSourceAndTarget(edgesStrings)
        for (id in this.vertexIds as Set<Int>) {
            if (sourceTarget.containsValue(Pair(id, id))) {
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
        val dotParserObj = dotParser as DotParser
        val edgeString = dotParserObj.parseEdges()
        val sourceTarget = dotParserObj.parseSourceAndTarget(edgeString)
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
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val sourceTarget = dotParserObj.parseSourceAndTarget(edgeStrings)
        val verticesInEdges: Set<Int> = sourceTarget.values.flatMap { (first, second) -> listOf(first, second) }.toSet()
        return (vertexIds as Set<Int>).containsAll(verticesInEdges)
    }

    /**
     * Check that road names are unique in a village
     *
     * @return true if road names are unique in each village
     */
    private fun validateRoadNamesUniqueInVillage(): Boolean {
        // TODO check if correct
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val edgeIdToRoadName = dotParserObj.parseRoadName(attributes)
        val primaryTypes = dotParserObj.parsePrimaryType(attributes)

        val edgeIdToVillageName = dotParserObj.parseVillageName(attributes)
        val villageNameToEdgeIds = reverseMap(edgeIdToVillageName)
        val villageNamesSet = edgeIdToVillageName.map { it.value }.toSet()

        val villageToRoadNames: MutableMap<String, Set<Pair<String, String>>> = mutableMapOf()
        villageNamesSet.associateWithTo(villageToRoadNames) {
            val edgeIds = villageNameToEdgeIds[it] ?: return false
            val roadNamesInVillage: MutableSet<String> = mutableSetOf()
            val villageRoadNamesWithPrimaryType: MutableSet<Pair<String, String>> = mutableSetOf()

            for (edgeId in edgeIds) {
                val nextRoadName: String = edgeIdToRoadName[edgeId] ?: return false
                if (!roadNamesInVillage.contains(nextRoadName)) {
                    roadNamesInVillage.add(nextRoadName)
                    val primaryType = primaryTypes.getValue(edgeId)
                    villageRoadNamesWithPrimaryType.add(Pair(nextRoadName, primaryType))
                } else {
                    return false
                }
            }
            villageRoadNamesWithPrimaryType
        }
        this.villageToRoads = villageToRoadNames.toMap()
        return true
    }

    /**
     * Checks that all vertices only have direct connections to edges
     * of the same village or are countyRoads
     *
     * @return true if vertices only have edges with valid village names or countyRoads
     */
    private fun validateVertexConnectedToSingleVillage(): Boolean {
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val sourceTargetMap = dotParserObj.parseSourceAndTarget(edgeStrings)
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val villageNames = dotParserObj.parseVillageName(attributes)

        val vertexIdToEdges: MutableMap<Int, Pair<List<Int>, List<Int>>> = mutableMapOf()

        val primaryTypes = dotParserObj.parsePrimaryType(attributes)

        (vertexIds as Set<Int>).associateWithTo(vertexIdToEdges) {
            val outEdges = sourceTargetMap.filter { (_, value) -> value.first == it }.keys.toList()
            val inEdges = sourceTargetMap.filter { (_, value) -> value.second == it }.keys.toList()
            Pair(outEdges, inEdges)
        }
        for ((_, value) in vertexIdToEdges) {
            var vertexVillage: String? = null

            val outgoingEdgesIds = value.first
            val incomingEdgesIds = value.second

            val outgoingEdges =
                outgoingEdgesIds.filter { PrimaryType.fromString(primaryTypes.getValue(it)) == PrimaryType.COUNTY }
            val incomingEdges =
                incomingEdgesIds.filter { PrimaryType.fromString(primaryTypes.getValue(it)) == PrimaryType.COUNTY }

            for (outEdge: Int in outgoingEdges) {
                val newVillageName = villageNames[outEdge] ?: return false
                vertexVillage = vertexVillage ?: newVillageName
                if (checkValidConnectedEdge(primaryTypes, outEdge, vertexVillage, newVillageName)) return false
            }
            for (inEdge: Int in incomingEdges) {
                val newVillageName = villageNames[inEdge] ?: return false
                vertexVillage = vertexVillage ?: newVillageName
                if (checkValidConnectedEdge(primaryTypes, inEdge, vertexVillage, newVillageName)) return false
            }
        }
        return true
    }

    /**
     * @param primaryTypes
     * @param edge
     * @param vertexVillage current name of village
     * @param newVillageName new name of village
     */

    private fun checkValidConnectedEdge(
        primaryTypes: Map<Int, String>,
        edge: Int,
        vertexVillage: String?,
        newVillageName: String
    ): Boolean {
        val primaryType = primaryTypes[edge]
        if (vertexVillage != newVillageName && primaryType != COUNTY_ROAD) {
            return true
        }
        return false
    }

    /**
     * Check that there exists a main street in every village
     *
     * @return true if every village has a main street
     */
    private fun validateMainStreetExistInVillages(): Boolean {
        val countyNames = getCountyNames()

        for ((key, value) in villageToRoads as Map<String, Set<Pair<String, String>>>) {
            if (key in countyNames) {
                continue
            }
            if (!value.any { it.second == "mainStreet" }) {
                return false
            }
        }
        return true
    }

    /**
     * Check that there exists at least one side street
     *
     * @return true if a side street exists
     */
    private fun validateSideStreetExists(): Boolean {
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val primaryTypes = dotParserObj.parsePrimaryType(attributes)
        return primaryTypes.containsValue("sideStreet")
    }

    /**
     * Checks that the weights of roads are positive and that
     * the minimum road height is 1
     *
     * @return true if all roads have positive weight
     * @return true if no road exists with a
     */
    private fun validateRoadWeightHeight(): Boolean {
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val weights = dotParserObj.parseWeight(attributes)
        val roadHeights = dotParserObj.parseHeight(attributes)
        return weights.filter { (_, value) -> value <= 0 }.isEmpty() && roadHeights.filter { (_, value) -> value < 1 }
            .isEmpty()
    }

    /**
     *
     * @return true if no road exists with a
     */

    /**
     * Checks that the maximum specified height of a tunnel
     * is not exceeded.
     *
     * @return true if tunnel heights are valid
     */
    private fun validateMaximumTunnelHeight(): Boolean {
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val secondaryTypes = dotParserObj.parseSecondaryType(attributes)
        val heights = dotParserObj.parseHeight(attributes)
        return heights.filter { (key, value) ->
            val secondaryType = secondaryTypes.getValue(key)
            secondaryType == "tunnel" && value > 3
        }.isEmpty()
    }

    /**
     * Checks that no village name is equal to a county name
     */
    private fun validateVillageNameNotCountyName(): Boolean {
        if (dotParser == null) {
            return false
        }
        val countyNames = getCountyNames().toSet()
        val villageNames = getVillageNames().toSet()
        return villageNames.intersect(countyNames).isEmpty()
    }

    private fun getCountyNames(): List<String> {
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val villageNames = dotParserObj.parseVillageName(attributes)
        val primaryTypes = dotParserObj.parsePrimaryType(attributes)

        val countyRoadIds = primaryTypes.filter { (_, type) -> type == COUNTY_ROAD }

        return villageNames.filter { (id, _) -> id in countyRoadIds }.values.toList()
    }

    private fun getVillageNames(): List<String> {
        val dotParserObj = dotParser as DotParser
        val edgeStrings = dotParserObj.parseEdges()
        val attributes = dotParserObj.parseAttributes(edgeStrings)
        val villageNames = dotParserObj.parseVillageName(attributes)
        val primaryTypes = dotParserObj.parsePrimaryType(attributes)

        val countyRoadIds = primaryTypes.filter { (_, type) -> type != COUNTY_ROAD }

        return villageNames.filter { (id, _) -> id in countyRoadIds }.values.toList()
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
