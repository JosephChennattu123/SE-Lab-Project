package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.model.BaseType

/**
 * represents a vertex in the graph.
 * */
class Vertex(val vertexId: Int, val baseId: Int?, val baseType: BaseType?) {

    private val outgoingEdges: List<Edge> = mutableListOf()
    private val ingoingEdges: List<Edge> = mutableListOf()

    /**
     * adds an outgoing edge to the vertex.
     * */
    fun addEdgeOutgoingEdge(edge: Edge) {
        outgoingEdges + edge
    }

    /**
     * adds an ingoing edge to the vertex.
     * */
    fun addEdgeIngoingEdge(edge: Edge) {
        ingoingEdges + edge
    }

    /**
     * retrieve edges of the vertex.
     * @param reverse if true returns ingoing edges, else returns outgoing edges.
     * @return list of outgoing or ingoing edges.
     * */
    fun getEdges(reverse: Boolean): List<Edge> {
        if (reverse) {
            return ingoingEdges
        }
        return outgoingEdges
    }
}
