package de.unisaarland.cs.se.selab.config

/**
 * @param graphFilePath the path to the file containing the graph
 */
class DotParser(val graphFilePath: String) {

    private val reader = FileReader(graphFilePath)

    var mapName = ""
    val vertices = mutableListOf<String>()
    val edgeIdToSourceTarget = mutableMapOf<Int, Pair<String, String>>()
    val edgeIdToAttributes = mutableMapOf<Int, MutableMap<String, String>>()

    private val labelList: List<String> = listOf(
        LABEL_VILLAGE,
        LABEL_ROAD_NAME,
        LABEL_HEIGHT_LIMIT,
        LABEL_WEIGHT,
        LABEL_PRIMARY_TYPE,
        LABEL_SECONDARY_TYPE,
        LABEL_MAIN_STREET,
        LABEL_SIDE_STREET,
        LABEL_COUNTY_ROAD,
        LABEL_ONE_WAY_STREET,
        LABEL_TUNNEL,
        LABEL_NONE
    )

    /**
     * Parses the dot file for the graph and checks that it is correct syntactically
     */
    fun parse(): Boolean {
        var closingBracketRead = false
        while (!reader.endReached()) {
            when (reader.readChar()) {
                'd' -> {
                    parseDigraph()
                }

                '{' -> {
                    reader.increaseIndexToNextNonWhiteSpaceChar()
                    val ret = parseVertices()
                    if (!ret) {
                        return false // problem in parseVertices/lower level
                    }
                }

                '}' -> {
                    closingBracketRead = true
                }

                else -> {
                    break
                }
            }
            reader.increaseIndexToNextNonWhiteSpaceChar()
        }
        if (!closingBracketRead) {
            error("top level parse error: expected a closing bracket but reached end of file")
        }
        // TODO check for character after end of graph
        return true
    }

    private fun parseDigraph() {
        var count = 0
        while (!reader.endReached() && count < LABEL_DIGRAPH.length) {
            when (val character = reader.readChar()) {
                LABEL_DIGRAPH[count] -> {
                    count++
                    reader.increaseIndex()
                }

                else -> {
                    error("Error in digraph: unexpected character \' $character\'")
                }
            }
        }
        if (reader.readChar().isWhitespace()) {
            reader.increaseIndexToNextNonWhiteSpaceChar()
        }
        mapName = parseId()
    }

    /**
     * return true if parsing of vertices successful and no error
     */
    private fun parseVertices(): Boolean {
        while (!reader.endReached()) {
            when (val character = reader.readChar()) {
                reader.isId(character) -> {
                    vertices.add(parseId())
                }

                ';' -> {
                }

                '-' -> {
                    // check if the next character is a >, if so, then we have reached the end of the vertices.
                    reader.increaseIndex()
                    if (reader.readChar() == '>') {
                        val vertex = vertices.removeLast()
                        // move back to the end of last vertex before the arrow.
                        reader.decreaseIndexToNextNonWhiteSpaceChar()
                        reader.decreaseIndexToNextNonWhiteSpaceChar()
                        // decreaseIndex()
                        // decreaseIndex()
                        // move back to the start of the last vertex.
                        reader.moveBackToLastVertex(vertex)
                        break
                    } else {
                        return false // illegal character
                    }
                }

                else -> {
                    return false // illegal character
                }
            }
            // currentIndex++
            reader.increaseIndex()
        }
        return parseEdges()
    }

    private fun parseEdges(): Boolean {
        var count = 0
        var source = ""
        var target = ""
        while (!reader.endReached()) {
            when (val character = reader.readChar()) {
                reader.isId(character) -> source = parseId()
                '-' -> {
                    reader.increaseIndex()
                    if (reader.readChar() == '>') {
                        reader.increaseIndexToNextNonWhiteSpaceChar()
                        target = parseId()
                    } else {
                        reader.decreaseIndexToNextNonWhiteSpaceChar()
                    }
                }

                '[' -> {
                    reader.increaseIndexToNextNonWhiteSpaceChar()
                    parseAttributes(count)
                    count++
                }

                ';' -> {
                    edgeIdToSourceTarget[count] = Pair(source, target)
                }

                '}' -> {
                    reader.decreaseIndexToNextNonWhiteSpaceChar()
                    return true // valid ending character
                }

                else -> {
                    return false // illegal character
                }
            }
            reader.increaseIndexToNextNonWhiteSpaceChar()
        }
        return false // reached end of file without completing the graph
    }

