package de.unisaarland.cs.se.selab.model.map


/**
 * @param roadType the primary type of the road
 * @param secondaryType the secondary type of the road
 * @param weight the weight of the edge (the road)
 * @param height the height of the road
 */
class RoadProperties(val roadType: PrimaryType, val secondaryType: SecondaryType, val weight: Int, val height: Int) {
    var factor: Int = 1
}
