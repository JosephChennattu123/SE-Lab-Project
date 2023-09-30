package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Test

internal class DotParserTest {

    @Test
    fun parseCountyName() {
        val newParser = NewDotParser("src/systemtest/resources/mapFiles/example_map.dot")
        newParser.parse()
    }
}
