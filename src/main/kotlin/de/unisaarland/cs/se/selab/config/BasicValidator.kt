package de.unisaarland.cs.se.selab.config

/**
 * The basic class for the Validators (not graph)
 * */
abstract class BasicValidator(jsonParser: JsonParser) {

    protected abstract var requiredProperties: List<String>
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
     * @param specificFields the list of special properties needed for that type
     * @return true if specific properties are in an entry iff it is a required property for it
     * */
    protected fun validateSpecificProperty(objectType: String, specificFields: List<String>): Boolean {
        TODO()
    }
}
