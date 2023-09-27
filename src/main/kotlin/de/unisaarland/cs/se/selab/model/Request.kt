package de.unisaarland.cs.se.selab.model

class Request private constructor(
    val requestId: Int,
    val mainBaseId: Int,
    val emergencyId: Int,
    val targetBaseId: Int,
    var processesBases: Set<Int>
) {
    companion object {
        private var currentRequestId = 0

        fun createNewRequest(
            mainBaseId: Int,
            emergencyId: Int,
            targetBaseId: Int,
            processesBases: Set<Int>
        ): Request {
            val newRequest = Request(currentRequestId, mainBaseId, emergencyId, targetBaseId, processesBases)
            currentRequestId++
            return newRequest
        }
    }
}

