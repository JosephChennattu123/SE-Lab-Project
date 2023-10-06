package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Location
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.Vertex
import de.unisaarland.cs.se.selab.model.vehicle.Path

/**
 * Util class to calculate the shortest path on a graph using Dijkstra's algorithm.
 * */
object Dijkstra {

    private const val speed: Int = 10
    private const val num9: Int = 9
    private const val THE_NUMBER_ONE = 1

    /**
     * Finds the nearest base to the given edge given as location.
     * @param location the location of the edge.
     * @param baseType the type of the required base.
     * @return the vertex id of the nearest base.
     * */
    fun getNearestBaseVertexIdToEdge(graph: Graph, location: Location, baseType: BaseType): Int? {
        val edge = graph.getEdge(location)
        val nearestBasesToTarget = findNearestBases(graph, edge.targetVertex.vertexId, baseType)
        val nearestBasesToSource = findNearestBases(graph, edge.sourceVertex.vertexId, baseType)
        val nearestBaseToTarget = nearestBasesToTarget?.minBy { it.value }
        val nearestBaseToSource = nearestBasesToSource?.minBy { it.value }
        if (nearestBaseToSource != null) {
            if (nearestBaseToTarget != null) {
                return if (nearestBasesToSource.getValue(nearestBaseToSource.key)
                    < nearestBasesToTarget.getValue(nearestBaseToTarget.key)
                ) {
                    nearestBaseToSource.key
                } else {
                    nearestBaseToTarget.key
                }
            }
        }
        return null
    }

    /**
     * Finds the nearest base relative to the Main base which has not been visited yed.
     * @param sourceVertex the vertex id of the vertex.
     * @param baseType the type of the required base.
     * @param requestedBases the set of base vertices that have already been visited.
     * @return the vertex id of the nearest base.
     * */
    fun getNextNearestBase(
        graph: Graph,
        sourceVertex: Int,
        baseType: BaseType,
        requestedBases: Set<Int>
    ): Int? {
        val nearestBases = findNearestBases(graph, sourceVertex, baseType, requestedBases)
        if (nearestBases != null) {
            return nearestBases.minBy { it.value }.key
        }
        return null
    }

    /**
     * calculates the shortest path when a vehicle needs to be reallocated to a different emergency
     * and is already on the way to one.
     * @param lastVisitedVertex the source vertex of the current traversed edge.
     * @param nextVertexToVisit the target vertex of the current traversed edge.
     * @param distanceFromSourceVertex the current distance on edge relative to source.
     * @param targetEdgeLocation the location of the target edge.
     * @param height the height of the vehicle.
     * @return Path object containing the shortest path.
     * */
    fun getShortestPathFromEdgeToEdge(
        graph: Graph,
        lastVisitedVertex: Int,
        nextVertexToVisit: Int,
        distanceFromSourceVertex: Int,
        targetEdgeLocation: Location,
        height: Int
    ): Path {
        val edge = graph.getEdge(targetEdgeLocation)
        // TODO check for one way
        val pathToTargetVertex =
            getShortestPathFromEdgeToVertex(
                graph,
                lastVisitedVertex,
                nextVertexToVisit,
                edge.targetVertex.vertexId,
                distanceFromSourceVertex,
                height
            )
        val pathToSourceVertex = getShortestPathFromEdgeToVertex(
            graph,
            lastVisitedVertex,
            nextVertexToVisit,
            edge.sourceVertex.vertexId,
            distanceFromSourceVertex,
            height
        )

        return if (pathToTargetVertex.totalTicksToArrive < pathToSourceVertex.totalTicksToArrive) {
            pathToTargetVertex
        } else if (pathToSourceVertex.getTotalDistance() == pathToTargetVertex.getTotalDistance()) {
            getLexSmallerPath(pathToSourceVertex, pathToTargetVertex)
        } else {
            pathToSourceVertex
        }
    }

