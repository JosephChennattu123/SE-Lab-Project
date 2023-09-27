package de.unisaarland.cs.se.selab.model

/**
 * The `Base` class represents a facility or station that can respond to emergencies.
 *
 * @property baseId The unique identifier for the base.
 * @property baseType The type of the base (e.g., FIRE_STATION, POLICE_STATION, HOSPITAL).
 * @property vertexID The identifier of the vertex in the simulation graph where the base is located.
 * @property numStaff The number of staff members available at the base.
 * @property doctors The number of doctors available at the base (relevant for medical bases).
 * @property dogs The number of search and rescue dogs available at the base (relevant for certain bases).
 * @property vehicles A list of unique identifiers for vehicles stationed at the base.
 * @property assignedEmergencies A list of unique identifiers for emergencies currently assigned to the base.
 */
class Base(
    private var baseId: Int,
    private var baseType: BaseType,
    private var vertexID: Int,
    private var numStaff: Int,
    private var doctors: Int,
    private var dogs: Int,
    private var vehicles: MutableList<Int>,
    private var assignedEmergencies: MutableList<Int>
) {
    /**
     * Adds an emergency to the list of emergencies assigned to this base.
     *
     * @param emId The unique identifier of the emergency to be added.
     */
    fun addEmergency(emId: Int) {
        assignedEmergencies.add(emId)
    }

    /**
     * Adds a vehicle to the list of vehicles stationed at this base.
     *
     * @param vehicleId The unique identifier of the vehicle to be added.
     */
    fun addVehicle(vehicleId: Int) {
        vehicles.add(vehicleId)
    }
}

/**
 * The `BaseType` enum represents the types of bases that can exist in the simulation.
 */
enum class BaseType {
    FIRE_STATION, POLICE_STATION, HOSPITAL
}
