package de.unisaarland.cs.se.selab.config

/**
 * Basic Info container.
 * @param id the id of the object corresponding to this info-object
 * @property infoMap used for checking of special properties
 */
abstract class BasicInfo(var id: Int) {
    abstract val infoMap: Map<String, Any?>
}
