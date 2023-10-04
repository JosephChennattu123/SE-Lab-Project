package de.unisaarland.cs.se.selab.model

import de.unisaarland.cs.se.selab.model.vehicle.VehicleType

/**
 * Type and number of asset required for the fulfillment of an Emergency */
data class EmergencyRequirement(
    var vehicleType: VehicleType,
    var numberOfVehicles: Int,
    var amountOfAsset: Int?
)

/**
 * Type of asset */
enum class AssetType {
    CRIMINAL, LADDER, PATIENT, WATER
}
