package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.map.Graph

/**
 * Validates the bases
 */
class BaseValidator(jsonParser: JsonParser) : BasicValidator(jsonParser) {
    override var requiredProperties: List<String> = listOf("id", "baseType", "staff", "location")

    private var fail = false

    /**
     * Validates the information for bases and creates bases.
     *
     * @return the list of bases created
     */
    fun validate(graph: Graph): List<Base>? {
        val jsonParserObj = jsonParser as JsonParser
        val baseInfos = jsonParserObj.parseBases()

        baseInfos.forEach { baseInfo ->
            if (!validateDogsOnlyInPoliceStations(baseInfo) || !validateDoctorsOnlyInHospitals(baseInfo)) {
                return null
            }

            if (baseInfo.baseType == BaseType.HOSPITAL) {
                fail = !validateNumDoctors(baseInfo)
            } else if (baseInfo.baseType == BaseType.POLICE_STATION) {
                fail = !validateNumDogs(baseInfo)
            }
            if (fail) return null
            if (!validateStaffBounds(baseInfo) || !validateVerticesExist(baseInfo, graph)) return null
            if (!validateAtMostOneBaseOnEachVertex(baseInfo, graph)) return null
        }
        return baseInfos.map { Base(it.id, it.baseType, it.locationVertex, it.staff, it.doctors, it.dogs) }.toList()
    }

    /**
     * Check that the number of doctors is non-negative
     *
     * @return true if number of doctors is valid
     */
    private fun validateNumDoctors(baseInfo: BaseInfo): Boolean {
        val doctorsInt = baseInfo.doctors as Int
        return doctorsInt >= 0
    }

    /**
     * Check that the bases have at least 1 staff
     *
     * @return true if number of staff is valid
     */
    private fun validateStaffBounds(baseInfo: BaseInfo): Boolean {
        return baseInfo.staff > 0
    }

    /**
     * Check that there are only doctors in bases of type HOSPITAL
     *
     * @return true if the bases contain doctors only if they are hospitals
     */
    private fun validateDoctorsOnlyInHospitals(baseInfo: BaseInfo): Boolean {
        // TODO also check if this is needed (special property)
        return !(baseInfo.doctors != null && baseInfo.baseType != BaseType.HOSPITAL)
    }

    /**
     * Check that the number of dogs is non-negative
     *
     * @return true if the number of dogs is valid
     */
    private fun validateNumDogs(baseInfo: BaseInfo): Boolean {
        val dogsInt = baseInfo.dogs as Int
        return dogsInt >= 0
    }

    /**
     * Check that there are only dogs in bases of type POLICE_STATION
     *
     * @return true if the bases contain dogs only if they are police stations
     */
    private fun validateDogsOnlyInPoliceStations(baseInfo: BaseInfo): Boolean {
        // TODO also check if this is needed (special property)
        return !(baseInfo.dogs != null && baseInfo.baseType != BaseType.POLICE_STATION)
    }

    /**
     * Check that the corresponding vertices exist in the graph
     *
     * @return true if the vertices exist
     */
    private fun validateVerticesExist(baseInfo: BaseInfo, graph: Graph): Boolean {
        return graph.vertices.any { (_, vertex) -> vertex.vertexId == baseInfo.locationVertex }
    }

    /**
     * Check that there is at most one base on a vertex
     *
     * @return true if the base placement is valid
     */
    private fun validateAtMostOneBaseOnEachVertex(baseInfo: BaseInfo, graph: Graph): Boolean {
        return graph.vertices.filter { (_, vertex) ->
            vertex.vertexId == baseInfo.locationVertex &&
                vertex.baseId != null
        }.isEmpty()
    }
}
