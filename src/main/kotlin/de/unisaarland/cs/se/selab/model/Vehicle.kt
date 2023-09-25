package de.unisaarland.cs.se.selab.model

open class Vehicle {
    fun driveUpdate(): Unit {
        //TODO
    }

    open fun handleEmergency(amount: Int): Int {
        //TODO
        return 0
    }

    fun setNewPath(): Boolean {
        //TODO
        return false
    }

    fun setAtBase(): Boolean {
        //TODO
        return false
    }

    fun decreaseBusyTicks() {
        //TODO
    }

    fun getCurrentVertexID(): Int {
        //TODO
        return 0
    }

    fun getNextVertexID(): Int? {
        //TODO
        return null
    }

    fun getDistanceOnEdge(): Int {
        //TODO
        return 0
    }
}