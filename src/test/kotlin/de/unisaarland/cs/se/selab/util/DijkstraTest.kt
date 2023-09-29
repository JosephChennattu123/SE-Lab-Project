package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.config.DotParser
import de.unisaarland.cs.se.selab.config.GraphValidator
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Location
import de.unisaarland.cs.se.selab.model.map.Graph
import org.junit.jupiter.api.Test

internal class DijkstraTest {
    // TODO test for DotParser and GraphValidator, just assume they are correct here
    private val graph: Graph = GraphValidator().validate(DotParser("mapFiles/example_map.dot"))
        ?: throw IllegalArgumentException("Graph creation go wrong!")

    @Test
    fun getNearestBaseToEdge() {
        val location = Location("Saarbruecken", "Eisenbahnstrasse")
        assert(Dijkstra.getNearestBaseToEdge(graph, location, BaseType.FIRE_STATION) == 2)
    }

    @Test
    fun getNextNearestBase() {
    }

    @Test
    fun getShortestPathFromEdgeToEdge() {
    }

    @Test
    fun getShortestPathFromVertexToEdge() {
    }

    @Test
    fun getShortestPathFromEdgeToVertex() {
    }
}
