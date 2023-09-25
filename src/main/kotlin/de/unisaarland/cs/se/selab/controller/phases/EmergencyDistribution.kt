package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Model

class EmergencyDistribution {

    public fun execute(m: Model) {
        getEmergenciesFor(0)
        findNearestBase()
        distributeEmergencies()
        getSeverity()

    }

    //List<Emergency>
    private fun getEmergenciesFor(tick: Int): List<Emergency> {

    }

    private fun findNearestBase(e: Emergency): Base {

    }

    private fun distributeEmergencies(l: List<Emergency>) {

    }

    private fun getSeverity(emergency: Emergency) {

    }


}