package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

/**
 * @property name
 * @property mapFileName
 * @property assetsFileName
 * @property scenarioFile
 * @property map
 * @property assets
 * @property scenario
 * @property maxTicks
 */
abstract class SystemTestBase(
    override val name: String,
    mapFolder: String,
    val mapFileName: String,
    assetsFolder: String,
    val assetsFileName: String,
    scenarioFolder: String,
    val scenarioFile: String,
    tick: Int = 1
) : SystemTest() {
    override val map: String = mapFolder + mapFileName
    override val assets: String = assetsFolder + assetsFileName
    override val scenario: String = scenarioFolder + scenarioFile
    override val maxTicks: Int = tick
}
