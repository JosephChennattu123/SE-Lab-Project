package de.unisaarland.cs.se.selab.model.vehicle

import de.unisaarland.cs.se.selab.model.BaseType

/** enum class for Vehicle Type */
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

        /**
         * @return the BaseType of the base which this vehicle could from
         * */
        fun getBaseType(vehicleType: VehicleType): BaseType {
            return when (vehicleType) {
                POLICE_CAR,
                K9_POLICE_CAR,
                POLICE_MOTOR_CYCLE -> BaseType.POLICE_STATION

                FIRE_TRUCK_WATER,
                FIRE_TRUCK_TECHNICAL,
                FIRE_TRUCK_LADDER,
                FIREFIGHTER_TRANSPORTER -> BaseType.FIRE_STATION

                AMBULANCE,
                EMERGENCY_DOCTOR_CAR -> BaseType.HOSPITAL
            }
        }
    }
}
