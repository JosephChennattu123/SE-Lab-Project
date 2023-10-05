package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.RoadProperties
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.model.map.Vertex

class
SimpleGraphHelper {
    val simpleVertices: MutableMap<Int, Vertex> = creatSimpleVertices()
    val simpleGraph: Graph = creatSimpleGraph()
    val verticesForWithHeight: MutableMap<Int, Vertex> = creatSimpleVertices()
    val simpleGraphWithHeight: Graph = creatSimpleGraphWithHeight()

    private fun creatSimpleVertices(): MutableMap<Int, Vertex> {
        val vertices: MutableMap<Int, Vertex> = mutableMapOf()
        vertices[0] = Vertex(0, 0, BaseType.FIRE_STATION)
        vertices[1] = Vertex(1, 1, BaseType.FIRE_STATION)
        vertices[2] = Vertex(2, 2, BaseType.POLICE_STATION)
        vertices[3] = Vertex(3, null, null)
        return vertices
    }

    private fun creatSimpleGraph(): Graph {
        val g = Graph(simpleVertices)

        // creat edges
        g.addEdge(
            simpleVertices[0]!!,
            simpleVertices[2]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r0", 60, 30)
        )
        g.addEdge(
            simpleVertices[1]!!,
            simpleVertices[3]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r1", 10, 30)
        )
        g.addEdge(
            simpleVertices[3]!!,
            simpleVertices[2]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r2", 10, 30)
        )
        g.addEdge(
            simpleVertices[0]!!,
            simpleVertices[1]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r3", 100, 30)
        )
        return g
    }

    private fun creatSimpleGraphWithHeight(): Graph {
        // creat vertices
        verticesForWithHeight[4] = Vertex(4, 4, BaseType.HOSPITAL)
        val g = Graph(verticesForWithHeight)

        // creat edges
        g.addEdge(
            verticesForWithHeight[0]!!,
            verticesForWithHeight[2]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r0", 60, 60)
        )
        g.addEdge(
            verticesForWithHeight[1]!!,
            verticesForWithHeight[3]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r1", 10, 30)
        )
        g.addEdge(
            verticesForWithHeight[3]!!,
            verticesForWithHeight[2]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r2", 10, 20)
        )
        g.addEdge(
            verticesForWithHeight[0]!!,
            verticesForWithHeight[1]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r3", 100, 40)
        )
        g.addEdge(
            verticesForWithHeight[0]!!,
            verticesForWithHeight[3]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.TUNNEL, "v0", "r4", 100, 40)
        )
        g.addEdge(
            verticesForWithHeight[4]!!,
            verticesForWithHeight[3]!!,
            RoadProperties(PrimaryType.COUNTY, SecondaryType.TUNNEL, "v0", "r5", 20, 10)
        )

        return g
    }
}
