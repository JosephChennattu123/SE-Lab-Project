package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Location
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.RoadProperties
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.model.map.Vertex
import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.assertEquals

internal class DijkstraTest {
    //    private val graph: Graph = GraphValidator().validate(DotParser("src/test/resources/mapFiles/example_map.dot"))
//        ?: throw IllegalArgumentException("Graph creation go wrong!")
    private val vertices: MutableMap<Int, Vertex> = mutableMapOf()
    private val simpleGraph: Graph = creatSimpleGraph()

    private fun creatSimpleGraph(): Graph {

        // creat vertices
        vertices[0] = Vertex(0, 0, BaseType.FIRE_STATION)
        vertices[1] = Vertex(1, 1, BaseType.FIRE_STATION)
        vertices[2] = Vertex(2, 2, BaseType.POLICE_STATION)
        vertices[3] = Vertex(3, null, null)
        val g = Graph(vertices)

        // creat edges
        g.addEdge(
            vertices[0]!!,
            vertices[2]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r0", 60, 30)
        )
        g.addEdge(
            vertices[1]!!,
            vertices[3]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r1", 10, 30)
        )
        g.addEdge(
            vertices[3]!!,
            vertices[2]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r2", 10, 30)
        )
        return g
    }

    @Test
    fun getNearestBaseToEdge() {
        // test for each BaseType

        val result1 =
            Dijkstra.getNearestBaseToEdge(
                simpleGraph,
                Location("v0", "r2"),
                BaseType.FIRE_STATION
            )
        assertEquals("Expected 1, but got $result1", 1, result1)

        val result2 =
            Dijkstra.getNearestBaseToEdge(
                simpleGraph,
                Location("v0", "r1"),
                BaseType.POLICE_STATION
            )
        assertEquals("Expected 2, but got $result2", 2, result2)

        val result3 =
            Dijkstra.getNearestBaseToEdge(
                simpleGraph,
                Location("v0", "r0"),
                BaseType.HOSPITAL
            )
        assertEquals("Expected null, but got $result3", null, result3)

        val result4 =
            Dijkstra.getNearestBaseToEdge(
                simpleGraph,
                Location("v0", "r0"),
                BaseType.FIRE_STATION
            )
        assertEquals("Expected 0, but got $result4", 0, result4)

    }

    @Test
    fun getNextNearestBase() {
        val r1 = Dijkstra.getNextNearestBase(simpleGraph, 0, BaseType.FIRE_STATION, setOf(0))
        assertEquals("Expected 1, but got $r1", 1, r1)

        val r2 = Dijkstra.getNextNearestBase(simpleGraph, 2, BaseType.POLICE_STATION, setOf(2))
        assertEquals("Expected null, but got $r2", null, r2)

        val r3 = Dijkstra.getNextNearestBase(simpleGraph, 0, BaseType.FIRE_STATION, setOf(0, 1))
        assertEquals("Expected null, but got $r3", null, r3)
    }

    @Test
    fun getShortestPathFromEdgeToEdge() {
        val r1 = Dijkstra.getShortestPathFromEdgeToEdge(simpleGraph, 3, 2, , Location("v0", "r0"), 10  )
    }

    @Test
    fun getShortestPathFromVertexToEdge() {
    }

    @Test
    fun getShortestPathFromEdgeToVertex() {
    }
}
