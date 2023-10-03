package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.SimpleGraphHelper
import de.unisaarland.cs.se.selab.model.Location
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GraphTest {
    private val helper: SimpleGraphHelper = SimpleGraphHelper()
    private val simpleGraph: Graph = helper.simpleGraph
    private val simpleVertices: MutableMap<Int, Vertex> = helper.simpleVertices

    @Test
    fun getEdges() {
        assert(simpleGraph.getEdges().size == 4)
    }

    @Test
    fun getEdge() {
        assert(simpleGraph.getEdge(0, 1).getWeight() == 100)
        assert(simpleGraph.getEdge(0, 2).getWeight() == 60)
        assert(simpleGraph.getEdge(2, 0).getWeight() == 60)

    }

    @Test
    fun testGetEdge() {
        assert(simpleGraph.getEdge(Location("v0", "r0")).getWeight() == 60)
        assert(simpleGraph.getEdge(Location("v0", "r3")).getWeight() == 100)
    }

    @Test
    fun doesLocationExist() {
        assert(simpleGraph.doesLocationExist(Location("v0", "r0")))
        assert(simpleGraph.doesLocationExist(Location("v0", "r2")))
        assert(!simpleGraph.doesLocationExist(Location("v1", "r0")))
        assert(!simpleGraph.doesLocationExist(Location("v0", "r9")))
        assert(!simpleGraph.doesLocationExist(Location("dasazdgf", "y5t4e")))
    }

    @Test
    fun doesEdgeExist() {
        assert(simpleGraph.doesEdgeExist(0, 1))
        assert(simpleGraph.doesEdgeExist(2, 0))
        assert(!simpleGraph.doesEdgeExist(1, 0))
        assert(!simpleGraph.doesEdgeExist(10, 0))
        assert(simpleGraph.doesEdgeExist(0, 2))
    }

    @Test
    fun addEdge() {
        simpleGraph.addEdge(
            simpleVertices[0]!!,
            simpleVertices[3]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r4", 30, 20)
        )
        assert(simpleGraph.getEdges().size == 5)
        assert(simpleGraph.doesEdgeExist(3, 0))
        assert(simpleGraph.doesLocationExist(Location("v0", "r4")))
        assert(simpleGraph.getEdge(Location("v0", "r4")).getWeight() == 30)

    }

}