package de.unisaarland.cs.se.selab.model

/**
 * Class for representing a path a vehicle has to travel to its destination.
 *
 * @param vertexPath the vertices of the path
 * @param edgeWeights the weight of the edges of the path
 * @param isOneWay marks the direction of the
 * @param totalTicksToArrive the number of ticks it takes to travel the path
 */
class Path(
    val vertexPath: List<Int>,
    val edgeWeights: List<Int>,
    val isOneWay: List<Boolean>,
    val totalTicksToArrive: Int
) {
    /**
     * calculates the total weight of the entire path.
     * */
    fun getTotalDistance(): Int {
        return edgeWeights.sum()
    }
}
