package de.unisaarland.cs.se.selab.util

import de.unisaarland.cs.se.selab.creatSimpleGraph
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
    private val simpleGraph: Graph = creatSimpleGraph()

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
        val r1 =
            Dijkstra.getShortestPathFromEdgeToEdge(simpleGraph, 1, 3, 5, Location("v0", "r0"), 10)
        val vertexPath = listOf(1, 3, 2)
        assertEquals("Expected $vertexPath, but got ${r1.vertexPath}", vertexPath, r1.vertexPath)
        val edgeWeights = listOf(5, 10)
        assertEquals(
            "Expected $edgeWeights, but got ${r1.edgeWeights}",
            edgeWeights,
            r1.edgeWeights
        )
        val isOneWay = listOf(true, true)
        assertEquals("Expected $isOneWay, but got ${r1.isOneWay}", isOneWay, r1.isOneWay)
        val totalTicksToArrive = 2
        assertEquals(
            "Expected $totalTicksToArrive, but got ${r1.totalTicksToArrive}",
            totalTicksToArrive,
            r1.totalTicksToArrive
        )


// todo() special scenario that the next vertex to go is one of the (lastVisitedVertex or nextVisitedVertex)
//        val r2 =
//            Dijkstra.getShortestPathFromEdgeToEdge(simpleGraph, 2, 0, 50, Location("v0", "r2"), 10)
//        val vertexPath2 = listOf(0, 2)
//        assertEquals("Expected $vertexPath2, but got ${r2.vertexPath}", vertexPath2, r2.vertexPath)
//        val edgeWeights2 = listOf(50)
//        assertEquals(
//            "Expected $edgeWeights2, but got ${r2.edgeWeights}",
//            edgeWeights2,
//            r2.edgeWeights
//        )
//        val isOneWay2 = listOf(false)
//        assertEquals("Expected $isOneWay2, but got ${r2.isOneWay}", isOneWay2, r2.isOneWay)
//        val totalTicksToArrive2 = 5
//        assertEquals(
//            "Expected $totalTicksToArrive2, but got ${r2.totalTicksToArrive}",
//            totalTicksToArrive2,
//            r2.totalTicksToArrive
//        )


    }

//    @Test
//    fun getShortestPathFromVertexToEdge() {
//    }
//
//    @Test
//    fun getShortestPathFromEdgeToVertex() {
//    }

}
