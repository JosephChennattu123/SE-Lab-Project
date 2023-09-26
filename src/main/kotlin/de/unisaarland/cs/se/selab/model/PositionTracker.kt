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
/** */
    fun updatePosition(): Unit {
        var distance = path.edgeWeights[currentVertexIndex]-(positionOnEdge+10)
        positionOnEdge = if(distance>0){
            positionOnEdge+10
        } else{
            currentVertexIndex++
            distance*-1
        }
    }

    fun assignPath(path: Path): Boolean {
        if(path.vertexPath == this.path.vertexPath && path.edgeWeights== this.path.edgeWeights && path.totalTicksToArrive == this.path.totalTicksToArrive)
        return false
        else{
            if(path.vertexPath[0]==this.path.vertexPath[currentVertexIndex])
            {
             this.path = path
             return true
            }
            else{
             positionOnEdge = this.path.edgeWeights[currentVertexIndex]-positionOnEdge
             currentVertexIndex = 0
             this.path = path
             return true
            }
        }
    }

    fun getNextVertex(): Int {
        return path.vertexPath[currentVertexIndex+1]
    }

    fun destinationReached(): Boolean {
        if (path.vertexPath.last()==path.vertexPath[currentVertexIndex])
        return true
        return false
    }

    fun getDestination(): Int {
        return path.vertexPath.last()

    }
}
