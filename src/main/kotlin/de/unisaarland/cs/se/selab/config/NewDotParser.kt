package de.unisaarland.cs.se.selab.config

import java.io.BufferedReader
import java.io.FileReader

class NewDotParser(val graphFilePath: String) {

    private val fileString = FileReader(graphFilePath).readText().replace("""\s""".toRegex(), "")

    // private val reader = BufferedReader(fileReader)
    private var charCode: Int = 0
    private var currentIndex = 0
    private var maxIndex = fileString.length

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

    fun parse() {
        var closingBracketRead = false
        while (!endReached()) {
            val character = fileString[currentIndex]
            when (character) {
                'd' -> {
                    print(character)
                    parseDigraph()
                }

                '{' -> {
                    currentIndex++
                    parseVertices()
                }
                '}' -> {}
                else -> {
                    println("Error in parse level: $character")
                    break
                }
            }
            currentIndex++
        }
        if (!closingBracketRead) {
            println("Error: closing bracket not read")
        }
    }

    private fun parseDigraph() {
        var count = 0
        while (!endReached() && count < LABEL_DIGRAPH.length) {
            when (val character = fileString[currentIndex]) {
                LABEL_DIGRAPH[count] -> {
                    print(character)
                    count++
                    currentIndex++
                }

                else -> {
                    println("digraph Error: $character")
                    return
                }
            }
        }
        mapName = parseId()
    }

    private fun parseVertices() {
        var id = ""
        while (!endReached()) {
            val character = fileString[currentIndex]
            when (character) {
                isId(character) -> {
                    vertices.add(parseId())
                }
                ';' -> {
                }

                '-' -> {
                    if (fileString[currentIndex + 1] == '>') {
                        currentIndex -= 1
                        vertices.removeLast()
                        break
                    } else {
                        println("Error: $character")
                    }
                }

                else -> println("Error: $character")
            }
            currentIndex++
        }
        parseEdges()
    }

    private fun parseEdges() {
        var count = 0
        var source = ""
        var target = ""
        while (!endReached()) {
            when (val character = fileString[currentIndex]) {
                isId(character) -> source = parseId()
                '-' -> {
                    if (fileString[currentIndex + 1] == '>') {
                        currentIndex += 2
                        target = parseId()
                    } else {
                        println("Error: $character")
                    }
                }

                '[' -> {
                    currentIndex++
                    parseAttributes(count)
                    count++
                }
                ';' -> {
                    edgeIdToSourceTarget[count] = Pair(source, target)
                }
                '}' -> return
                else -> println("Error: $character")
            }
            currentIndex++
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
            when (val character = fileString[currentIndex]) {
                isLetter(character) -> {
                    attributeValues[currentLabel] = parseLabel(currentLabel, withId)
                }

                ';' -> {
                    labelIndex++
                }
            }
            currentIndex++
        }
        if (fileString[currentIndex] == ']') {
            edgeIdToAttributes[edgeId] = attributeValues
            currentIndex++
            return
        } else { println("error when parsing attributes: expected a ] but was $fileString[currentIndex]") }
    }

    private fun parseLabel(label: String, isId: Boolean): String {
        var count = 0
        var labelValue = ""
        while (!endReached() && count < label.length) {
            when (val character = fileString[currentIndex]) {
                label[count] -> count++
            }
            currentIndex++
        }
        if (fileString[currentIndex] == '=') {
            currentIndex++
            if (isId) labelValue = parseId() else labelValue = parseWord()
            return labelValue
        }
        println("Error: label value not found")
        return labelValue
    }

    private fun parseId(): String {
        var id = ""
        while (!endReached()) {
            when (val character = fileString[currentIndex]) {
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
                    println("id Error: expected a char that matches [a-zA-Z0-9] but was $character")
                    return ""
                }
            }
        }
        return id
    }

    private fun parseWord(): String {
        var word = ""
        while (!endReached()) {
            when (val character = fileString[currentIndex]) {
                isLetter(character), '_' -> word += character
                ';', '-' -> {
                    currentIndex--
                    return word
                }
                isNumber(character) -> {
                    println("word Error: expected a char that matches [a-zA-Z] but was $character")
                    return ""
                }

                else -> {
                    currentIndex--
                    println("word Error: expected a char that matches [a-zA-Z] but was $character")
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
            when (val character = fileString[currentIndex]) {
                isNumber(character) -> number += character
                ';', '-' -> {
                    currentIndex--
                    return number
                }

                else -> {
                    currentIndex--
                    println("number Error: expected a char that matches [0-9] but was $character")
                    break
                }
            }
            currentIndex++
        }
        return number
    }

    private fun consumeSpaces(reader: BufferedReader) {
        reader.reset()
        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            reader.mark(1)
            charCode = reader.read()
            println("space consumed")
        }
    }

    private fun isId(character: Char): Char? {
        return if (character.toString().matches(Regex("""[_a-zA-Z0-9]"""))) character else null
    }

    private fun isLetter(character: Char): Char? {
        return if (character.toString().matches(Regex("""[_a-zA-Z]"""))) character else null
    }

    private fun isNumber(character: Char): Char? {
        return if (character.toString().matches(Regex("""[0-9]"""))) character else null
    }

    private fun isSpace(character: Char): Char? {
        return if (character.toString().matches(Regex("""\s"""))) character else null
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
