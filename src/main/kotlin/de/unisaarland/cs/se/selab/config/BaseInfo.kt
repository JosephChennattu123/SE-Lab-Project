package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.BaseType

const val STAFF = "staff"
const val BASE_TYPE = "baseType"
const val LOCATION_VERTEX = "locationVertex"
const val DOCTORS = "doctors"
const val DOGS = "dogs"

/**
 * Collects the info to construct bases
 *
 * @param id baseId
 * @param baseType type of base
 * @param locationVertex id of the vertex which is the location of the base
 * @param staff the total number of staff in the base
 */
class BaseInfo(
    id: Int,
    val baseType: BaseType,
    val locationVertex: Int,
    val staff: Int,
    val doctors: Int?,
    val dogs: Int?
) : BasicInfo(id) {
    override val infoMap = mapOf(
        ID to id,
        BASE_TYPE to baseType,
        LOCATION_VERTEX to locationVertex,
        STAFF to staff,
        DOCTORS to doctors,
        DOGS to dogs
    )
}
