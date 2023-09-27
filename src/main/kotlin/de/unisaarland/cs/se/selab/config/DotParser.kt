package de.unisaarland.cs.se.selab.config

import java.io.File

/**
 * parses the dot-file
 * @param graphFilePath the name of the file that contains the graph information
 * */
class DotParser(val graphFilePath: String) {

    private val fileContent = File(graphFilePath).readText()

    private val regexId = """([_a-zA-z]+|\d*\.*\d*)""".toRegex()

    private var parsingErrorOccurred: Boolean = false

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
    fun parseCountyName(): String? {
        val regex = """\s*([a-zA-Z][a-zA-Z0-9]*)\s*\{""".toRegex()
        val matchResult = regex.find(fileContent)
        val matchedString = matchResult?.groupValues?.get(1)
        return matchedString ?: run {
            parsingErrorOccurred = true
            null // default value
        }
    }

    /**
     * extracts the village name from the file.
     */
    fun parseVertexIds(): List<Int> {
        val regexFromCurlyToEdge = """\{(\s*([0-9]+)\s*;\s*)+[0-9]+\s*-""".toRegex()
        val regexForVertexId = """\s*([0-9]+)\s*;""".toRegex()
        val unParsedVertices = regexFromCurlyToEdge.find(fileContent)
        val noCurlyNoEdge = unParsedVertices?.groupValues?.get(0)
        val matchResults = regexForVertexId.findAll(noCurlyNoEdge ?: "")
        return matchResults.map { it.groupValues[1].toInt() }.toList()
    }

    /**
     * extracts edges from the file.
     * */
    fun parseEdges(): List<String> {
        val regex = """\s*[0-9]+\s*->\s*[0-9]+\s*\[(\s*[a-zA-z]+\s*=\s*[a-zA-z0-9]+\s*;\s*)*\s*]\s*;""".toRegex()
        val matchResult = regex.findAll(fileContent)
        val edges: List<String> = matchResult.map { it.groupValues[0].replace("\\s".toRegex(), "") }
            .toList()
        return edges
    }

    /**
     * extracts the source and target from an edge.
     * */
    fun parseSourceAndTarget(edges: List<String>): Map<Int, Pair<Int, Int>> {
        val edgeIdToSourceTargetPair = mutableMapOf<Int, Pair<Int, Int>>()
        val regexIdArrowId = """([0-9]+)->([0-9]+)\[""".toRegex()
        for ((index, edge) in edges.withIndex()) {
            val matchResult = regexIdArrowId.find(edge)
            val matchedString = matchResult?.groupValues?.get(1)
            val matchedString2 = matchResult?.groupValues?.get(2)
            val source = matchedString?.toInt() ?: run {
                parsingErrorOccurred = true
                0 // default value
            }
            val target = matchedString2?.toInt() ?: run {
                parsingErrorOccurred = true
                0 // default value
            }

            edgeIdToSourceTargetPair[index] = Pair(source, target)
        }
        return edgeIdToSourceTargetPair
    }

    /**
     * extracts the attributes from an edge string.
     * */
    fun parseAttributes(edges: List<String>): Map<Int, String> {
        val edgeIdToAttributes = mutableMapOf<Int, String>()
        val regexAttributes = """\[([a-zA-z]+\s*=\s*[a-zA-z0-9]+\s*;\s*)*\s*]\s*;""".toRegex()
        for ((index, edge) in edges.withIndex()) {
            edgeIdToAttributes[index] = matchRegex(regexAttributes, edge)
        }
        return edgeIdToAttributes
    }

    /**
     * extracts village name from attributes.
     * */
    fun villageName(attributes: Map<Int, String>): Map<Int, String> {
        val edgeIdToVillageName = mutableMapOf<Int, String>()
        val regexVillageName = """village\s*=\s*$regexId\s*;""".toRegex()
        for ((index, attribute) in attributes) {
            edgeIdToVillageName[index] = matchRegex(regexVillageName, attribute)
        }
        return edgeIdToVillageName
    }

    /**
     * extracts road name from attributes.
     * */
    fun parseRoadName(attributes: Map<Int, String>): Map<Int, String> {
        val edgeIdToRoadName = mutableMapOf<Int, String>()
        val regexRoadName = """name\s*=\s*$regexId\s*;""".toRegex()
        for ((index, attribute) in attributes) {
            edgeIdToRoadName[index] = matchRegex(regexRoadName, attribute)
        }
        return edgeIdToRoadName
    }

    /**
     * extracts road height from attributes.
     * */
    fun parseHeight(attributes: Map<Int, String>): Map<Int, Int> {
        val edgeIdToHeight = mutableMapOf<Int, Int>()
        val regexHeight = """heightLimit\s*=\s*$regexId\s*;""".toRegex()
        for ((index, attribute) in attributes) {
            edgeIdToHeight[index] = matchRegex(regexHeight, attribute).toInt()
        }
        return edgeIdToHeight
    }

    /**
     * extracts road weight from attributes.
     * */
    fun parseWeight(attributes: Map<Int, String>): Map<Int, Int> {
        val edgeIdToWeight = mutableMapOf<Int, Int>()
        val regexWeight = """weight\s*=\s*$regexId\s*;""".toRegex()
        for ((index, attribute) in attributes) {
            edgeIdToWeight[index] = matchRegex(regexWeight, attribute).toInt()
        }
        return edgeIdToWeight
    }

    /**
     * extracts primary road type from attributes.
     * */
    fun parsePrimaryType(attributes: Map<Int, String>): Map<Int, String> {
        val edgeIdToPrimaryType = mutableMapOf<Int, String>()
        val regexPrimaryType = """primaryType\s*=\s*(mainStreet|sideStreet|countyRoad)\s*;""".toRegex()
        for ((index, attribute) in attributes) {
            edgeIdToPrimaryType[index] = matchRegex(regexPrimaryType, attribute)
        }
        return edgeIdToPrimaryType
    }

    /**
     * extracts secondary road type from attributes.
     * */
    fun parseSecondaryType(attributes: Map<Int, String>): Map<Int, String> {
        val edgeIdToSecondaryType = mutableMapOf<Int, String>()
        val regexSecondaryType = """secondaryType\s*=\s*(oneWayStreet|tunnel|none)\s*;""".toRegex()
        for ((index, attribute) in attributes) {
            edgeIdToSecondaryType[index] = matchRegex(regexSecondaryType, attribute)
        }
        return edgeIdToSecondaryType
    }

    fun validateSyntax(): Boolean {
        TODO()
    }

    private fun matchRegex(regex: Regex, string: String): String {
        val matchResult = regex.find(string)
        val matchedString = matchResult?.groupValues?.get(1) ?: run {
            parsingErrorOccurred = true
            "" // default value
        }
        return matchedString
    }
}
