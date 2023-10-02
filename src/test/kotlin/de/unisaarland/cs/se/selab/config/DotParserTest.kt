package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Test

internal class DotParserTest {

    @Test
    fun parseCountyName() {
        // val newParser = DotParser("src/systemtest/resources/mapFiles/example_map.dot")
        val newParser = DotParser("src/systemtest/resources/mapFiles/brokenTest/mapV100_1.dot")
        newParser.parse()
    }
}
