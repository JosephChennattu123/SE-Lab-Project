package de.unisaarland.cs.se.selab.systemtest

object InitialisationLogging {

    /**
     * generates the Initialization Info for successfully parsed and validated
     */
    fun logSuccess(filename: String): String {
        return "Initialization Info: $filename successfully parsed and validated"
    }

    /**
     * generates the Initialization Info for failing parsed and validated
     */
    fun logFailed(filename: String): String {
        return "Initialization Info: $filename invalid"
    }
}