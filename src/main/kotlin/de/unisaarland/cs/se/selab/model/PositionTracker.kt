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

    /** updates Position: updates the value of current Vertex index and position on Edge*/
    fun updatePosition() {
        var distance = SPEED
        while (!destinationReached() && distance != 0) {
            distance = path.edgeWeights[currentVertexIndex] - (positionOnEdge + distance)
            if (distance < 0) {
                positionOnEdge = 0
                currentVertexIndex++
                distance = distance * -1
            } else {
                positionOnEdge = path.edgeWeights[currentVertexIndex] - distance
                distance = 0
            }
        }
    }

    /** @returns true if path has to be changed and false if not. If path has to be changed,(.ie starting point
     * has change) then currentVertexIndex
     * is reset to 0 and position on edge is updated  * */

    fun assignPath(path: Path): Boolean {
        return if (path.vertexPath == this.path.vertexPath && path.edgeWeights == this.path.edgeWeights &&
            path.totalTicksToArrive == this.path.totalTicksToArrive
        ) {
            false
        } else {
            if (path.vertexPath[0] == this.path.vertexPath[currentVertexIndex]) {
                this.path = path
                true
            } else {
                positionOnEdge = this.path.edgeWeights[currentVertexIndex] - positionOnEdge
                currentVertexIndex = 0
                this.path = path
                true
            }
        }
    }

    /** returns current Vertex of vehicle */
    fun getCurrentVertex(): Int {
        return path.vertexPath[currentVertexIndex]
    }

    /** returns the id of the next vertex that will be reached */
    fun getNextVertex(): Int {
        return path.vertexPath[currentVertexIndex + 1]
    }

    /** @returns true if destination vertex is reached */
    fun destinationReached(): Boolean {
        if (path.vertexPath.last() == path.vertexPath[currentVertexIndex]) {
            return true
        }
        return false
    }

    /** @returns id of destination vertex*/
    fun getDestination(): Int {
        return path.vertexPath.last()
    }
}
const val SPEED = 10
