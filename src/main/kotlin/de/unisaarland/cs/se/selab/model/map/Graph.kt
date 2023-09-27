package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.model.Location

class Graph {

    val vertices: MutableMap<Int, Vertex> = mutableMapOf()
    val villages: MutableMap<String, MutableMap<String, Edge>> = mutableMapOf()
    fun getEdges(): List<Edge> {
        TODO()
    }

    fun getEdge(source: Int, target: Int): Edge {
        TODO()
    }

    fun getEdge(location: Location): Edge {
        TODO()
    }

    fun addEdge(
        source: Vertex,
        target: Vertex,
        properties: RoadProperties,
        villageName: String,
        roadName: String
    ) {
        TODO()
    }

    fun getEdge(edgeID: Int): Edge {
        TODO()
    }
}