    private fun parseAttributes(edgeId: Int) {
        var labelIndex = 0
        val labelsWithIds = LABEL_ID_VALUES
        var withId = true
        var currentLabel = ""
        val attributeValues: MutableMap<String, String> = mutableMapOf()
        while (!reader.endReached() && labelIndex < AMOUNT_OF_LABELS) {
            withId = labelsWithIds >= labelIndex - 1
            currentLabel = labelList[labelIndex]
            when (val character = reader.readChar()) {
                reader.isLetter(character) -> {
                    attributeValues[currentLabel] = parseLabel(currentLabel, withId)
                }

                ';' -> {
                    labelIndex++
                }
            }
            reader.increaseIndexToNextNonWhiteSpaceChar()
        }
        if (reader.readChar() == ']') {
            edgeIdToAttributes[edgeId] = attributeValues
            return
        } else {
            // error("Error: expected a closing bracket but was ${readChar()}")
        }
    }

    private fun parseLabel(label: String, isId: Boolean): String {
        var count = 0
        var labelValue = ""
        while (!reader.endReached() && count < label.length) {
            when (reader.readChar()) {
                label[count] -> {
                    count++
                }
            }
            reader.increaseIndexToNextNonWhiteSpaceChar()
        }
        if (reader.readChar() == '=') {
            reader.increaseIndexToNextNonWhiteSpaceChar()
            if (isId) {
                labelValue = parseId()
            } else {
                labelValue = parseWord()
            }
            return labelValue
        }
        return labelValue
    }

    private fun parseId(): String {
        var id = ""
        while (!reader.endReached()) {
            when (val character = reader.readChar()) {
                reader.isLetter(character) -> {
                    id = parseWord()
                    return id
                }

                reader.isNumber(character) -> {
                    id = parseNumber()
                    return id
                }

                ';', '-' -> return id // TODO might cause a bug by parsing edges with id ->-> id.
                else -> {
                    return id
                }
            }
        }
        return id
    }

    private fun parseWord(): String {
        var word = ""
        while (!reader.endReached()) {
            when (val character = reader.readChar()) {
                reader.isLetter(character), '_' -> word += character
                ';', '-' -> {
                    reader.decreaseIndexToNextNonWhiteSpaceChar()
                    return word
                }

                else -> {
                    return word
                }
            }
            reader.increaseIndex()
        }
        return word
    }

    private fun parseNumber(): String {
        var number = ""
        while (!reader.endReached()) {
            when (val character = reader.readChar()) {
                reader.isNumber(character) -> number += character
                ';', '-' -> {
                    reader.decreaseIndexToNextNonWhiteSpaceChar()
                    return number
                }

                else -> {
                    reader.decreaseIndexToNextNonWhiteSpaceChar()
                    break
                }
            }
            reader.increaseIndexToNextNonWhiteSpaceChar()
        }
        return number
    }
}

private const val AMOUNT_OF_LABELS = 6
private const val LABEL_ID_VALUES = 4
private const val LABEL_DIGRAPH = "digraph"
private const val LABEL_VILLAGE = "village"
private const val LABEL_ROAD_NAME = "name"
private const val LABEL_HEIGHT_LIMIT = "heightLimit"
private const val LABEL_WEIGHT = "weight"
private const val LABEL_PRIMARY_TYPE = "primaryType"
private const val LABEL_SECONDARY_TYPE = "secondaryType"
private const val LABEL_MAIN_STREET = "mainStreet"
private const val LABEL_SIDE_STREET = "sideStreet"
private const val LABEL_COUNTY_ROAD = "countyRoad"
private const val LABEL_ONE_WAY_STREET = "oneWayStreet"
private const val LABEL_TUNNEL = "tunnel"
private const val LABEL_NONE = "none"
