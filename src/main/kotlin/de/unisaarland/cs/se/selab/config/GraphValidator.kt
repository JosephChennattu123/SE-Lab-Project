package de.unisaarland.cs.se.selab.config

/**
 * Validates the graph
 */
class GraphValidator {
    /**
     * Validate the information for the graph and create the graph
     *
     * @param dotParser
     * @return the created graph if the information was valid
     */
    fun validate(dotParser: DotParser): Graph? {
        // TODO
        return null
    }

    /**
     * Validates the vertexIds
     *
     * @return true if the vertices are valid
     */
    private fun validateVertexIds(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that the vertices are each connected to at least another one
     *
     * @return true if all vertices are connected to the graph
     */
    private fun validateVerticesConnected(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that there is no direct edge from a vertex to itself
     *
     * @return true if there are no self loops at vertices
     */
    private fun validateNoSelfLoops(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that there does not exist more than one edge between any to vertices
     *
     * @return true if no duplicate connections exist between to vertices
     */
    private fun validateNoDuplicateConnections(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that vertices connected to and edge do exist
     * @return true if edges are connected to existing vertices
     */
    private fun validateEdgeConnectsExistingVertices(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that all vertices only have direct connections to edges
     * of the same village or the county (countyRoads)
     *
     * @return true if vertices only connected to their village or county
     */
    private fun validateSameVertexSameVillageOrCounty(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that road names are unique in a village
     *
     * @return true if road names are unique in each village
     */
    private fun validateRoadNamesUniqueInVillage(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that there exists a main street in every village
     *
     * @return true if every village has a main street
     */
    private fun validateMainStreetExistInVillages(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that there exists at least one side street
     *
     * @return true if a side street exists
     */
    private fun validateSideStreetExists(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that the weights of road are positive
     *
     * @return true if all roads have positive weight
     */
    private fun validateNonZeroRoadWeights(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that the minimum road height is 1
     * @return true if no road exists with a
     */
    private fun validateMinimumRoadHeight(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that the maximum specified height of a tunnel
     * is not exceeded.
     *
     * @return true if tunnel heights are valid
     */
    private fun validateMaximumTunnelHeight(): Boolean {
        // TODO
        return false
    }

    /**
     * Checks that no village name is equal to a county name
     */
    private fun validateVillageNameNotCountyName(): Boolean {
        // TODO newly added (was missing
        return false
    }
}