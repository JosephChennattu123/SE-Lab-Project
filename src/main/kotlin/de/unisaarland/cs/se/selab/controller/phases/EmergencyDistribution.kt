import de.unisaarland.cs.se.selab.model.BaseType
import de.unisaarland.cs.se.selab.model.Emergency
import de.unisaarland.cs.se.selab.model.EmergencyType
import de.unisaarland.cs.se.selab.model.Model
import de.unisaarland.cs.se.selab.util.Dijkstra
import de.unisaarland.cs.se.selab.util.Logger

class EmergencyDistribution {


    public fun execute(model: Model) {
        //get emergencies for current tick
        var currentEmergencies = model.tickToEmergencyId.get(0)

        //get graph from model
        var graph = model.graph


        for (e in currentEmergencies)
        {
            //iterate over emergencies and get nearest base using one of the dijkstra methods
            var nearestbaseId : Int = 0
            var loc = e.location

            //This seems very wrong,but I need to provide a basetype for getnearestbasetoEdge,so I will have multiple if statements for this purpose
            if(e.type = EmergencyType.CRIME)
            {
                nearestbaseId = Dijkstra.getnearestbasetoEdge(graph,loc, BaseType.POLICE_STATION)
            }
            if(e.type = EmergencyType.FIRE)
            {
                nearestbaseId = Dijkstra.getnearestbasetoEdge(graph,loc,BaseType.FIRE_STATION)
            }
            if(e.type = EmergencyType.ACCIDENT || e.type = EmergencyType.MEDICAL)
            {
                nearestbaseId =  Dijkstra.getnearestbasetoEdge(graph,loc,BaseType.HOSPITAL)
            }
            //assign the base Id to the emergency
            //e.baseId = nearestbaseId

            //assign the emergency Id to the base
            model.getBasebyId(nearestbaseId).addEmergency(e)


            //add emergency to active emergencies in the model
            model.addToAssignedEmergencies(nearestbaseId)

            Logger.logEmergencyAssigned(e.id,nearestbaseId)

        }



    }

}