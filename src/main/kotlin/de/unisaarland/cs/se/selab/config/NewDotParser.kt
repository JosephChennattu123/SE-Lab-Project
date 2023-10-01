package de.unisaarland.cs.se.selab.config

import java.io.BufferedReader
import java.io.File
import java.io.RandomAccessFile

private const val ERROR_COLON = "Error: "

/**
 * @param graphFilePath the path to the file containing the graph
 */
class NewDotParser(val graphFilePath: String) {

    // private val fileString = FileReader(graphFilePath).readText().replace("""\s""".toRegex(), "")

    private val reader = RandomAccessFile(File(graphFilePath.replace("/", File.separator)), "r")


    private var charCode: Int = 0
    private var currentIndex = 0

    // private var maxIndex = fileString.length
    private var maxIndex = reader.length()

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
    fun parse() {
        var closingBracketRead = false
        while (!endReached()) {
            // val character = fileString[currentIndex]
            val character = readChar(currentIndex)
            when (character) {
                'd' -> {
                    // print(character)
                    parseDigraph()
                }

                '{' -> {
                    // currentIndex++
                    increaseIndex()
                    parseVertices()
                }

                '}' -> {
                    closingBracketRead = true
                }

                else -> {
                    // println("Error in parse level: $character")
                    break
                }
            }
            // currentIndex++
            increaseIndex()
        }
        if (!closingBracketRead) {
            // println("Error: closing bracket not read")
        }
    }

    private fun readChar(pos: Int): Char {
        reader.seek(pos.toLong())

        charCode = reader.read()
        consumeWhiteSpace()
        return charCode.toChar()
    }

    private fun increaseIndex() {
        reader.seek(currentIndex.toLong())
        charCode = reader.read()
        currentIndex++

        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            charCode = reader.read()
            currentIndex++
            // println("space consumed")
        }
    }

    private fun decreaseIndex() {
        reader.seek(currentIndex.toLong())
        charCode = reader.read()
        currentIndex -= 1

        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            charCode = reader.read()
            currentIndex -= 1
            // println("space consumed")
        }
    }

    private fun consumeWhiteSpace() {
        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            charCode = reader.read()
            // println("space consumed")
        }
    }

    private fun parseDigraph() {
        var count = 0
        while (!endReached() && count < LABEL_DIGRAPH.length) {
            when (val character = readChar(currentIndex)) {
                // when (val character = fileString[currentIndex]) {
                LABEL_DIGRAPH[count] -> {
                    // print(character)
                    count++
                    // currentIndex++
                    increaseIndex()
                }

                else -> {
                    // println("digraph Error: $character")
                    return
                }
            }
        }
        mapName = parseId()
    }

    private fun parseVertices() {
        var id = ""
        while (!endReached()) {
            val character = readChar(currentIndex)
            // val character = fileString[currentIndex]
            when (character) {
                isId(character) -> {
                    vertices.add(parseId())
                }

                ';' -> {
                }

                '-' -> {
                    if (readChar(currentIndex + 1) == '>') {
                        //if (fileString[currentIndex + 1] == '>') {
                        val vertex = vertices.removeLast()
                        reader.seek(reader.filePointer - vertex.length)
                        currentIndex -= vertex.length
                        break
                    } else {
                        // println(ERROR_COLON + "$character")
                    }
                }

                else -> {
                    // println(ERROR_COLON + "$character")
                }
            }
            // currentIndex++
            increaseIndex()
        }
        parseEdges()
    }

    private fun parseEdges() {
        var count = 0
        var source = ""
        var target = ""
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                // when (val character = fileString[currentIndex]) {
                isId(character) -> source = parseId()
                '-' -> {
                    if (readChar(currentIndex + 1) == '>') {
                        // if (fileString[currentIndex + 1] == '>') {
                        currentIndex += 2
                        target = parseId()
                    } else {
                        // println("Error: $character")
                    }
                }

                '[' -> {
                    // currentIndex++
                    increaseIndex()
                    parseAttributes(count)
                    count++
                }

                ';' -> {
                    edgeIdToSourceTarget[count] = Pair(source, target)
                }

                '}' -> return
                else -> {
                    // println("Error: $character")
                }
            }
            // currentIndex++
            increaseIndex()
        }
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
                // when (val character = fileString[currentIndex]) {
                isLetter(character) -> {
                    attributeValues[currentLabel] = parseLabel(currentLabel, withId)
                }

                ';' -> {
                    labelIndex++
                }
            }
            // currentIndex++
            increaseIndex()
        }
        if (readChar(currentIndex) == ']') {
            // if (fileString[currentIndex] == ']') {
            edgeIdToAttributes[edgeId] = attributeValues
            // currentIndex++
            increaseIndex()
            return
        } else {
            // println("error when parsing attributes: expected a ] but was $fileString[currentIndex]")
        }
    }

    private fun parseLabel(label: String, isId: Boolean): String {
        var count = 0
        var labelValue = ""
        while (!endReached() && count < label.length) {
            when (val character = readChar(currentIndex)) {
                // when (val character = fileString[currentIndex]) {
                label[count] -> count++
            }
            // currentIndex++
            increaseIndex()
        }
        if (readChar(currentIndex) == '=') {
            // if (fileString[currentIndex] == '=') {
            // currentIndex++
            increaseIndex()
            if (isId) labelValue = parseId() else labelValue = parseWord()
            return labelValue
        }
        // println("Error: label value not found")
        return labelValue
    }

    private fun parseId(): String {
        var id = ""
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                // when (val character = fileString[currentIndex]) {
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
                    // println("id Error: expected a char that matches [a-zA-Z0-9] but was $character")
                    return ""
                }
            }
        }
        return id
    }

    private fun parseWord(): String {
        var word = ""
        // currentIndex++
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                // when (val character = fileString[currentIndex]) {
                isLetter(character), '_' -> word += character
                ';', '-' -> {
                    // currentIndex--
                    decreaseIndex()
                    return word
                }

                isNumber(character) -> {
                    // println("word Error: expected a char that matches [a-zA-Z] but was $character")
                    return ""
                }

                else -> {
                    // currentIndex--
                    decreaseIndex()
                    // println("word Error: expected a char that matches [a-zA-Z] but was $character")
                    return word
                }
            }
            // currentIndex++
            increaseIndex()
        }
        return word
    }

    private fun parseNumber(): String {
        var number = ""
        while (!endReached()) {
            when (val character = readChar(currentIndex)) {
                // when (val character = fileString[currentIndex]) {
                isNumber(character) -> number += character
                ';', '-' -> {
                    // currentIndex--
                    decreaseIndex()
                    return number
                }

                else -> {
                    // currentIndex--
                    decreaseIndex()
                    // println("number Error: expected a char that matches [0-9] but was $character")
                    break
                }
            }
            // currentIndex++
            increaseIndex()
        }
        return number
    }

    /*private fun consumeSpaces(reader: BufferedReader) {
        reader.reset()
        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            reader.mark(1)
            charCode = reader.read()
            println("space consumed")
        }
    }*/

    private fun isId(character: Char): Char? {
        return if (character.toString().matches(Regex("""[_a-zA-Z0-9]"""))) character else null
    }

    private fun isLetter(character: Char): Char? {
        return if (character.toString().matches(Regex("""[_a-zA-Z]"""))) character else null
    }

    private fun isNumber(character: Char): Char? {
        return if (character.toString().matches(Regex("""[0-9]"""))) character else null
    }

    /*private fun isSpace(character: Char): Char? {
        return if (character.toString().matches(Regex("""\s"""))) character else null
    }*/

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
