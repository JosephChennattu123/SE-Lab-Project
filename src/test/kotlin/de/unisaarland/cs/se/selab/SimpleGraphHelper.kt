package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.RoadProperties
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.model.map.Vertex


 fun creatSimpleGraph(): Graph {
        val vertices: MutableMap<Int, Vertex> = mutableMapOf()
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

//fun creat