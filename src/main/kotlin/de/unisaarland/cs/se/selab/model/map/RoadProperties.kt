package de.unisaarland.cs.se.selab.model.map
/**
 * @param roadType
 * @param secondaryType
 * @param weight
 * @param height
 * @param factor
 * */
class RoadProperties(
    val roadType: PrimaryType,
    val secondaryType: SecondaryType,
    val weight: Int,
    val height: Int,
    var factor: Int
)