    /**
     * calculates the shortest path between two vertices when vehicle needs to be sent to
     * an emergency or to back to base once emergency is finished.
     * @param sourceVertex the vertex of a base or emergency.
     * @param targetVertex the vertex of an emergency or base.
     * @param height the height of the vehicle.
     * @return Path object containing the shortest path.
     * */
    fun getShortestPathFromVertexToVertex(
        graph: Graph,
        sourceVertex: Int,
        targetVertex: Int,
        height: Int
    ): Path {
        val parentMap = HashMap<Int, Int>()
        val distanceFromSourceVertex =
            dijkstraAlgorithm(graph, targetVertex, true, height, parentMap)
        val path = reconstructPath(sourceVertex, targetVertex, parentMap)
        val weights: MutableList<Int> = mutableListOf()
        val isOneWay: MutableList<Boolean> = mutableListOf()
        for (i in 0 until path.size - 1) {
            weights.add(graph.getEdge(path[i], path[i + 1]).getWeight())
            isOneWay.add(
                graph.getEdge(
                    path[i],
                    path[i + 1]
                ).isOneWay()
            )
        }
        return Path(
            path,
            weights,
            isOneWay,
            getRoundedTicks(distanceFromSourceVertex.getValue(sourceVertex))
        )
    }

    /**
     * calculates the shortest path between a vertex and an edge when vehicle needs to be sent to
     * an emergency.
     * @param sourceVertex the vertex of a base or emergency.
     * @param location the location of the edge.
     * @param height the height of the vehicle.
     * @return Path object containing the shortest path.
     * */
    fun getShortestPathFromVertexToEdge(
        graph: Graph,
        sourceVertex: Int,
        location: Location,
        height: Int
    ): Path {
        val edge = graph.getEdge(location)
        val pathToEdgeSource =
            getShortestPathFromVertexToVertex(
                graph,
                sourceVertex,
                edge.sourceVertex.vertexId,
                height
            )
        val pathToEdgeTarget =
            getShortestPathFromVertexToVertex(
                graph,
                sourceVertex,
                edge.targetVertex.vertexId,
                height
            )
        if (pathToEdgeSource.getTotalDistance() < pathToEdgeTarget.getTotalDistance()) {
            return pathToEdgeSource
        } else if (pathToEdgeSource.getTotalDistance() > pathToEdgeTarget.getTotalDistance()) {
            return pathToEdgeTarget
        }
        return getLexSmallerPath(pathToEdgeSource, pathToEdgeTarget)
    }

    private fun getLexSmallerPath(pathToEdgeSource: Path, pathToEdgeTarget: Path): Path {
        val pathWithLessVertices: Path?
        val otherPath: Path?
        if (pathToEdgeSource.vertexPath.count() < pathToEdgeTarget.vertexPath.count()) {
            pathWithLessVertices = pathToEdgeSource
            otherPath = pathToEdgeTarget
        } else {
            pathWithLessVertices = pathToEdgeTarget
            otherPath = pathToEdgeSource
        }
        for (id in 0 until pathWithLessVertices.vertexPath.size) {
            if (pathWithLessVertices.vertexPath[id] < otherPath.vertexPath[id]) {
                return pathWithLessVertices
            } else if (pathWithLessVertices.vertexPath[id] > otherPath.vertexPath[id]) {
                return otherPath
            }
        }
        return pathWithLessVertices
    }

