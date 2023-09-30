package de.unisaarland.cs.se.selab.model.map

/**
 * The primary type of road.
 * */
enum class PrimaryType {
    MAIN, SIDE, COUNTY;

    companion object {
        /**
         * deserialize a string to a [PrimaryType].
         * */
        fun fromString(value: String): PrimaryType? {
            return when (value) {
                "MAIN", "mainStreet" -> MAIN
                "SIDE", "sideStreet" -> SIDE
                "COUNTY", "countyRoad" -> COUNTY
                else -> null
            }
        }

        /**
         * @param value the value
         * @return the value as a string
         */
        fun toString(value: PrimaryType): String {
            return when (value) {
                MAIN -> "mainRoad"
                SIDE -> "sideRoad"
                COUNTY -> "countyRoad"
            }
        }
    }
}
