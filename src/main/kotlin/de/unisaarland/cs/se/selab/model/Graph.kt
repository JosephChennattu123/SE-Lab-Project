package de.unisaarland.cs.se.selab.model

class Graph {
    fun getEdges(): List<Edge> {
        //TODO
    }

    fun getEdge(source: Int, target: Int): Edge {
        //TODO
    }

    fun getEdge(location: Location): Edge {
        //TODO
    }

    fun addEdge(
        source: Vertex,
        target: Vertex,
        properties: RoadProperties,
        villageName: String,
        roadName: String
    ): unit {
        //TODO
    }

    fun getEdge(edgeID: int): Edge {
        //TODO
    }
}