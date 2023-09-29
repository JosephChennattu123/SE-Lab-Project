package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.config.DotParser
import de.unisaarland.cs.se.selab.config.GraphValidator
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Location
import de.unisaarland.cs.se.selab.model.map.Graph
import org.junit.jupiter.api.Test

internal class DijkstraTest {
    private val graph: Graph = GraphValidator().validate(DotParser("src/test/resources/mapFiles/example_map.dot"))
        ?: throw IllegalArgumentException("Graph creation go wrong!")
//    private val graph: Graph = Graph(mutableMapOf())
//
//    private fun creatGraph(): Graph {
//        val vertices: MutableMap<Int, Vertex> = mutableMapOf()
//        for (vertexId in setOf(0, 1, 2, 3, 4)) {
//            val vertex = Vertex(vertexId, null, null)
//            vertices[vertexId] = vertex
//        }
//        val graph = Graph(vertices)
//    }

    @Test
    fun getNearestBaseToEdge() {
        val location = Location("Saarbruecken", "Eisenbahnstrasse")
        println(graph.getEdge(2, 3).edgeId)
        println(Dijkstra.getNearestBaseToEdge(graph, location, BaseType.FIRE_STATION))
//        assert( == 2)
    }

//    @Test
//    fun getNextNearestBase() {
//    }
//
//    @Test
//    fun getShortestPathFromEdgeToEdge() {
//    }
//
//    @Test
//    fun getShortestPathFromVertexToEdge() {
//    }
//
//    @Test
//    fun getShortestPathFromEdgeToVertex() {
//    }
}
