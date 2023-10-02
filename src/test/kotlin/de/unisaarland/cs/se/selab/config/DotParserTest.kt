package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DotParserTest {
    val validMap = "src/systemtest/resources/mapFiles/validTest/example_map.dot"
    val brokenMap1 = "src/test/resources/mapFiles/brokenTest/broken_map1.dot"
    val brokenMap2 = "src/test/resources/mapFiles/brokenTest/broken_map2.dot"
    val brokenMap3 = "src/test/resources/mapFiles/brokenTest/broken_map3.dot"
    val brokenMap4 = "src/test/resources/mapFiles/brokenTest/broken_map4.dot"

    @Test
    fun testValidFile() {
        val parser = DotParser(validMap)
        assertTrue(parser.parse())
    }

    @Test
    fun testMissingSeparators() {
        val parser = DotParser(brokenMap1)
        assertFalse(parser.parse())
    }

    @Test
    fun testNoSeparatorsBetweenLabels() {
        val parser = DotParser(brokenMap2)
        assertFalse(parser.parse())
    }

    @Test
    fun testIllegalChars() {
        val parser = DotParser(brokenMap3)
        assertFalse(parser.parse())
    }

    @Test
    fun testTextAfterClosingBrace() {
        val parser = DotParser(brokenMap4)
        assertFalse(parser.parse())
    }
}
