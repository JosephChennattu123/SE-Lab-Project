package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.util.Logger
import java.io.File

/**
 * parses the dot-file
 * @param graphFilePath the name of the file that contains the graph information
 * */
class DotParser(val graphFilePath: String) {

    private val fileContent = File(graphFilePath).readText()
    private val regexId = """([_a-zA-Z]+|\d*\.*\d*)""".toRegex()
    private val whitespace = """\s""".toRegex()

    private var parsingErrorOccurred: Boolean = false

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
        return matchResult.map { it.groupValues[0].replace(whitespace, "") }
            .toList()
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
                -1 // default value
            }
            val target = matchedString2?.toInt() ?: run {
                parsingErrorOccurred = true
                -1 // default value
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
        val regexAttributes = """\[\s*($regexId\s*=\s*$regexId\s*;\s*)*\s*]\s*;""".toRegex()
        for (edge in edges) {
            val matchResult = regexAttributes.findAll(edge)
            val matchedString =
                matchResult.map {
                    it.groupValues[0]
                        .replace(whitespace, "")
                        .replace("[\\[\\]]".toRegex(), "")
                }
            edgeIdToAttributes[edges.indexOf(edge)] = matchedString.first()
        }
        return edgeIdToAttributes
    }

    /**
     * extracts village name from attributes.
     * */
    fun parseVillageName(attributes: Map<Int, String>): Map<Int, String> {
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

    private fun matchRegex(regex: Regex, string: String): String {
        val matchResult = regex.find(string)
        val matchedString = matchResult?.groupValues?.get(1) ?: run {
            Logger.logParsingValidationResult(graphFilePath, false)
            parsingErrorOccurred = true
            "" // default value
        }
        return matchedString
    }

    /**
     * validates the syntax of the dot-file.
     * */
    fun validateSyntax() {
        val reconstructedFile = reconstructValidFile()
        if (parsingErrorOccurred) {
            Logger.logParsingValidationResult(graphFilePath, false)
            error("Parsing error occurred.")
        }
        val originalFileNoSpaces = fileContent.replace(whitespace, "")
        if (reconstructedFile != originalFileNoSpaces) {
            Logger.logParsingValidationResult(graphFilePath, false)
            error("Parsing error occurred.")
        }
    }

    /**
     * parses the dot-file to be checked for syntax errors and reconstructs the valid portion of it.
     * */
    private fun reconstructValidFile(): String {
        val countyName: String? = parseCountyName()
        val vertexIds: List<Int> = parseVertexIds()
        val edges: List<String> = parseEdges()
        val edgeIdToSourceTargetPairs: Map<Int, Pair<Int, Int>> = parseSourceAndTarget(edges)
        val edgeIdToAttributes: Map<Int, String> = parseAttributes(edges)
        val edgeIdToVillageName: Map<Int, String> = parseVillageName(edgeIdToAttributes)
        val edgeIdToRoadName: Map<Int, String> = parseRoadName(edgeIdToAttributes)
        val edgeIdToHeight: Map<Int, Int> = parseHeight(edgeIdToAttributes)
        val edgeIdToWeight: Map<Int, Int> = parseWeight(edgeIdToAttributes)
        val edgeIdToPrimaryType: Map<Int, String> = parsePrimaryType(edgeIdToAttributes)
        val edgeIdToSecondaryType: Map<Int, String> = parseSecondaryType(edgeIdToAttributes)

        // reconstruct county name.
        var reconstructedFile = "digraph$countyName{"

        // reconstruct vertices.
        for (vertexId in vertexIds) {
            reconstructedFile += "$vertexId;"
        }

        // reconstruct edges.
        for (i in edges.indices) {
            reconstructedFile += "${edgeIdToSourceTargetPairs[i]?.first}->${edgeIdToSourceTargetPairs[i]?.second}[" +
                "village=${edgeIdToVillageName[i]};" +
                "name=${edgeIdToRoadName[i]};" +
                "heightLimit=${edgeIdToHeight[i]};" +
                "weight=${edgeIdToWeight[i]};" +
                "primaryType=${edgeIdToPrimaryType[i]};" +
                "secondaryType=${edgeIdToSecondaryType[i]};" +
                "];"
        }

        // reconstruct closing curly bracket.
        reconstructedFile += "}"
        return reconstructedFile
    }
}
