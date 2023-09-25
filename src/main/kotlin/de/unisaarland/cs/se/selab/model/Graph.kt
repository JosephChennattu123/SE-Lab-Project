package de.unisaarland.cs.se.selab.model

class Graph {
    fun getEdges(): List<Edge> {
        //TODO
        return listOf()
    }

    fun getEdge(source: Int, target: Int): Edge {
        //TODO
        return Edge()
    }

    fun getEdge(location: Location): Edge {
        //TODO
        return Edge()
    }

    fun addEdge(
        source: Vertex, target: Vertex, properties: RoadProperties,
        villageName: String, roadName: String
    ) {
        //TODO
    }

    fun getEdge(edgeID: Int): Edge {
        //TODO
        return Edge()
    }
}
