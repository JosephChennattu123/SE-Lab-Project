package de.unisaarland.cs.se.selab.config

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * parses the dot-file
 * @param graphFilePath the name of the file that contains the graph information
 * */
class DotParser(val graphFilePath: String) {

    //private val graphFile: File = File(graphFilePath)
    //private val scanner = Scanner(graphFile.readText())
    //private var currentToken: String? = null

    var reader: BufferedReader = BufferedReader(FileReader(graphFilePath))
    val fileContent = File(graphFilePath).readText()

    private var parsingErrorOccurred: Boolean = false

    var countyName: String = ""
    var vertexIds: List<Int> = mutableListOf()
    var edges: List<String> = emptyList()
    var edgeIdToSourceTargetPairs: Map<Int, Pair<Int, Int>> = emptyMap()
    var edgeIdToAttributes: Map<Int, String> = emptyMap()
    var edgeIdToVillageName: Map<Int, String> = emptyMap()
    var edgeIdToRoadName: Map<Int, String> = emptyMap()
    var villagesToRoads: Map<String, List<String>> = emptyMap()
    var edgeIdToHeight: Map<Int, Int> = emptyMap()
    var edgeIdToWeight: Map<Int, Int> = emptyMap()
    var edgeIdToPrimaryType: Map<Int, String> = emptyMap()
    var edgeIdToSecondaryType: Map<Int, String> = emptyMap()

    /**
     * Parses the dot-file.
     * Extracts the vertices, edges and other information from the file-
     *
     * @return true if parsing was successful
     * */
    fun parse(): Boolean {
        TODO()
    }

    /**
     * extracts the county name from the file.
     */
    private fun parseCountyName() {
        val regex = """\s*([a-zA-Z][a-zA-Z0-9]*)\s*\{""".toRegex()
        val matchResult = regex.find(fileContent)
        val matchedString = matchResult?.groupValues?.get(1)
        countyName = matchedString ?: run {
            parsingErrorOccurred = true
            "" //default value
        }
    }

    /**
     * extracts the village name from the file.
     */
    private fun parseVertexIds() {
        val regexFromCurlyToEdge = """\{(\s*([0-9]+)\s*;\s*)+[0-9]+\s*-""".toRegex()
        val regexForVertexId = """\s*([0-9]+)\s*;""".toRegex()
        val unParsedVertices = regexFromCurlyToEdge.find(fileContent)
        val noCurlyNoEdge = unParsedVertices?.groupValues?.get(0)
        val matchResults = regexForVertexId.findAll(noCurlyNoEdge ?: "")
        vertexIds = matchResults.map { it.groupValues[1].toInt() }.toList()
    }

    /**
     * extracts edges from the file.
     * */
    private fun parseEdges() {}

    /**
     * extracts the source and target from an edge.
     * */
    private fun parseSourceAndTarget(edge: String) {}

    /**
     * extracts the attributes from an edge string.
     * */
    private fun parseAttributes(edge: String) {}

    /**
     * extracts village name from attributes.
     * */
    private fun villageName(attributes: String) {}

    /**
     * extracts road name from attributes.
     * */
    private fun parseRoadName(attributes: String) {}

    /**
     * extracts road height from attributes.
     * */
    private fun parseHeight(attributes: String) {}

    /**
     * extracts road weight from attributes.
     * */
    private fun parseWeight(attributes: String) {}

    /**
     * extracts primary road type from attributes.
     * */
    private fun parsePrimaryType(attributes: String) {}

    /**
     * extracts secondary road type from attributes.
     * */
    private fun parseSecondaryType(attributes: String) {}

//    private fun notFinished(): Boolean {
//        return scanner.hasNext()
//    }
}
