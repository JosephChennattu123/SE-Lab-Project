package de.unisaarland.cs.se.selab.controller.events

class ConstructionSite(id: Int, start: Int, duration: Int, sourceId: Int, targetId: Int, factor: Int, oneway: Boolean) :
    RoadEvent(id, EventType.CONSTRUCTION_SITE, duration) {
    override fun applyEffect() {
        TODO("Not yet implemented")
    }

    override fun decrementTimer() {
        TODO("Not yet implemented")
    }

}