package de.unisaarland.cs.se.selab.model

class Base(
    var baseId: Int,
    var baseType: BaseType,
    var vertexID: Int,
    val numStaff: Int,
    var currStaff: Int,
    var doctors: Int,
    var dogs: Int,
    var vehicles: MutableList<Int>,
    var assignedEmergencies: MutableList<Int>
) {


    fun addEmergency(emId: Int) {
        assignedEmergencies.add(emId)
    }

    fun addVehicle(vehicleId: Int) {
        vehicles.add(vehicleId)
    }
}

enum class BaseType {
    FIRE_STATION, POLICE_STATION, HOSPITAL;

    companion object {
        fun fromString(value: String): BaseType? {
            return when (value) {
                "FIRE_STATION" -> FIRE_STATION
                "POLICE_STATION" -> POLICE_STATION
                "HOSPITAL" -> HOSPITAL
                else -> null
            }
        }
    }
}
