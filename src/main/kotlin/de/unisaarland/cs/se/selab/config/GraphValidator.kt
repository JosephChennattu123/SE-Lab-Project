package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.RoadProperties
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.model.map.Vertex
import de.unisaarland.cs.se.selab.util.Logger
import kotlin.NumberFormatException

// private const val COUNTY_ROAD = "countyRoad"

/**
 * Validates the graph
 */
class GraphValidator {

    private var dotParser: DotParser? = null

    private var vertexIds: Set<Int> = emptySet()

    private var villageToRoads: Map<String, Set<Pair<String, String>>>? = null
    private var edges: Map<Int, List<Connection>>? = null

    private val sourceTarget: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()
    private var attributes: Map<Int, Map<String, String>> = emptyMap()
    private var heights: Map<Int, Int> = emptyMap()
    private var weights: Map<Int, Int> = emptyMap()

    private var primaryTypes: Map<Int, PrimaryType> = emptyMap()
    private var secondaryTypes: Map<Int, SecondaryType> = emptyMap()

    /**
     * Validate the information for the graph and create the graph
     *
     * @param dotParser
     * @return the created graph if the information was valid
     */
    fun validate(dotParser: DotParser): Graph? {
        this.dotParser = dotParser

        if (!doValidation()) return null

        if (vertexIds == emptySet<Int>()) {
            return null
        }

        // val edgeStrings = dotParser.parseEdges()
        // val sourceTargetMap = dotParser.parseSourceAndTarget(edgeStrings)
        // val attributes = dotParser.parseAttributes(edgeStrings)
        // val villageNames = dotParser.parseVillageName(attributes)
        // val roadNames = dotParser.parseRoadName(attributes)
        // val weights = dotParser.parseWeight(attributes)
        // val heights = dotParser.parseHeight(attributes)
        // val primaryTypes = dotParser.parsePrimaryType(attributes)
        // val secondaryTypes = dotParser.parseSecondaryType(attributes)

        val edgeRange = 1..attributes.size
        val edgeIds = edgeRange.toList()

        val allEdges: MutableMap<Int, Connection> = mutableMapOf()
        edgeIds.associateWithTo(allEdges) {
            val (source, target) = sourceTarget.getValue(it)
            val weight = weights.getValue(it - 1)
            val height = heights.getValue(it - 1)
            val primaryType = primaryTypes.getValue(it - 1)
            val secondaryType = secondaryTypes.getValue(it - 1)
            val villageName = attributes.getValue(it - 1).getValue(LABEL_VILLAGE)
            val roadName = attributes.getValue(it - 1).getValue(LABEL_ROAD_NAME)
            Connection(
                source,
                target,
                weight,
                height,
                primaryType,
                secondaryType,
                villageName,
                roadName
            )
        }

        edges = mutableMapOf()
        vertexIds.associateWithTo(edges as MutableMap<Int, List<Connection>>) {
            allEdges.filter { (_, value) -> value.sourceId == it }.values.toList()
        }

        return buildGraph()
    }

