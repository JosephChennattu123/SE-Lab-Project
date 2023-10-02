package de.unisaarland.cs.se.selab.model.map

/**
 * @param roadType the primary type of the road
 * @param secondaryType the secondary type of the road
 * @param weight the weight of the edge (the road)
 * @param height the height of the road
 * @param factor the factor that is applied on this road
 */
class RoadProperties(
    val roadType: PrimaryType,
    val secondaryType: SecondaryType,
    val villageName: String,
    val roadName: String,
    private val weight: Int,
    val height: Int,
    var factor: Int = 1
) {
    /**
     * @return the weight of the edge
     */
    fun getWeight(): Int {
        return weight * factor
    }
}
