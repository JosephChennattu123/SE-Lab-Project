package de.unisaarland.cs.se.selab.controller.phases

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.Model

class EmergencyDistribution {

    public fun execute(m:Model) {
        getEmergenciesFor(0)
        findNearestBase()
        distributeEmergencies()
        getSeverity()

    }

    //List<Emergency>
    /**
     * @param tick : gets current tick
     * @return list of emergencies at the current tick
     * Get the emergecies that are happening at the current tick
     */
    private fun getEmergenciesFor(tick:Int) : List<Emergency> {


    }
    /**
     * @param e : the emergency to be handled which needs a near base to be assigned to
     * @return the nearest base found
     * Get the nearest base to an emergency
     */

    private fun findNearestBase(e: Emergency):Base {

    }
    /**
     * @param l : list of the emergencies to be distributed
     * @return the nearest base found
     * Get the nearest base to an emergency
     */

    private fun distributeEmergencies(l:List<Emergency>) {

    }

    /**
     * @param emergency : the emergency to get the severity for
     * @return the severity of the severity
     * Takes an emergency and returns its severity
     */
    private fun getSeverity(emergency:Emergency) {

    }


}
