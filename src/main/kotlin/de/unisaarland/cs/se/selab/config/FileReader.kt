package de.unisaarland.cs.se.selab.config

import java.io.FileReader
import java.lang.IndexOutOfBoundsException

class FileReader(filePath: String) {

    private val fileString: String = FileReader(filePath).readText()
    private var currentIndex: Int = 0
    private val maxIndex: Int = fileString.length - 1
    var charCode = readCharCode(currentIndex)

    /**
     * increases the index of the reader to the next non-whitespace character.
     * */
    fun increaseIndexToNextNonWhiteSpaceChar() {
        currentIndex++
        // reader.seek(currentIndex.toLong())
        charCode = readCharCode(currentIndex)

        if (charCode.toChar().isWhitespace()) {
            consumeWhiteSpace(false)
        }
        if (currentIndex > maxIndex) {
            currentIndex = maxIndex
        }
    }

    /**
     * decreases the index of the reader to the next non-whitespace character.
     * */
    fun decreaseIndexToNextNonWhiteSpaceChar() {
        currentIndex -= 1
        if (currentIndex < 0) {
            currentIndex = 0
            throw IndexOutOfBoundsException("currentIndex was negative")
        }
        // reader.seek(currentIndex.toLong())
        charCode = readCharCode(currentIndex)

        // var index = currentIndex
        if (charCode.toChar().isWhitespace()) {
            consumeWhiteSpace(true)
        }

        if (currentIndex < 0) {
            throw IndexOutOfBoundsException("currentIndex was negative")
        }
    }

    /**
     * moves the current index of the reader vertex length steps back.
     * */
    fun moveBackToLastVertex(vertex: String) {
        var loopIndex = 0
        while (loopIndex < vertex.length - 1) {
            loopIndex++
            decreaseIndexToNextNonWhiteSpaceChar()
        }
    }

    /**
     * increments the current index of the reader by one.
     * */
    fun increaseIndex() {
        currentIndex++
    }

    /**
     * increments or decrements the current index until a non whitespace character is reached.
     * */
    private fun consumeWhiteSpace(reverse: Boolean) {
        val step = if (reverse) -1 else 1
        var index = currentIndex
        while (charCode != -1 && charCode.toChar().isWhitespace()) {
            index += step
            charCode = readCharCode(index)
            // println("space consumed")
        }
        if (charCode == -1) {
            error("end of file reached but no yet done.")
        }
        currentIndex = index
    }

    /**
     * returns the ascii code of the current char
     * */
    fun readCharCode(index: Int): Int {
        // charCode = reader.read()
        return if (index >= fileString.length) {
            -1
        } else {
            fileString[index].code
        }
    }

    fun isId(character: Char): Char? {
        // return if (character.toString().matches(Regex("""[_a-zA-Z0-9]"""))) character else null
        return if (isLetter(character) != null || isNumber(character) != null) character else null
    }

    fun isLetter(character: Char): Char? {
        // return if (character.toString().matches(Regex("""[_a-zA-Z]"""))) character else null
        return if (character.code in LETTER_LOWER_START..LETTER_LOWER_END ||
            character.code in LETTER_UPPER_START..LETTER_UPPER_END
        ) {
            character
        } else {
            null
        }
    }

    fun isNumber(character: Char): Char? {
        return if (character.code in DIGIT_START..DIGIT_END) character else null
    }

    fun readChar(): Char {
        charCode = readCharCode(currentIndex)
        return charCode.toChar()
    }

    fun endReached(): Boolean {
        return currentIndex >= maxIndex
    }
}
private const val LETTER_LOWER_START = 65
private const val LETTER_LOWER_END = 90

private const val LETTER_UPPER_START = 97
private const val LETTER_UPPER_END = 122

private const val DIGIT_START = 48
private const val DIGIT_END = 57
