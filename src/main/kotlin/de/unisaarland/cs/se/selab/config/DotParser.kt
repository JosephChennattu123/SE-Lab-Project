package de.unisaarland.cs.se.selab.config

class DotParser(graphFilePath: String) {

    fun parse(): Boolean {
        return false
    }

    /**
     * extracts the county name from the file path.
     *  */
    private fun parseCountyName(): Unit {}

    /**
     * extracts the village name from the file path.
     * */
    private fun parseVertexIds(): Unit {}

    /**
     * extracts edges from the file path.
     * */
    private fun parseEdges(): Unit {}

    /**
     * extracts the source and target from an edge.
     * */
    private fun parseSourceAndTarget(): Unit {}

    /**
     * extracts the attributes from an edge string.
     * */
    private fun parseAttributes(): Unit {}

    /**
     * extracts village name from attributes.
     * */
    private fun villageName(): Unit {}

    /**
     * extracts road name from attributes.
     * */
    private fun parseRoadName(): Unit {}

    /**
     * extracts road height from attributes.
     * */
    private fun parseHeight(): Unit {}

    /**
     * extracts road weight from attributes.
     * */
    private fun parseWeight(): Unit {}

    /**
     * extracts primary road type from attributes.
     * */
    private fun parsePrimaryType(): Unit {}

    /**
     * extracts secondary road type from attributes.
     * */
    private fun parseSecondaryType(): Unit {}
}