package de.unisaarland.cs.se.selab.util
import de.unisaarland.cs.se.selab.controller.events.Event
import de.unisaarland.cs.se.selab.model.Ambulance
import de.unisaarland.cs.se.selab.model.Base
import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.FireTruck
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.model.PoliceCar
import de.unisaarland.cs.se.selab.model.Vehicle
import de.unisaarland.cs.se.selab.model.VehicleType
import de.unisaarland.cs.se.selab.model.map.Graph
import de.unisaarland.cs.se.selab.model.map.PrimaryType
import de.unisaarland.cs.se.selab.model.map.RoadProperties
import de.unisaarland.cs.se.selab.model.map.SecondaryType
import de.unisaarland.cs.se.selab.model.map.Vertex

class AssetManagerTest {
    val graph: Graph = createSimpleGraph()
    val maxTick: Int = 1
    val bases: Map<Int, Base> = mapOf(0 to createPoliceBase(), 1 to createFireBase(), 2 to createMedicalBase())
    val vehicles: Map<Int, Vehicle> = createVehicles()
    val vehicleToBase: MutableMap<Int, Int> = mutableMapOf()
    val emergencies: Map<Int, Emergency> = emptyMap()
    val tickToEmergencyId: Map<Int, List<Int>> = emptyMap()
    val events: Map<Int, Event> = emptyMap()
    var tickToEventId: Map<Int, List<Int>> = emptyMap()
    var model =
        Model(graph, maxTick, bases, vehicles, vehicleToBase, emergencies, tickToEmergencyId, events, tickToEventId)

    private val vertices: MutableMap<Int, Vertex> = mutableMapOf()

    private fun createSimpleGraph(): Graph {
        val g = Graph(createVertices())
        createEdges(g)
        return g
    }

    private fun createVertices(): MutableMap<Int, Vertex> {
        val vertices = mutableMapOf<Int, Vertex>()

        vertices[0] = Vertex(0, 0, BaseType.POLICE_STATION)
        vertices[1] = Vertex(1, 1, BaseType.FIRE_STATION)
        vertices[2] = Vertex(2, 2, BaseType.HOSPITAL)
        vertices[3] = Vertex(3, null, null)
        vertices[4] = Vertex(4, null, null)

        return vertices
    }

    private fun createEdges(g: Graph) {
        val roadProperties = listOf(
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r0", 60, 30),
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r1", 10, 30),
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r2", 10, 30),
            RoadProperties(PrimaryType.COUNTY, SecondaryType.ONE_WAY, "v0", "r3", 10, 30),
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r4", 10, 30),
            RoadProperties(PrimaryType.COUNTY, SecondaryType.NONE, "v0", "r5", 10, 30)
        )

        val edgeVertices = listOf(
            Pair(0, 1), Pair(1, 0), Pair(0, 2), Pair(2, 0),
            Pair(2, 1), Pair(1, 2), Pair(3, 4), Pair(4, 3),
            Pair(0, 3), Pair(3, 0), Pair(1, 4), Pair(4, 1)
        )

        edgeVertices.zip(roadProperties).forEach { (vertices, prop) ->
            g.addEdge(g.vertices[vertices.first]!!, g.vertices[vertices.second]!!, prop)
        }
    }

    private fun createPoliceBase(): Base {
        val base = Base(0, BaseType.POLICE_STATION, 0, 100, null, 10)
        var iter = 0
        // setting all ids of police vehicles 0->16
        while (iter < 17) {
            base.vehicles[iter] = iter
            iter++
        }
        // base.assignedEmergencies = mutableListOf()
        base.currentStaff = 100
        return base
    }
    private fun createFireBase(): Base {
        val base = Base(0, BaseType.FIRE_STATION, 0, 100, null, null)
        var iter = 0
        // setting all ids of fire vehicles 17->32
        while (iter < 16) {
            base.vehicles[iter + 16] = iter + 16 // id math
            iter++
        }
        // base.assignedEmergencies = mutableListOf()
        base.currentStaff = 100
        return base
    }
    private fun createMedicalBase(): Base {
        val base = Base(0, BaseType.HOSPITAL, 0, 100, 10, null)
        var iter = 0
        // setting all ids of medical vehicles 33->48 both included
        while (iter < 16) {
            base.vehicles[iter + 33] = iter + 33
            iter++
        }
        // base.assignedEmergencies = mutableListOf()
        base.currentStaff = 100
        return base
    }
    private fun createVehicles(): Map<Int, Vehicle> {
        val finalMap: MutableMap<Int, Vehicle> = mutableMapOf()

        for (i in 0..47) {
            when {
                i < 17 -> {
                    finalMap[i] = createPoliceVehicle(i)
                    vehicleToBase[0] = i
                }
                i in 17..32 -> {
                    finalMap[i] = createFireVehicle(i)
                    vehicleToBase[1] = i
                }
                else -> {
                    finalMap[i] = createMedicalVehicle(i)
                    vehicleToBase[2] = i
                }
            }
        }

        return finalMap
    }

    private fun createPoliceVehicle(id: Int): Vehicle {
        return when (id) {
            in 0..3 -> PoliceCar(id, 0, VehicleType.POLICE_MOTOR_CYCLE, 1, 1, 0)
            in 4..13 -> PoliceCar(id, 0, VehicleType.POLICE_CAR, 1, 1, 10)
            in 14..16 -> PoliceCar(id, 0, VehicleType.K9_POLICE_CAR, 1, 1, 0)
            else -> throw IllegalArgumentException("Invalid id for police vehicle: $id")
        }
    }

    private fun createFireVehicle(id: Int): Vehicle {
        return when (id - 17) {
            in 0..7 -> FireTruck(id, 0, VehicleType.FIRE_TRUCK_WATER, 1, 1, 2400)
            in 8..9 -> FireTruck(id, 0, VehicleType.FIRE_TRUCK_LADDER, 1, 1, 40)
            in 10..11 -> FireTruck(id, 0, VehicleType.FIREFIGHTER_TRANSPORTER, 1, 1, 0)
            in 12..15 -> FireTruck(id, 0, VehicleType.FIRE_TRUCK_TECHNICAL, 1, 1, 0)
            else -> throw IllegalArgumentException("Invalid id for fire vehicle: $id")
        }
    }

    private fun createMedicalVehicle(id: Int): Vehicle {
        return when (id - 33) {
            in 0..11 -> Ambulance(id, 0, VehicleType.AMBULANCE, 1, 1, 1)
            in 12..15 -> Ambulance(id, 0, VehicleType.AMBULANCE, 1, 1, 1)
            else -> throw IllegalArgumentException("Invalid id for medical vehicle: $id")
        }
    }
}
