package de.unisaarland.cs.se.selab.config

import org.junit.jupiter.api.Assertions.*
import java.io.File

class DotParserTest {
    @org.junit.jupiter.api.Test
    fun parse() {
        val fileContent = File("D:\\Projects\\test\\src\\main\\resources\\example_map.dot").readText()
        //print (fileContent)

        val newLineBeforeCurly : String = "digraph"+" " + "t3st " + "\n" + " {"
        val onlySpaces = "digraph t3st {"
        val onlyNewLine = "\n" + "digraph" + "\n" +  "t3st" + "\n" + "{"
        val list : List<String> = listOf(newLineBeforeCurly, onlySpaces, onlyNewLine)

        val regex = """\s*([a-zA-Z][a-zA-Z0-9]*)\s*\{""".toRegex()

        val matchResult = regex.find(fileContent)
        val matchedString = matchResult?.groupValues?.get(1)
        println("name:$matchedString")

//    for (line in list) {
//        val matchResult = regex.find(line)
//        //println("String:" + line)
//
//        if (matchResult != null) {
//            val matchedString = matchResult.groupValues[1]
//            //println("Matched String: $matchedString")
//        } else {
//            //println("No match found.")
//        }
        //}

        //val dotParser = DotParser("graphFilePath")
        //dotParser.parse()
    }
}
