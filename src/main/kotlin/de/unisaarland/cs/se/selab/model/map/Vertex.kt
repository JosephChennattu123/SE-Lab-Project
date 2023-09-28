package de.unisaarland.cs.se.selab.model.map

import de.unisaarland.cs.se.selab.model.BaseType

/**
 * Represents a vertex in the graph
 * @param vertexId the id of the vertex
 * @param baseId the id of the base at the that vertex
 * @param baseType the type of the base
 */
class Vertex(val vertexId: Int, val baseId: Int?, val baseType: BaseType?) {
    private val outgoingEdges: List<Edge> = mutableListOf()
    private val ingoingEdges: List<Edge> = mutableListOf()

    /**
     * adds an outgoing edge to the vertex.
     * @param edge the edge to add
     * */
    fun addEdgeOutgoingEdge(edge: Edge) {
        outgoingEdges + edge
    }

    /**
     * adds an ingoing edge to the vertex.
     * @param edge the edge to add
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
