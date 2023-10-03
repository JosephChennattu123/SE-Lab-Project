package de.unisaarland.cs.se.selab.config

import java.io.File
import java.io.FileReader
import java.lang.IndexOutOfBoundsException

/**
 * class used to read a file character by character.
 * @param filePath the path to the file to be read.
 * */
class DotFileReader(filePath: String) {

    val fileString: String = FileReader(filePath.replace("/", File.separator)).readText()
    private var currentIndex: Int = 0
    private val maxIndex: Int = fileString.length - 1

    /**
     * increases the index of the reader to the next non-whitespace character.
     * */
    fun increaseIndexToNextNonWhiteSpaceChar() {
        increaseIndex()
        if (readChar().isWhitespace()) {
            consumeWhiteSpace(false)
        }
        if (currentIndex > maxIndex) {
            currentIndex = maxIndex + 1
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

        if (readChar().isWhitespace()) {
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
        if (currentIndex < maxIndex) {
            currentIndex++
        }
    }

    /**
     * increments or decrements the current index until a non whitespace character is reached.
     * */
    private fun consumeWhiteSpace(reverse: Boolean) {
        val step = if (reverse) -1 else 1
        while (!endReached() && currentIndex >= 0 && readChar().isWhitespace()) {
            currentIndex += step
        }
    }

    /**
     * checks if the given character is a valid id character.
     * @return the character if it is a valid id character, null otherwise.
     * */
    fun isId(character: Char): Char? {
        return if (isLetter(character) != null || isNumber(character) != null) character else null
    }

    /**
     * checks if the given character is a valid letter.
     * @return the character if it is a valid letter, null otherwise.
     * */
    fun isLetter(character: Char): Char? {
        return if (character.code in LETTER_LOWER_START..LETTER_LOWER_END ||
            character.code in LETTER_UPPER_START..LETTER_UPPER_END
        ) {
            character
        } else {
            null
        }
    }

    /**
     * checks if the given character is a valid number.
     * @return the character if it is a valid number, null otherwise.
     * */
    fun isNumber(character: Char): Char? {
        return if (character.code in DIGIT_START..DIGIT_END) character else null
    }

    /**
     * reads the character on the current index and returns it.
     * */
    fun readChar(): Char {
        return fileString[currentIndex]
    }

    /**
     * checks if the current index is at the end of the file.
     * */
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
