package de.unisaarland.cs.se.selab.model

/**
 * @param mainBaseId the id of the main bases that was assigned to the emergency
 * @param emergencyId the id of the emergency
 * @param targetBaseId the id of the base that should process this request
 * @param processedBases the ids of bases that already have received a request this tick for this emergency
 */
class Request private constructor(
    val requestId: Int,
    val mainBaseId: Int,
    val emergencyId: Int,
    val targetBaseId: Int,
    var processedBases: Set<Int>
) {
    companion object {
        private var currentRequestId = 1

        /**
         * Create a new request
         *
         * @param mainBaseId the id of the main bases that was assigned to the emergency
         * @param emergencyId the id of the emergency
         * @param targetBaseId the id of the base that should process this request
         * @param processedBases the ids of bases that already have received a request this tick for this emergency
         */
        fun createNewRequest(
            mainBaseId: Int,
            emergencyId: Int,
            targetBaseId: Int,
            processedBases: Set<Int>
        ): Request {
            val newRequest = Request(currentRequestId, mainBaseId, emergencyId, targetBaseId, processedBases)
            currentRequestId++
            return newRequest
        }
    }
}
