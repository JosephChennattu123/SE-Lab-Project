package de.unisaarland.cs.se.selab.config

/**
 * Validates the bases
 */
class BaseValidator: BasicValidator() {
    /**
     * Validates the information for bases and creates bases.
     *
     * @return the list of bases created
     */
    fun validate(): List<Base> {
        // TODO
        return listOf()
    }

    /**
     * Check that the number of doctors is non-negative
     *
     * @return true if number of doctors is valid
     */
    fun validateNumDoctors(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that the bases have at least 1 staff
     *
     * @return true if number of staff is valid
     */
    fun validateStaffBounds(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that there are only doctors in bases of type HOSPITAL
     *
     * @return true if the bases contain doctors only if they are hospitals
     */
    fun validateDoctorsOnlyInHospitals(): Boolean {
        // TODO also check if this is needed (special property)
        return false
    }

    /**
     * Check that the number of dogs is non-negative
     *
     * @return true if the number of dogs is valid
     */
    fun validateNumDogs(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that there are only dogs in bases of type POLICE_STATION
     *
     * @return true if the bases contain dogs only if they are police stations
     */
    fun validateDogsOnlyInHospitals(): Boolean {
        // TODO also check if this is needed (special property)
        return false
    }

    /**
     * Check that the corresponding vertices exist in the graph
     *
     * @return true if the vertices exist
     */
    fun validateVerticesExist(): Boolean {
        // TODO
        return false
    }

    /**
     * Check that there is at most one base on a vertex
     *
     * @return true if the base placement is valid
     */
    fun validateAtMostOneBaseOnEachVertex(): Boolean {
        // TODO
        return false
    }
}