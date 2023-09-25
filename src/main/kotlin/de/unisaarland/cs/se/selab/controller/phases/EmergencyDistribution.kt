package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model

class EmergencyDistribution {

    fun execute(m: Model) {
        getEmergenciesFor(0)
        findNearestBase()
        distributeEmergencies()
        getSeverity()

    }

    //List<Emergency>
    private fun getEmergenciesFor(tick: Int): List<Emergency> {
        return listOf()
    }

    private fun findNearestBase(e: Emergency): Base {
        return Base()
    }

    private fun distributeEmergencies(l: List<Emergency>) {

    }

    private fun getSeverity(emergency: Emergency) {

    }


}