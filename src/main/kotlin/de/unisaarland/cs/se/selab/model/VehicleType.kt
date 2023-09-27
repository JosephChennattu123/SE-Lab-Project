package de.unisaarland.cs.se.selab.model

enum class VehicleType {
    AMBULANCE,
    EMERGENCY_DOCTOR_CAR,
    FIRE_TRUCK_WATER,
    FIRE_TRUCK_TECHNICAL,
    FIRE_TRUCK_LADDER,
    FIREFIGHTER_TRANSPORTER,
    POLICE_CAR,
    K9_POLICE_CAR,
    POLICE_MOTOR_CYCLE;

    companion object {
        /**
         * deserializes a vehicle type from a string
         * @param value the string to deserialize
         * @return the deserialized vehicle type
         * */
        fun fromString(value: String): VehicleType? {
            return when (value) {
                "POLICE_CAR" -> POLICE_CAR
                "K9_POLICE_CAR" -> K9_POLICE_CAR
                "POLICE_MOTORCYCLE" -> POLICE_MOTOR_CYCLE
                "FIRE_TRUCK_WATER" -> FIRE_TRUCK_WATER
                "FIRE_TRUCK_TECHNICAL" -> FIRE_TRUCK_TECHNICAL
                "FIRE_TRUCK_LADDER" -> FIRE_TRUCK_LADDER
                "FIREFIGHTER_TRANSPORTER" -> FIREFIGHTER_TRANSPORTER
                "AMBULANCE" -> AMBULANCE
                "EMERGENCY_DOCTOR_CAR" -> EMERGENCY_DOCTOR_CAR
                else -> null
            }
        }
    }
}
