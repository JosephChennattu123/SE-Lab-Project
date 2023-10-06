package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.vehicle.Ambulance
import de.unisaarland.cs.se.selab.model.vehicle.FireTruck
import de.unisaarland.cs.se.selab.model.vehicle.PoliceCar
import de.unisaarland.cs.se.selab.model.vehicle.Vehicle
import de.unisaarland.cs.se.selab.model.vehicle.VehicleType
import de.unisaarland.cs.se.selab.util.Logger

private const val WATER600 = 600
private const val WATER1200 = 1200
private const val WATER2400 = 2400
private const val LADDERMIN = 30
private const val LADDERMAX = 70
private const val CRIMINALSMIN = 1
private const val CRIMINALSMAX = 4
private const val STAFFMAXIMUM = 12
private const val VEHICLEMAXHEIGHT = 5

/**
 * Validates the vehicles
 */
class VehicleValidator(jsonParser: JsonParser) : BasicValidator(jsonParser) {

    override val requiredProperties: List<String> = listOf(ID, BASE_ID, VEHICLE_TYPE, STAFF_CAPACITY)
    override val optionalProperties: List<String> = listOf(WATER_CAPACITY, CRIMINAL_CAPACITY, LADDER_LENGTH)

    /**
     * Validates the information for vehicles and creates vehicles.
     *
     * @return the list of vehicles created
     */
    fun validate(bases: List<Base>): List<Vehicle>? {
        val jsonParserObj = jsonParser as JsonParser
        val vehicleInfos = jsonParserObj.parseVehicles()

        val vehicleIds: MutableList<Int> = mutableListOf()

        vehicleInfos.forEach { vehicleInfo ->
            val mandatoryFields = when (vehicleInfo.vehicleType) {
                VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR -> emptyList()
                VehicleType.FIRE_TRUCK_WATER -> listOf(WATER_CAPACITY)
                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIREFIGHTER_TRANSPORTER -> emptyList()
                VehicleType.FIRE_TRUCK_LADDER -> listOf(LADDER_LENGTH)
                VehicleType.POLICE_CAR -> listOf(CRIMINAL_CAPACITY)
                VehicleType.POLICE_MOTOR_CYCLE, VehicleType.K9_POLICE_CAR -> emptyList()
            }
            if (!validateSpecificProperties(vehicleInfo, optionalProperties, mandatoryFields) ||
                !validateAssetCapacity(vehicleInfo)
            ) {
                Logger.outputLogger.error { "vehicle has invalid specific properties or asset capacity is invalid" }
                return null
            }

            if (!validateStaffBounds(vehicleInfo) || !validateHeightBounds(vehicleInfo)) {
                return null
            }
            if (!checkBaseRequirements(vehicleInfo, bases)) {
                return null
            }
            vehicleIds.add(vehicleInfo.id)
        }

        if (vehicleIds.toSet().toList().sorted() != vehicleIds.sorted()) {
            Logger.outputLogger.error { "vehicle ids are not unique" }
            return null
        }
        return buildVehicles(vehicleInfos)
    }

    private fun checkBaseRequirements(vehicleInfo: VehicleInfo, bases: List<Base>): Boolean {
        if (validateBaseExists(vehicleInfo, bases)) {
            val returnValue = validateBasesNessesaryStaff(vehicleInfo, bases)
            if (!returnValue) {
                Logger.outputLogger.error { "base of this vehicle can not support the staff requirements" }
                return false
            }
        } else {
            Logger.outputLogger.error { "base of vehicle does not exist" }
            return false
        }
        return true
    }

    private fun buildVehicles(vehicleInfos: List<VehicleInfo>): List<Vehicle> {
        return vehicleInfos.map {
            when (it.vehicleType) {
                VehicleType.POLICE_CAR, VehicleType.POLICE_MOTOR_CYCLE, VehicleType.K9_POLICE_CAR ->
                    PoliceCar(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        it.criminalCapacity
                    )

                VehicleType.AMBULANCE ->
                    Ambulance(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        1
                    )

                VehicleType.EMERGENCY_DOCTOR_CAR ->
                    Ambulance(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        null
                    )

                VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIRE_TRUCK_WATER ->
                    FireTruck(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        if (it.vehicleType == VehicleType.FIRE_TRUCK_LADDER) {
                            it.ladderLength
                        } else {
                            it.waterCapacity
                        }
                    )

                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIREFIGHTER_TRANSPORTER -> {
                    FireTruck(
                        it.id,
                        it.baseId,
                        it.vehicleType,
                        it.vehicleHeight,
                        it.staffCapacity,
                        null
                    )
                }
            }
        }.toList()
    }

    /**
     * Checks the asset capacity is the right amount
     */
    private fun validateAssetCapacity(vehicleInfo: VehicleInfo): Boolean {
        val returnValue = when (vehicleInfo.vehicleType) {
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
        if (returnValue) {
            return true
        }
        Logger.outputLogger.error { "vehicle has invalid asset capacity" }
        return false
    }

    /**
     * Checks the staff capacity to be in bound.
     *
     * @return true if staff bounds where matched
     */
    private fun validateStaffBounds(vehicleInfo: VehicleInfo): Boolean {
        val returnValue = vehicleInfo.staffCapacity in 1..STAFFMAXIMUM
        if (returnValue) {
            return true
        }
        Logger.outputLogger.error { "vehicle has invalid staff amount" }
        return false
    }

    /**
     * Check that the height is in bound.
     *
     * @return true if height bounds where matched
     */
    private fun validateHeightBounds(vehicleInfo: VehicleInfo): Boolean {
        val returnValue = vehicleInfo.vehicleHeight in 1..VEHICLEMAXHEIGHT
        if (returnValue) {
            return true
        }
        Logger.outputLogger.error { "vehicle has invalid height" }
        return false
    }

    /**
     * Checks that the corresponding bases exist
     *
     * @return true if bases exist
     * */
    private fun validateBaseExists(vehicleInfo: VehicleInfo, bases: List<Base>): Boolean {
        return bases.any { it.baseId == vehicleInfo.baseId }
    }

    /**
     * Check that the correnspoding bases have enough staff
     *
     * @return true if bases have enough staff to fully staff each of the vehicles
     */
    private fun validateBasesNessesaryStaff(vehicleInfo: VehicleInfo, bases: List<Base>): Boolean {
        val base = bases.first { it.baseId == vehicleInfo.baseId }
        return base.numStaff >= vehicleInfo.staffCapacity
    }
}
