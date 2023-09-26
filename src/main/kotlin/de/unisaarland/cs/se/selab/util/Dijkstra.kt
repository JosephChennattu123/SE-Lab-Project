package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Location
import de.unisaarland.cs.se.selab.model.Path
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.Vertex

/**
 * Util class to calculate the shortest path on a graph using Dijkstra's algorithm.
 * */
object Dijkstra {
    /**
     * calculates the shortest distance weights from sourceVertex to targetVertex.
     * @return the distance in weight units.
     * */
    fun getShortestDistance(
        graph: Graph, sourceVertex: Int, targetVertex: Int,
        height: Int
    ): Int {
        TODO()
    }

    /**
     * Finds the nearest base to the given edge given as location.
     * @param location the location of the edge.
     * @param baseType the type of the required base.
     * @return the vertex id of the nearest base.
     * */
    fun getNearestBaseToEdge(graph: Graph, location: Location, baseType: BaseType): Int {
        TODO()
    }

    /**
     * Finds the nearest base relative to the Main base which has not been visited yed.
     * @param sourceVertex the vertex id of the vertex.
     * @param baseType the type of the required base.
     * @return the vertex id of the nearest base.
     * */
    fun getNextNearestBase(
        graph: Graph, sourceVertex: Int, baseType: BaseType,
        requestedBases: Set<Int>
    ): Int? {
        TODO()
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
        graph: Graph, lastVisitedVertex: Int,
        nextVertexToVisit: Int,
        distanceFromSourceVertex: Int,
        targetEdgeLocation: Location,
        height: Int
    ): Path {
        TODO()
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
        graph: Graph, sourceVertex: Int, targetVertex: Int,
        height: Int
    ): Path {
        TODO()
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
        graph: Graph, sourceVertex: Int, location: Location,
        height: Int
    ): Path {
        TODO()
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
        graph: Graph, sourceVertex: Int, targetVertex: Int, destinationVertex: Int,
        height: Int
    ): Path {
        TODO()
    }

    /**
     * finds the nearest base between all found bases of one type.
     * @param location the location of the edge.
     * @param targetVertex the vertex of an emergency or base.
     * @param height the height of the vehicle.
     * @return Path object containing the shortest path.
     * */
    private fun findNearestBases(
        graph: Graph, vertexId: Int,
        baseType: BaseType
    ): HashMap<Int, Int> {
        TODO()
    }

    /**
     * main dijkstra algorithm that calculates from a vertex to all other vertices
     * and keeps track of the parent vertices.
     * @param graph the graph to be traversed.
     * @param source the stating node.
     * @param reverse if true, the graph is traversed in reverse.
     * @param parentMap the map that keeps track of the parent vertices.
     * @return map from vertex id to the distance from source to all other vertices .
     * */
    private fun dijkstra(
        graph: Graph, source: Int, reverse: Boolean, parentMap: HashMap<Int, Int>
    ): HashMap<Int, Int> {
        TODO()
    }

    /**
     * finds the vertex with the minimum distance from the source vertex.
     * @param vertices the vertices to be traversed.
     * @param distances the distances from the source vertex to all other vertices.
     * @param visited the vertices that have been visited.
     * @return the vertex with the minimum distance.
     * */
    private fun getMinDistance(
        vertices: HashMap<Int, Vertex>, distances: HashMap<Int, Int>,
        visited: HashMap<Int, Boolean>
    ): Vertex {
        TODO()
    }

    /**
     * relaxes the edge and updates the parenMap.
     * @param vertex the vertex to be updated.
     * @param distancesFromSource the distances from the source vertex to all other vertices.
     * @param parentMap the map that keeps track of the parent vertices.
     * @param reverse if true, the graph is traversed in reverse.
     * */
    private fun updateParent(
        vertex: Vertex, distancesFromSource: HashMap<Int, Int>,
        parentMap: HashMap<Int, Int>, reverse: Boolean
    ): Unit {
        TODO()
    }
}