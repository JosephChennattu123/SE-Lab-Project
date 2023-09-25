package de.unisaarland.cs.se.selab.model

class FireTruck : Vehicle() {
    fun setBusy(): boolean {
        //todo
        return true
    }

    override fun handleEmergency(amount: Int): Int {
        //todo
        return -1
    }

    fun refill(): Unit {
        //todo

    }

    fun isFull(): Boolean {
        //todo
        return true
    }
}