package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class CriminalOffloadingTest : SystemTest() {
    override val assets: String = "assetsJsons/CriminalOffloadAssets.json"
    override val map: String = "mapFiles/OneLongStreet.dot"
    override val maxTicks: Int = 20
    override val name: String = "CriminalOffloadingTest"
    override val scenario: String = "scenarioJsons/CriminalOffloadScenario.json"

    override suspend fun run() {
        TODO()
    }
}