    /**
     * calculates the shortest path from an edge to a vertex when vehicle needs to be returned
     * to base midway as an emergency failed.
     * @param sourceVertex the source vertex of the edge on which vehicle currently travels.
     * @param targetVertex the target vertex of the currently travelled edge.
     * @param destinationVertex the vertex of the base.
     * @param height the height of the vehicle.
     * @return Path object containing the shortest path.
     * */
    fun getShortestPathFromEdgeToVertex(
        graph: Graph,
        sourceVertex: Int,
        targetVertex: Int,
        destinationVertex: Int,
        currentDistanceOnEdge: Int,
        height: Int
    ): Path {
        val edge = graph.getEdge(sourceVertex, targetVertex)
        val pathFromTarget =
            getShortestPathFromVertexToVertex(
                graph,
                targetVertex,
                destinationVertex,
                height
            )
        // update path from target to include the remaining edge.
        // add remaining edge to be travelled.
        val path: MutableList<Int> = mutableListOf()
        path.add(sourceVertex)
        path.addAll(pathFromTarget.vertexPath)
        // add remaining edge weight to be travelled.
        val weights: MutableList<Int> = mutableListOf()
        weights.add(edge.getWeight() - currentDistanceOnEdge)
        weights.addAll(pathFromTarget.edgeWeights)
        // add remaining edge one way status.
        val isOneWay: MutableList<Boolean> = mutableListOf()
        isOneWay.add(edge.isOneWay())
        isOneWay.addAll(pathFromTarget.isOneWay)
        val fullPathFromTarget = Path(path, weights, isOneWay, getRoundedTicks(weights.sum()))

//        println("pathFromSource" + pathFromSource.vertexPath)
        if (!edge.isOneWay()) {
            val pathFromSource =
                getShortestPathFromVertexToVertex(
                    graph,
                    sourceVertex,
                    destinationVertex,
                    height
                )
            val newPath: MutableList<Int> = mutableListOf()
            newPath.add(targetVertex)
            newPath.addAll(pathFromSource.vertexPath)
            // add remaining edge weight to be travelled.
            val newWeights: MutableList<Int> = mutableListOf()
            newWeights.add(currentDistanceOnEdge)
            newWeights.addAll(pathFromSource.edgeWeights)
            // add remaining edge one way status.
            val newIsOneWay: MutableList<Boolean> = mutableListOf()
            newIsOneWay.add(edge.isOneWay())
            newIsOneWay.addAll(pathFromSource.isOneWay)
            val fullPathFromSource = Path(
                newPath,
                newWeights,
                newIsOneWay,
                getRoundedTicks(newWeights.sum())
            )

            if (fullPathFromSource.getTotalDistance() <
                fullPathFromTarget.getTotalDistance()
            ) {
                return fullPathFromSource
            } else if (fullPathFromTarget.getTotalDistance() == fullPathFromSource.getTotalDistance()) {
                return getLexSmallerPath(fullPathFromTarget, fullPathFromSource)
            }
        }
        return fullPathFromTarget
    }

    /**
     * finds the all the nearest bases to the given vertex.
     * @param vertexId the vertex to which bases need to find the shortest path to.
     * @return map of all bases and their distances to the given vertex.
     * */
    private fun findNearestBases(
        graph: Graph,
        vertexId: Int,
        baseType: BaseType,
        excludedBases: Set<Int> = emptySet()
    ): HashMap<Int, Int>? {
        val nearestBases = HashMap<Int, Int>()
        val distanceFromSourceVertex =
            dijkstraAlgorithm(graph, vertexId, true, parentMap = HashMap())
        val filterVertices = distanceFromSourceVertex.keys.filter {
            !excludedBases.contains(it) && graph.vertices[it]?.baseType == baseType
        }
        if (filterVertices.isEmpty()) {
            return null
        }
        for (vertex in filterVertices) {
            nearestBases[vertex] = distanceFromSourceVertex.getValue(vertex)
        }
        return nearestBases
    }

    /**
     * main dijkstra algorithm that calculates from a vertex to all other vertices
     * and keeps track of the parent vertices.
     * @param graph the graph to be traversed.
     * @param source the starting node.
     * @param reverse if true, the graph is traversed in reverse.
     * @param height restricts the edges to be traversed to those with height greater than or equal to the given height.
     * @param parentMap the map that keeps track of the parent vertices.
     * @return map from vertex id to the distance from source to all other vertices .
     * */
    private fun dijkstraAlgorithm(
        graph: Graph,
        source: Int,
        reverse: Boolean,
        height: Int? = null,
        parentMap: HashMap<Int, Int>
    ): HashMap<Int, Int> {
        val visited = HashMap<Int, Boolean>()
        val distancesFromSource = HashMap<Int, Int>()

        // initialize the distances and visited maps.
        for (vertex in graph.vertices.values.toMutableList()) {
            visited[vertex.vertexId] = false
            distancesFromSource[vertex.vertexId] = Int.MAX_VALUE
        }
        distancesFromSource[source] = 0
//        println("start" + distancesFromSource)

        while (visited.containsValue(false)) {
            // find the vertex with the minimum distance from the source vertex.
            val currentVertex =
                getMinDistance(graph.vertices.values.toMutableList(), distancesFromSource, visited)
            visited[currentVertex.vertexId] = true

            // relax all edges of the vertex.
            updateParent(currentVertex, height, distancesFromSource, parentMap, reverse)
//            println(visited)
//            println(distancesFromSource)
        }
        return distancesFromSource
    }

