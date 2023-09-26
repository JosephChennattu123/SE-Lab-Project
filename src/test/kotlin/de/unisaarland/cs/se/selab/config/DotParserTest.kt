package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class DotParserTest {
    @Test
    fun parseCountyName() {
        val newLineBeforeCurly : String = "digraph"+" " + "t3st " + "\n" + " {"
        val onlySpaces = "digraph t3st {"
        val onlyNewLine = "\n" + "digraph" + "\n" +  "t3st" + "\n" + "{"
        val list : List<String> = listOf(newLineBeforeCurly, onlySpaces, onlyNewLine)
    }

    @Test
    fun parseVertices() {
       val input = "{" + "\n"+ "1;" + "2" + "\n" + ";" + "32 ;" + "\n" + "7 ->" + "}"
    }

    @Test
    fun parseEdges() {
        val input = ";" + "1" + "\n" + "->" + "\n" + "2" + "\n" + "[label=" + "\n" + "test" + "\n" + "];"
        val regex ="""""".toRegex()
        val matchResult = regex.find(input)
        val matchedString = matchResult?.groupValues?.get(0)
        println(matchedString)


    }

}