    private fun doValidation(): Boolean {
        val dotParserObj = dotParser as DotParser

        val validSyntax = dotParserObj.parse()

        if (!validSyntax) {
            Logger.outputLogger.error { "Graph has invalid syntax" }
            return false
        }

        var exceptionOccurred = false
        try {
            val vertexIdsList = dotParserObj.vertices.map { it.toInt() }
            val ret = validateVertexIds(vertexIdsList)
            if (!ret) {
                Logger.outputLogger.error { "vertex ids are invalid" }
                return false
            }
            dotParserObj.edgeIdToSourceTarget.forEach { (key, pair) ->
                sourceTarget[key] = Pair(pair.first.toInt(), pair.second.toInt())
            }

            attributes = dotParserObj.edgeIdToAttributes
            heights = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_HEIGHT_LIMIT).toInt()) }.toMap()
            weights = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_WEIGHT).toInt()) }.toMap()

            val returnedPair = getPrimaryAndSecondaryTypes()
            if (returnedPair == null) {
                Logger.outputLogger.error { "Was not able to convert String to PrimaryType/SecondaryType" }
                exceptionOccurred = true
            } else {
                primaryTypes = returnedPair.first
                secondaryTypes = returnedPair.second
            }
        } catch (exception: NumberFormatException) {
            Logger.outputLogger.error { "Was not able to convert String to In" }
            exceptionOccurred = true
        }
        if (exceptionOccurred) {
            return false
        }

        val functions = listOf(
            ::validateEdgeConnectsExistingVertices,
            ::validateNoSelfLoops,
            ::validateVerticesConnected,
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
                Logger.outputLogger.error { "${i.name} failed" }
                return false
            }
        }
        return true
    }

    private fun getPrimaryAndSecondaryTypes(): Pair<MutableMap<Int, PrimaryType>, MutableMap<Int, SecondaryType>>? {
        val primaryMap: MutableMap<Int, PrimaryType> = mutableMapOf()
        for ((key, value) in attributes) {
            val type = PrimaryType.fromString(value.getValue(LABEL_PRIMARY_TYPE))
            val valuePrimary = type ?: return null
            primaryMap[key] = valuePrimary
        }

        val secondaryMap: MutableMap<Int, SecondaryType> = mutableMapOf()
        for ((key, value) in attributes) {
            val type = SecondaryType.fromString(value.getValue(LABEL_SECONDARY_TYPE))
            val valuePrimary = type ?: return null
            secondaryMap[key] = valuePrimary
        }
        return Pair(primaryMap, secondaryMap)
    }

    private fun buildGraph(): Graph? {
        val vertices: MutableMap<Int, Vertex> = mutableMapOf()
        for (vertexId in vertexIds) {
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
    private fun validateVertexIds(vertexIdsList: List<Int>): Boolean {
        if (vertexIdsList.map { it >= 0 }.contains(false)) { // no negative elements allowed
            return false
        }
        val vertexIdsSet: Set<Int> = vertexIdsList.toSet()
        if (vertexIdsList.count() != vertexIdsSet.count()) { // no duplicate elements allowed
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
        val verticesInEdges: Set<Int> = sourceTarget.values.flatMap { (first, second) -> listOf(first, second) }.toSet()
        return verticesInEdges.containsAll(this.vertexIds)
    }

    /**
     * Checks that there is no direct edge from a vertex to itself
     *
     * @return true if there are no self loops at vertices
     */
    private fun validateNoSelfLoops(): Boolean {
        for (id in this.vertexIds) {
            if (sourceTarget.containsValue(Pair(id, id))) {
                Logger.outputLogger.error { "found a self loop" }
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
        for (pair in sourceTarget.values) {
            val v1 = sourceTarget.values.count { (first, second) -> pair.first == first && pair.second == second }
            val v2 = sourceTarget.values.count { (first, second) -> pair.second == first && pair.first == second }
            if (v1 + v2 > 1) {
                Logger.outputLogger.error { "found more a duplicate connection" }
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
        val verticesInEdges: Set<Int> = sourceTarget.values.flatMap { (first, second) -> listOf(first, second) }.toSet()
        return vertexIds.containsAll(verticesInEdges)
    }

    /**
     * Check that road names are unique in a village
     *
     * @return true if road names are unique in each village
     */
    private fun validateRoadNamesUniqueInVillage(): Boolean {
        // TODO check if correct
        // val dotParserObj = dotParser as DotParser
        // val edgeStrings = dotParserObj.parseEdges()
        // val attributes = dotParserObj.parseAttributes(edgeStrings)
        // val edgeIdToRoadName = dotParserObj.parseRoadName(attributes)
        // val primaryTypes = dotParserObj.parsePrimaryType(attributes)

        // val edgeIdToVillageName = dotParserObj.parseVillageName(attributes)
        val edgeIdToVillageName = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_VILLAGE)) }.toMap()
        val edgeIdToRoadName = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_ROAD_NAME)) }.toMap()
        val primaryTypes = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_PRIMARY_TYPE)) }.toMap()

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
                    Logger.outputLogger.error { "!roadNamesInVillage.contains(nextRoadName) failed" }
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
     * @return true if vertices only have edges with a single village names each or are countyRoads
     */
    private fun validateVertexConnectedToSingleVillage(): Boolean {
        val villageLabelsOfEdges = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_VILLAGE)) }.toMap()

        val vertexIdToEdges: MutableMap<Int, Pair<List<Int>, List<Int>>> = mutableMapOf()
        // val primaryTypes = dotParserObj.parsePrimaryType(attributes)
        vertexIds.associateWithTo(vertexIdToEdges) {
            val outEdges = sourceTarget.filter { (_, value) -> value.first == it }.keys.toList()
            val inEdges = sourceTarget.filter { (_, value) -> value.second == it }.keys.toList()
            Pair(outEdges, inEdges)
        }
        for ((_, value) in vertexIdToEdges) {
            var vertexVillage: String? = null

            val outgoingEdgesIds = value.first
            val incomingEdgesIds = value.second

            val outgoingEdges =
                outgoingEdgesIds.filter {
                    primaryTypes.getValue(it - 1) == PrimaryType.COUNTY
                }
            val incomingEdges =
                incomingEdgesIds.filter { primaryTypes.getValue(it - 1) == PrimaryType.COUNTY }

            outgoingEdges.forEach { outEdge ->
                val newVillageName = villageLabelsOfEdges[outEdge] ?: return false
                if (getCountyOrVillageNames(false).contains(newVillageName)) {
                    vertexVillage = vertexVillage ?: newVillageName
                }
                if (!checkValidConnectedEdge(primaryTypes, outEdge, vertexVillage, newVillageName)) return false
            }
            incomingEdges.forEach { inEdge ->
                val newVillageName = villageLabelsOfEdges[inEdge] ?: return false
                if (getCountyOrVillageNames(false).contains(newVillageName)) {
                    vertexVillage = vertexVillage ?: newVillageName
                }
                if (!checkValidConnectedEdge(primaryTypes, inEdge, vertexVillage, newVillageName)) return false
            }
        }
        return true
    }

    /**
     * @param primaryTypes
     * @param edge
     * @param vertexVillage current name of village
     * @param newVillageName new name of village
     * @return false if newVillage is a different village but the road is not a countyRoad
     */
    private fun checkValidConnectedEdge(
        primaryTypes: Map<Int, PrimaryType>,
        edge: Int,
        vertexVillage: String?,
        newVillageName: String
    ): Boolean {
        val primaryType = primaryTypes[edge]
        if (vertexVillage != null && vertexVillage != newVillageName && primaryType != PrimaryType.COUNTY) {
            Logger.outputLogger.error {
                "found a edge to a village that is not the same as " +
                    "the first village connected to the vertex"
            }
            return false
        }
        return true
    }

    /**
     * Check that there exists a main street in every village
     *
     * @return true if every village has a main street
     */
    private fun validateMainStreetExistInVillages(): Boolean {
        val countyNames = getCountyOrVillageNames(true)

        for ((key, value) in villageToRoads as Map<String, Set<Pair<String, String>>>) {
            if (key in countyNames) {
                continue
            }
            if (!value.any { it.second == "mainStreet" }) {
                Logger.outputLogger.error { "did not find a mainStreet in a village" }
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
        // val dotParserObj = dotParser as DotParser
        // val edgeStrings = dotParserObj.parseEdges()
        // val attributes = dotParserObj.parseAttributes(edgeStrings)
        // val primaryTypes = dotParserObj.parsePrimaryType(attributes)
        val primaryTypes = attributes.map { (key, value) -> Pair(key, value.getValue(LABEL_PRIMARY_TYPE)) }.toMap()
        val v = primaryTypes.containsValue(LABEL_SIDE_STREET)
        if (v) {
            return true
        }
        Logger.outputLogger.error { "did not find a sideStreet on the map" }
        return false
    }

    /**
     * Checks that the minimum weights of roads is 1 and that
     * the minimum road height is 1
     *
     * @return true if all roads have positive weight and height
     */
    private fun validateRoadWeightHeight(): Boolean {
        val weightsEmpty = weights.filter { (_, value) -> value < 1 }.isEmpty()
        val heightsEmpty = heights.filter { (_, value) -> value < 1 }.isEmpty()
        if (!weightsEmpty) {
            Logger.outputLogger.error { "found a non positive weight" }
            return false
        }
        if (!heightsEmpty) {
            Logger.outputLogger.error { "found a non positive height" }
            return false
        }
        return true
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
        // val dotParserObj = dotParser as DotParser
        // val edgeStrings = dotParserObj.parseEdges()
        // val attributes = dotParserObj.parseAttributes(edgeStrings)
        // val secondaryTypes = dotParserObj.parseSecondaryType(attributes)
        // val heights = dotParserObj.parseHeight(attributes)
        return heights.filter { (key, value) ->
            val secondaryType = secondaryTypes.getValue(key)
            secondaryType == SecondaryType.TUNNEL && value > 3
        }.isEmpty()
    }

    /**
     * Checks that no village name is equal to a county name
     */
    private fun validateVillageNameNotCountyName(): Boolean {
        val countyNames = getCountyOrVillageNames(true).toSet()
        val villageNames = getCountyOrVillageNames(false).toSet()
        return villageNames.intersect(countyNames).isEmpty()
    }

    /**
     * Returns the list containing all village names (non-countyRoad) or all county names
     * @param county true if it should output county names else should output village names
     * @return a list containing names
     */
    private fun getCountyOrVillageNames(county: Boolean): List<String> {
        return if (county) {
            attributes.values
                .filter { it[LABEL_PRIMARY_TYPE] == LABEL_COUNTY_ROAD }
                .map { it[LABEL_VILLAGE] as String }
        } else { // non countyRoads i.e. village names
            attributes.values
                .filter { it[LABEL_PRIMARY_TYPE] != LABEL_COUNTY_ROAD }
                .map { it[LABEL_VILLAGE] as String }
        }
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
