package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Base

/**
 * Validates the bases
 */
class BaseValidator : BasicValidator() {
    override var requiredProperties: List<String> = listOf("id", "baseType", "staff", "location")

    /**
     * Validates the information for bases and creates bases.
     *
     * @return the list of bases created
     */
    fun validate(): List<Base> {
        validateNumDoctors()
        validateStaffBounds()
        validateDoctorsOnlyInHospitals()
        validateNumDogs()
        validateDogsOnlyInPoliceStations()
        validateVerticesExist()
        validateAtMostOneBaseOnEachVertex()
        TODO()
    }

    /**
     * Check that the number of doctors is non-negative
     *
     * @return true if number of doctors is valid
     */
    private fun validateNumDoctors(): Boolean {
        TODO()
    }

    /**
     * Check that the bases have at least 1 staff
     *
     * @return true if number of staff is valid
     */
    private fun validateStaffBounds(): Boolean {
        TODO()
    }

    /**
     * Check that there are only doctors in bases of type HOSPITAL
     *
     * @return true if the bases contain doctors only if they are hospitals
     */
    private fun validateDoctorsOnlyInHospitals(): Boolean {
        // TODO also check if this is needed (special property)
        TODO()
    }

    /**
     * Check that the number of dogs is non-negative
     *
     * @return true if the number of dogs is valid
     */
    private fun validateNumDogs(): Boolean {
        TODO()
    }

    /**
     * Check that there are only dogs in bases of type POLICE_STATION
     *
     * @return true if the bases contain dogs only if they are police stations
     */
    private fun validateDogsOnlyInPoliceStations(): Boolean {
        // TODO also check if this is needed (special property)
        TODO()
    }

    /**
     * Check that the corresponding vertices exist in the graph
     *
     * @return true if the vertices exist
     */
    private fun validateVerticesExist(): Boolean {
        TODO()
    }

    /**
     * Check that there is at most one base on a vertex
     *
     * @return true if the base placement is valid
     */
    private fun validateAtMostOneBaseOnEachVertex(): Boolean {
        TODO()
    }
}