    /**
     * finds the vertex with the minimum distance from the source vertex.
     * @param vertices the vertices to be traversed.
     * @param distances the distances from the source vertex to all other vertices.
     * @param visited the vertices that have been visited.
     * @return the vertex with the minimum distance.
     * */
    private fun getMinDistance(
        vertices: MutableCollection<Vertex>,
        distances: HashMap<Int, Int>,
        visited: HashMap<Int, Boolean>
    ): Vertex {
        return vertices.filter { !visited.getValue(it.vertexId) }
            .minBy { distances.getValue(it.vertexId) }
    }

    /**
     * relaxes the edge and updates the parenMap.
     * @param vertex the vertex to be updated.
     * @param distancesFromSource the distances from the source vertex to all other vertices.
     * @param parentMap the map that keeps track of the parent vertices.
     * @param reverse if true, the graph is traversed in reverse.
     * */
    private fun updateParent(
        vertex: Vertex,
        height: Int?,
        distancesFromSource: HashMap<Int, Int>,
        parentMap: HashMap<Int, Int>,
        reverse: Boolean
    ) {
        val edges = vertex.getEdges(reverse)
        for (edge in edges) {
            if (height != null && edge.properties.height < height) {
                continue
            }
            var newDistance = distancesFromSource.getValue(vertex.vertexId)
            if (newDistance != Int.MAX_VALUE) {
                newDistance += edge.getWeight()
            }
            // vertex.getEdges(reverse) can guarantee this direction of edge exit
            val newVertexId =
                if (vertex.vertexId != edge.sourceVertex.vertexId) {
                    edge.sourceVertex.vertexId
                } else {
                    edge.targetVertex.vertexId
                }

            updateDistanceAndParent(distancesFromSource, newVertexId, newDistance, vertex, parentMap)
        }
    }

    private fun updateDistanceAndParent(
        distancesFromSource: HashMap<Int, Int>,
        newVertexId: Int,
        newDistance: Int,
        vertex: Vertex,
        parentMap: HashMap<Int, Int>
    ) {
        val oldDistance = distancesFromSource.getValue(newVertexId)
        if (newDistance <= oldDistance) {
            distancesFromSource[newVertexId] = newDistance
            if (newDistance == oldDistance) {
                if (vertex.vertexId < parentMap.getValue(newVertexId)) {
                    parentMap[newVertexId] = vertex.vertexId
                }
            }
            parentMap[newVertexId] = vertex.vertexId
        }
    }

    private fun reconstructPath(
        source: Int,
        target: Int,
        parentMap: HashMap<Int, Int>
    ): List<Int> {
        val path: MutableList<Int> = mutableListOf()
        var currentVertex = source
        while (currentVertex != target) {
            path.add(currentVertex)
            currentVertex = parentMap.getValue(currentVertex)
        }
        path.add(target)
        return path
    }

    private fun getRoundedTicks(distance: Int): Int {
        val ticks = roundToNextTen(distance) / speed
        return if (ticks == 0) {
            THE_NUMBER_ONE
        } else {
            ticks
        }
    }

    private fun roundToNextTen(number: Int): Int {
        val ticks = (number + num9) / speed * speed
        return if (ticks == 0) {
            THE_NUMBER_ONE
        } else {
            ticks
        }
    }
}
