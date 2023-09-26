package de.unisaarland.cs.se.selab.model

/**
 * @property path
 * @property currentVertexIndex
 *
 * */
class PositionTracker {
    lateinit var path: Path
    var currentVertexIndex: Int = 0
    var positionOnEdge: Int = 0
    private val speed = 10

    fun updatePosition(): Unit {

    }

    fun assignPath(path: Path): Boolean {
        //TODO
        return true
    }

    fun getNextVertex(): Int {
        //TODO
        return -1
    }

    fun destinationReached(): Boolean {
        //TODO
        return true
    }

    fun getDestination(): Int {
        TODO()
    }
}
