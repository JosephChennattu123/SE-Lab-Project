package de.unisaarland.cs.se.selab.config

import de.unisaarland.cs.se.selab.util.Logger

/**
 * The basic class for the Validators (not graph)
 * */
abstract class BasicValidator(jsonParser: JsonParser) {
    protected abstract val requiredProperties: List<String>
    protected abstract val optionalProperties: List<String>
    protected var jsonParser: JsonParser? = null

    init {
        this.jsonParser = jsonParser
    }

    /**
     * Validate ids.
     * Checks for duplicate ids and if the values are in range.
     *
     * @return true if ids are valid
     * */
    protected fun validateIds(): Boolean {
        TODO()
    }

    /**
     * Validates specific properties.
     * Checks if entries that need it have it and ones that should not have it really don't have them
     *
     * @param objectType the type of the entry
     * @param optionalFields the optional fields for this Info-object class
     * @param specificFields the list of special properties needed for that type
     * @return true if specific properties are in an entry iff it is a required property for it
     * */
    protected fun validateSpecificProperties(
        basicInfo: BasicInfo,
        optionalFields: List<String>,
        specificFields: List<String>
    ): Boolean {
        for (field in optionalFields) {
            if (field in specificFields) {
                if (basicInfo.infoMap[field] != null) {
                    continue
                }
                Logger.outputLogger.error {
                    "required specific field '$field' was null. " +
                        "optional fields: $optionalFields, specificFields: $specificFields"
                }
                return false
            } else if (field !in specificFields && basicInfo.infoMap[field] != null) {
                Logger.outputLogger.error {
                    "Invalid specific property: '$field' invalid. " +
                        "optional fields: $optionalFields, specificFields: $specificFields"
                }
                return false
            }
        }
        return true
    }
}
