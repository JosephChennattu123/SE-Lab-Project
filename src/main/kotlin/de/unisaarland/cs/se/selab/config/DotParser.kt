package de.unisaarland.cs.se.selab.config

/**
 * parses the dot-file
 * @param graphFilePath the name of the file that contains the graph information
 * */
class DotParser(graphFilePath: String) {

    /**
     * Parses the dot-file.
     * Extracts the vertices, edges and other information from the file-
     *
     * @return true if parsing was successful
     * */
    fun parse(): Boolean {
        return false
    }

    /**
     * extracts the county name from the file.
     */
    private fun parseCountyName(): Unit {}

    /**
     * extracts the village name from the file.
     */
    private fun parseVertexIds(): Unit {}

    /**
     * extracts edges from the file.
     * */
    private fun parseEdges(): Unit {}

    /**
     * extracts the source and target from an edge.
     * */
    private fun parseSourceAndTarget(edge: String): Unit {}

    /**
     * extracts the attributes from an edge string.
     * */
    private fun parseAttributes(edge: String): Unit {}

    /**
     * extracts village name from attributes.
     * */
    private fun villageName(attributes: String): Unit {}

    /**
     * extracts road name from attributes.
     * */
    private fun parseRoadName(attributes: String): Unit {}

    /**
     * extracts road height from attributes.
     * */
    private fun parseHeight(attributes: String): Unit {}

    /**
     * extracts road weight from attributes.
     * */
    private fun parseWeight(attributes: String): Unit {}

    /**
     * extracts primary road type from attributes.
     * */
    private fun parsePrimaryType(attributes: String): Unit {}

    /**
     * extracts secondary road type from attributes.
     * */
    private fun parseSecondaryType(attributes: String): Unit {}
}