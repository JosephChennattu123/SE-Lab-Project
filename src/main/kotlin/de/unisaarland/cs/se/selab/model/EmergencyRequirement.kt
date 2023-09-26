package de.unisaarland.cs.se.selab.model

/**
 * Type and number of asset required for the fulfillment of an Emergency */
data class EmergencyRequirement(
    var vehicleType: VehicleType,
    var assetType: AssetType?,
    var numberOfVehicles: Int,
    var amountOfAsset: Int?
)

/**
 * Type of asset */
enum class AssetType {
    CRIMINAL, LADDER, PATIENT, WATER
}
