package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class GraphValidatorTest {
    val validMap = "src/systemtest/resources/mapFiles/example_map.dot"

    @Test
    fun validate() {
        val dotParser = DotParser(validMap)
        val graphValidator = GraphValidator()
        assertNotNull(graphValidator.validate(dotParser))
    }
}
