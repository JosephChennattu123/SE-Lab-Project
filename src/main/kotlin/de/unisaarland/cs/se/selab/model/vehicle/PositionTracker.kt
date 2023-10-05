package de.unisaarland.cs.se.selab.model.vehicle

/**
 * @property path
 * @property currentVertexIndex
 *
 * */
class PositionTracker {
    var path: Path? = null
    var currentVertexIndex: Int? = null
    var positionOnEdge: Int? = null

    /** updates Position: updates the value of current Vertex index and position on Edge*/
    fun updatePosition() {
        var distance = SPEED
        while (!destinationReached() && distance != 0) {
            val edgeWeights = (path as Path).edgeWeights
            distance = edgeWeights[currentVertexIndex as Int].minus(positionOnEdge as Int + distance)
            if (distance <= 0) {
                positionOnEdge = 0
                currentVertexIndex = currentVertexIndex?.plus(1)
                distance *= -1
            } else {
                positionOnEdge = edgeWeights[currentVertexIndex as Int] - distance
                distance = 0
            }
        }
    }

    /** @returns true if path has to be changed and false if not. If path has to be changed,(.ie starting point
     * has change) then currentVertexIndex
     * is reset to 0 and position on edge is updated   */
    fun assignPath(newPath: Path): Boolean {
        if (path == null) {
            path = newPath
            currentVertexIndex = 0
            positionOnEdge = 0
            return true
        }
        return if (newPath.vertexPath == this.path?.vertexPath && newPath.edgeWeights == this.path?.edgeWeights &&
            newPath.totalTicksToArrive == this.path?.totalTicksToArrive
        ) {
            false
        } else {
            val oldSumOfWeights = path?.edgeWeights?.sum()
            val newSumOfWeights = newPath.edgeWeights.sum()
            val currentPath = path?.vertexPath ?: error("path is null")
            path = newPath
            currentVertexIndex = 0
            positionOnEdge = 0
            return oldSumOfWeights != newSumOfWeights || !areEdgesTheSame(currentPath, newPath.vertexPath)
        }
    }

    /** returns current Vertex of vehicle */
    fun getCurrentVertex(): Int? {
        return path?.vertexPath?.get(currentVertexIndex as Int)
    }

    /** returns the id of the next vertex that will be reached */
    fun getNextVertex(): Int? {
        return path?.vertexPath?.get(currentVertexIndex as Int + 1)
    }

    /** @returns true if destination vertex is reached */
    fun destinationReached(): Boolean {
        return path?.vertexPath?.last() == path?.vertexPath?.get(currentVertexIndex as Int)
    }

    /** @returns id of destination vertex*/
    fun getDestination(): Int? {
        return path?.vertexPath?.last()
    }

    /**
     * checks if two one path belongs to the same set of edges.
     * */
    private fun areEdgesTheSame(path1: List<Int>, path2: List<Int>): Boolean {
        // if sizes are not equal there must be an edge which is different.
        if (path1.size != path2.size) {
            return false
        }
        for (i in path1.indices) {
            if (path1[i] != path2[i]) {
                return false
            }
        }
        return true
    }
}

const val SPEED = 10
