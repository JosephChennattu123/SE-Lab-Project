package de.unisaarland.cs.se.selab.config

/**
 * parses the dot-file
 * @param graphFilePath the name of the file that contains the graph information
 * */
class DotParser(graphFilePath: String) {

    var countyName: String = ""
    var vertexIds: List<String> = emptyList()
    var edges: List<String> = emptyList()
    var edgeIdToSourceTargetPairs: Map<Int, Pair<Int, Int>> = emptyMap()
    var edgeIdToAttributes: Map<Int, String> = emptyMap()
    var edgeIdToVillageName: Map<Int, String> = emptyMap()
    var edgeIdToRoadName: Map<Int, String> = emptyMap()
    var villagesToRoads: Map<String, List<String>> = emptyMap()
    var edgeIdToHeight: Map<Int, Int> = emptyMap()
    var edgeIdToWeight: Map<Int, Int> = emptyMap()
    var edgeIdToPrimaryType: Map<Int, String> = emptyMap()
    var edgeIdToSecondaryType: Map<Int, String> = emptyMap()

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
    private fun parseCountyName() {}

    /**
     * extracts the village name from the file.
     */
    private fun parseVertexIds() {}

    /**
     * extracts edges from the file.
     * */
    private fun parseEdges() {}

    /**
     * extracts the source and target from an edge.
     * */
    private fun parseSourceAndTarget(edge: String) {}

    /**
     * extracts the attributes from an edge string.
     * */
    private fun parseAttributes(edge: String) {}

    /**
     * extracts village name from attributes.
     * */
    private fun villageName(attributes: String) {}

    /**
     * extracts road name from attributes.
     * */
    private fun parseRoadName(attributes: String) {}

    /**
     * extracts road height from attributes.
     * */
    private fun parseHeight(attributes: String) {}

    /**
     * extracts road weight from attributes.
     * */
    private fun parseWeight(attributes: String) {}

    /**
     * extracts primary road type from attributes.
     * */
    private fun parsePrimaryType(attributes: String) {}

    /**
     * extracts secondary road type from attributes.
     * */
    private fun parseSecondaryType(attributes: String) {}
}
