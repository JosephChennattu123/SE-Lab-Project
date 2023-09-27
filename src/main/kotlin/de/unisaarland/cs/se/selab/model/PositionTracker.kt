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
        var distance = path.edgeWeights[currentVertexIndex] - (positionOnEdge + SPEED)
        if (distance > 0) {
            positionOnEdge + SPEED
        } else {
            currentVertexIndex++
            positionOnEdge = distance * -1
        }
    }

    /** @returns true if path has to be changed and false if not. If path has to be changed,(.ie starting point
     * has change) then currentVertexIndex
     * is reset to 0 and position on edge is updated  * */

    fun assignPath(path: Path): Boolean {
        if (path.vertexPath == this.path.vertexPath && path.edgeWeights == this.path.edgeWeights &&
            path.totalTicksToArrive == this.path.totalTicksToArrive
        ) {
            return false
        } else {
            if (path.vertexPath[0] == this.path.vertexPath[currentVertexIndex]) {
                this.path = path
                return true
            } else {
                positionOnEdge = this.path.edgeWeights[currentVertexIndex] - positionOnEdge
                currentVertexIndex = 0
                this.path = path
                return true
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
