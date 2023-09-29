package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Test

internal class DotParserTest {

    @Test
    fun parseCountyName() {
        val dotParser = DotParser("C:\\Projects\\SE-Lab 2023\\src\\systemtest\\resources\\mapFiles\\60NodeMap.dot")
        dotParser.validateSyntax()
    }
}
