package de.unisaarland.cs.se.selab.config

import java.io.FileReader
import java.lang.IndexOutOfBoundsException

private const val LETTER_LOWER_START = 65
private const val LETTER_LOWER_END = 90

private const val LETTER_UPPER_START = 97
private const val LETTER_UPPER_END = 122

private const val DIGIT_START = 48
private const val DIGIT_END = 57

/**
 * @param graphFilePath the path to the file containing the graph
 */
class NewDotParser(val graphFilePath: String) {

    private val fileString = FileReader(graphFilePath).readText() // .replace("""\s""".toRegex(), "")

    private var charCode: Int = 0
    private var currentIndex = 0

    private val maxIndex = fileString.length

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
        while (!endReached()) {
            val character = readChar(currentIndex)
            when (character) {
                'd' -> {
                    parseDigraph()
                }

                '{' -> {
                    increaseIndex()
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
            increaseIndex()
        }
        if (!closingBracketRead) {
            error("top level parse error: expected a closing bracket but reached end of file")
        }
        // TODO check for character after end of graph
        return true
    }

    private fun parseDigraph() {
        var count = 0
        while (!endReached() && count < LABEL_DIGRAPH.length) {
            when (val character = readChar(currentIndex)) {
                LABEL_DIGRAPH[count] -> {
                    count++
                    currentIndex++
                }

                else -> {
                    error("Error in digraph: unexpected character \' $character\'")
                }
            }
        }
        if (readChar(currentIndex).isWhitespace()) {
            increaseIndex()
        }
        mapName = parseId()
    }

    /**
     * return true if parsing of vertices successful and no error
     */
    private fun parseVertices(): Boolean {
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                isId(character) -> {
                    vertices.add(parseId())
                }

                ';' -> {
                }

                '-' -> {
                    // check if the next character is a >, if so, then we have reached the end of the vertices.
                    increaseIndex()
                    if (readChar(currentIndex) == '>') {
                        val vertex = vertices.removeLast()
                        // move back to the end of last vertex before the arrow.
                        decreaseIndex()
                        decreaseIndex()
                        // move back to the start of the last vertex.
                        moveBackToLastVertex(vertex)
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
            increaseIndex()
        }
        return parseEdges()
    }

    private fun parseEdges(): Boolean {
        var count = 0
        var source = ""
        var target = ""
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                isId(character) -> source = parseId()
                '-' -> {
                    increaseIndex()
                    if (readChar(currentIndex) == '>') {
                        increaseIndex()
                        target = parseId()
                    } else {
                        decreaseIndex()
                    }
                }

                '[' -> {
                    increaseIndex()
                    parseAttributes(count)
                    count++
                }

                ';' -> {
                    edgeIdToSourceTarget[count] = Pair(source, target)
                }

                '}' -> {
                    decreaseIndex()
                    return true // valid ending character
                }

                else -> {
                    return false // illegal character
                }
            }
            increaseIndex()
        }
        return false // reached end of file without completing the graph
    }

    private fun parseAttributes(edgeId: Int) {
        var labelIndex = 0
        val labelsWithIds = LABEL_ID_VALUES
        var withId = true
        var currentLabel = ""
        val attributeValues: MutableMap<String, String> = mutableMapOf()
        while (!endReached() && labelIndex < AMOUNT_OF_LABELS) {
            withId = labelsWithIds >= labelIndex - 1
            currentLabel = labelList[labelIndex]
            when (val character = readChar(currentIndex)) {
                isLetter(character) -> {
                    attributeValues[currentLabel] = parseLabel(currentLabel, withId)
                }

                ';' -> {
                    labelIndex++
                }
            }
            increaseIndex()
        }
        if (readChar(currentIndex) == ']') {
            edgeIdToAttributes[edgeId] = attributeValues
            return
        } else {
            error("Error: expected a closing bracket but was ${readChar(currentIndex)}")
        }
    }

    private fun parseLabel(label: String, isId: Boolean): String {
        var count = 0
        var labelValue = ""
        while (!endReached() && count < label.length) {
            when (readChar(currentIndex)) {
                label[count] -> {
                    count++
                }
            }
            increaseIndex()
        }
        if (readChar(currentIndex) == '=') {
            increaseIndex()
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
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                isLetter(character) -> {
                    id = parseWord()
                    return id
                }

                isNumber(character) -> {
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
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                isLetter(character), '_' -> word += character
                ';', '-' -> {
                    decreaseIndex()
                    return word
                }

                else -> {
                    return word
                }
            }
            currentIndex++
        }
        return word
    }

    private fun parseNumber(): String {
        var number = ""
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                isNumber(character) -> number += character
                ';', '-' -> {
                    decreaseIndex()
                    return number
                }

                else -> {
                    decreaseIndex()
                    break
                }
            }
            increaseIndex()
        }
        return number
    }

    private fun increaseIndex() {
        currentIndex++
        // reader.seek(currentIndex.toLong())
        charCode = getRawChar(currentIndex)

        if (charCode.toChar().isWhitespace()) {
            consumeWhiteSpace(false)
        }
        if (currentIndex > maxIndex) {
            currentIndex = maxIndex
        }
    }

    private fun decreaseIndex() {
        currentIndex -= 1
        if (currentIndex < 0) {
            currentIndex = 0
            throw IndexOutOfBoundsException("currentIndex was negative")
        }
        // reader.seek(currentIndex.toLong())
        charCode = getRawChar(currentIndex)

        // var index = currentIndex
        if (charCode.toChar().isWhitespace()) {
            consumeWhiteSpace(true)
        }

        if (currentIndex < 0) {
            throw IndexOutOfBoundsException("currentIndex was negative")
        }
    }

    private fun moveBackToLastVertex(vertex: String) {
        var loopIndex = 0
        while (loopIndex < vertex.length - 1) {
            loopIndex++
            decreaseIndex()
        }
    }

    private fun consumeWhiteSpace(reverse: Boolean) {
        var step = 0
        if (reverse) step = -1 else step = 1
        var index = currentIndex
        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            index += step
            charCode = getRawChar(index)
            // println("space consumed")
        }
        if (charCode == -1) {
            error("end of file reached but no yet done.")
        }
        currentIndex = index
    }

    private fun getRawChar(index: Int): Int {
        // charCode = reader.read()
        return if (index >= fileString.length) {
            -1
        } else {
            fileString[index].code
        }
    }

    private fun isId(character: Char): Char? {
        // return if (character.toString().matches(Regex("""[_a-zA-Z0-9]"""))) character else null
        return if (isLetter(character) != null || isNumber(character) != null) character else null
    }

    private fun isLetter(character: Char): Char? {
        // return if (character.toString().matches(Regex("""[_a-zA-Z]"""))) character else null
        return if (character.code in LETTER_LOWER_START..LETTER_LOWER_END ||
            character.code in LETTER_UPPER_START..LETTER_UPPER_END
        ) {
            character
        } else {
            null
        }
    }

    private fun isNumber(character: Char): Char? {
        // return if (character.toString().matches(Regex("""[0-9]"""))) character else null
        return if (character.code in DIGIT_START..DIGIT_END) character else null
    }

    private fun readChar(pos: Int): Char {
        // reader.seek(pos.toLong())
        charCode = getRawChar(pos)
        return charCode.toChar()
    }

    private fun endReached(): Boolean {
        return currentIndex >= maxIndex
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
