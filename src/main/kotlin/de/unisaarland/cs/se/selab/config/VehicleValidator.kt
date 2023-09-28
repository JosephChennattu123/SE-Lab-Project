package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Vehicle

/**
 * Validates the vehicles
 */
class VehicleValidator : BasicValidator() {

    override var requiredProperties: List<String> = listOf("id", "baseId", "vehicleType", "staffCapacity")

    /**
     * Validates the information for vehicles and creates vehicles.
     *
     * @return the list of vehicles created
     */
    fun validate(): List<Vehicle>? {
        validateStaffBounds()
        validateHeightBounds()
        validateBaseExists()
        validateBasesNessesaryStaff()
        validateLadderLenght()
        TODO()
    }

    /**
     * Checks the staff capacity to be in bound.
     *
     * @return true if staff bounds where matched
     */
    private fun validateStaffBounds(): Boolean {
        TODO()
    }

    /**
     * Check that the height is in bound.
     *
     * @return true if height bounds where matched
     */
    private fun validateHeightBounds(): Boolean {
        TODO()
    }

    /**
     * Checks that the corresponding bases exist
     *
     * @return true if bases exist
     * */
    private fun validateBaseExists(): Boolean {
        TODO()
    }

    /**
     * Check that the correnspoding bases have enough staff
     *
     * @return true if bases have enough staff to fully staff each of the vehicles
     */
    private fun validateBasesNessesaryStaff(): Boolean {
        TODO()
    }

    /**
     * Check that the ladderlenght is in bounds (for FIRE_TRUCK_LADDER)
     *
     * @return true if bounds where matched
     */
    private fun validateLadderLenght(): Boolean {
        TODO()
    }
}
