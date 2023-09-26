package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class DotParserTest {
    @Test
    fun parseCountyName() {
        val newLineBeforeCurly: String = "digraph" + " " + "t3st " + "\n" + " {"
        val onlySpaces = "digraph t3st {"
        val onlyNewLine = "\n" + "digraph" + "\n" + "t3st" + "\n" + "{"
        val list: List<String> = listOf(newLineBeforeCurly, onlySpaces, onlyNewLine)
    }

    @Test
    fun parseVertices() {
        val input = "{" + "\n" + "1;" + "2" + "\n" + ";" + "32 ;" + "\n" + "7 ->" + "}"
    }

    @Test
    fun parseEdges() {
        // val input = ";" + "1" + "\n" + "->" + "\n" + "2" + "\n" + "[label=" + "\n" + "test;" + "\n" + "labelb=text;" + "];"
        val input = """5 -> 6 [vllage=Saarland; name=Motorway; heightLimit=a; weight = 1.6.; primaryType=countyRoad; secondaryType=none;];
            
    10 -> 11 [village=Saarland; name=Countryroad; _height_Limit_=2; weight = .39; primaryType=countyRoad; secondaryType=tunnel;];
    
    15 -> 1 [village=Saarland; name=Fastlane; heightLimit=10; weight = 29.a; primaryType=countyRoad; secondaryType=none;];"""

        // val regex ="""\s*[0-9]+\s*->\s*[0-9]+\s*\[([a-z]*\s*=\s*([a-z0-9]|_|\.)*\s*;\s*)*]""".toRegex()
        val fileContent = File("D:\\Projects\\SE Lab 2023\\src\\systemtest\\resources\\mapFiles\\example_map.dot").readText()
        val regex = """\s*[0-9]+\s*->\s*[0-9]+\s*\[(\s*[a-zA-Z_]+\s*=\s*(?:[a-zA-Z_]+|\d*\.*\d*)+\s*;\s*)*\s*]\s*;""".toRegex()
        val matchResult = regex.findAll(input)
        val matchedString = matchResult.map { it.groupValues[0].replace("\\s".toRegex(),"") }
            .toList()
        println("edges:$matchedString")
    }
}
