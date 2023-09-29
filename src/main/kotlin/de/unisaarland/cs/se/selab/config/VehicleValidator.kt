package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Ambulance
import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.FireTruck
import de.unisaarland.cs.se.selab.model.PoliceCar
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleType

private const val WATER600 = 600
private const val WATER1200 = 1200
private const val WATER2400 = 2400
private const val LADDERMIN = 30
private const val LADDERMAX = 70
private const val CRIMINALSMIN = 1
private const val CRIMINALSMAX = 4

/**
 * Validates the vehicles
 */
class VehicleValidator(jsonParser: JsonParser) : BasicValidator(jsonParser) {

    override var requiredProperties: List<String> = listOf("id", "baseId", "vehicleType", "staffCapacity")

    /**
     * Validates the information for vehicles and creates vehicles.
     *
     * @return the list of vehicles created
     */
    fun validate(bases: List<Base>): List<Vehicle>? {
        val jsonParserObj = jsonParser as JsonParser
        val vehicleInfos = jsonParserObj.parseVehicles()

        vehicleInfos.forEach { vehicleInfo ->
            if (vehicleInfo.vehicleType == VehicleType.FIRE_TRUCK_LADDER) {
                validateLadderLenght(vehicleInfo)
            } else if (vehicleInfo.ladderLength != null) {
                return null
            }
            if (vehicleInfo.vehicleType in listOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.POLICE_CAR)) {
                validateAssetCapacity(vehicleInfo)
            }
            validateStaffBounds(vehicleInfo)
            validateHeightBounds(vehicleInfo)
            validateBaseExists(vehicleInfo, bases)
            validateBasesNessesaryStaff(vehicleInfo, bases)
        }
        return vehicleInfos.map {
            when (it.vehicleType) {
                VehicleType.POLICE_CAR, VehicleType.POLICE_MOTOR_CYCLE, VehicleType.K9_POLICE_CAR ->
                    PoliceCar(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        it.criminalCapacity ?: 0
                    )

                VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR ->
                    Ambulance(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        1
                    )

                VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIREFIGHTER_TRANSPORTER ->
                    FireTruck(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        it.waterCapacity ?: 0
                    )
            }
        }.toList()
    }

    /**
     * Checks the asset capacity is the right amount
     */
    private fun validateAssetCapacity(vehicleInfo: VehicleInfo): Boolean {
        return when (vehicleInfo.vehicleType) {
            VehicleType.AMBULANCE -> true
            VehicleType.EMERGENCY_DOCTOR_CAR -> true
            VehicleType.FIRE_TRUCK_WATER -> vehicleInfo.waterCapacity as Int in listOf(
                WATER600,
                WATER1200,
                WATER2400
            )

            VehicleType.FIRE_TRUCK_TECHNICAL -> true
            VehicleType.FIRE_TRUCK_LADDER -> vehicleInfo.ladderLength as Int in LADDERMIN..LADDERMAX
            VehicleType.FIREFIGHTER_TRANSPORTER -> true
            VehicleType.POLICE_CAR -> vehicleInfo.criminalCapacity as Int in CRIMINALSMIN..CRIMINALSMAX
            VehicleType.K9_POLICE_CAR -> true
            VehicleType.POLICE_MOTOR_CYCLE -> true
        }
    }

    /**
     * Checks the staff capacity to be in bound.
     *
     * @return true if staff bounds where matched
     */
    private fun validateStaffBounds(vehicleInfo: VehicleInfo): Boolean {
        vehicleInfo
        TODO()
    }

    /**
     * Check that the height is in bound.
     *
     * @return true if height bounds where matched
     */
    private fun validateHeightBounds(vehicleInfo: VehicleInfo): Boolean {
        vehicleInfo
        TODO()
    }

    /**
     * Checks that the corresponding bases exist
     *
     * @return true if bases exist
     * */
    private fun validateBaseExists(vehicleInfo: VehicleInfo, bases: List<Base>): Boolean {
        vehicleInfo
        bases
        TODO()
    }

    /**
     * Check that the correnspoding bases have enough staff
     *
     * @return true if bases have enough staff to fully staff each of the vehicles
     */
    private fun validateBasesNessesaryStaff(vehicleInfo: VehicleInfo, bases: List<Base>): Boolean {
        vehicleInfo
        bases
        TODO()
    }

    /**
     * Check that the ladderlenght is in bounds (for FIRE_TRUCK_LADDER)
     *
     * @return true if bounds where matched
     */
    private fun validateLadderLenght(vehicleInfo: VehicleInfo): Boolean {
        vehicleInfo
        TODO()
    }
}
