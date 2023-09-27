package de.unisaarland.cs.se.selab.model

/**
 * class for representing a path a vehicle has to travel to its destination.
 * */
class Path(
    val vertexPath: List<Int>,
    val edgeWeights: List<Int>,
    val isOneWay: List<Boolean>,
    val totalTicksToArrive: Int
){
    /**
     * calculates the total weight of the entire path.
     * */
    fun getTotalDistance(): Int{
        return edgeWeights.sum()
    }
}
