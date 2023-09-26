package de.unisaarland.cs.se.selab.model

class Ambulance : Vehicle() {
    fun setBusy(): Boolean {
        //todo
        return true
    }

    override fun handleEmergency(amount: Int): Int {
        //todo
        return -1
    }


    fun isFull(): Boolean {
        //todo
        return true
    }
}
