package de.unisaarland.cs.se.selab.model.map

/**
 * The secondary type of a road
 */
enum class SecondaryType {
    ONE_WAY, TUNNEL, NONE;

    companion object {
        /**
         * deserialize a string to a [SecondaryType].
         * */
        fun fromString(value: String): SecondaryType? {
            return when (value) {
                "oneWayStreet" -> ONE_WAY
                "tunnel" -> TUNNEL
                "none" -> NONE
                else -> null
            }
        }
    }
}
