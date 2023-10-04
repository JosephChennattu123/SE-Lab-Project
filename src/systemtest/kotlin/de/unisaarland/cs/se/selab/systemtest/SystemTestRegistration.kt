package de.unisaarland.cs.se.selab.systemtest

// import de.unisaarland.cs.se.selab.systemtest.basictests.FireEmergencyTest
import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleTest
import de.unisaarland.cs.se.selab.systemtest.basictests.FireEmergencyTest
import de.unisaarland.cs.se.selab.systemtest.basictests.OneEmergency
import de.unisaarland.cs.se.selab.systemtest.basictests.SimpleTest
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager
import de.unisaarland.cs.se.selab.systemtest.validation.BaseWithNoVertex
import de.unisaarland.cs.se.selab.systemtest.validation.EmergencyDoctorWithLadder
import de.unisaarland.cs.se.selab.systemtest.validation.EmergencyOnNonExistingRoad
import de.unisaarland.cs.se.selab.systemtest.validation.EventOnNonExistingRoad
import de.unisaarland.cs.se.selab.systemtest.validation.FireStationWithAmbulance
import de.unisaarland.cs.se.selab.systemtest.validation.FireTruckWithAmbulance
import de.unisaarland.cs.se.selab.systemtest.validation.HospitalWithFireTruck
import de.unisaarland.cs.se.selab.systemtest.validation.MissingDepartmentFireStation
import de.unisaarland.cs.se.selab.systemtest.validation.MissingDepartmentHospital
import de.unisaarland.cs.se.selab.systemtest.validation.MissingDepartmentPolice
import de.unisaarland.cs.se.selab.systemtest.validation.MixedBases
import de.unisaarland.cs.se.selab.systemtest.validation.MixedEvents
import de.unisaarland.cs.se.selab.systemtest.validation.MixedVehicles
import de.unisaarland.cs.se.selab.systemtest.validation.PoliceCarWithWater
import de.unisaarland.cs.se.selab.systemtest.validation.PoliceNoVehicles
import de.unisaarland.cs.se.selab.systemtest.validation.PoliceStationWithAmbulance
import de.unisaarland.cs.se.selab.systemtest.validation.TwoBasesOneVertex
import de.unisaarland.cs.se.selab.systemtest.validation.VehicleEventNoVehicle
import de.unisaarland.cs.se.selab.systemtest.validation.VehiclesWithIllegalValues

object SystemTestRegistration {
    fun registerSystemTests(manager: SystemTestManager) {
        manager
    }

    /**
     * First Section: Test correctness checking
     * Broken/unfinished tests go here
     *
     * Description:
     * For system tests for which you want to check the simulation behaviour of our reference implementation
     * and to fix your system tests before including them in different parts of the pipeline
     *
     * Runs: Every 2 hours
     * If all tests of this section pass: -
     */
    fun registerSystemTestsReferenceImpl(manager: SystemTestManager) {
        manager.registerTest(ExampleTest())
        manager.registerTest(OneEmergency())
        manager.registerTest(FireEmergencyTest())
        manager.registerTest(SimpleTest())
        manager.registerTest(BaseWithNoVertex())
        manager.registerTest(EmergencyDoctorWithLadder())
        manager.registerTest(FireStationWithAmbulance())
        manager.registerTest(FireTruckWithAmbulance())
        manager.registerTest(HospitalWithFireTruck())
        manager.registerTest(MissingDepartmentPolice())
        manager.registerTest(MissingDepartmentFireStation())
        manager.registerTest(MissingDepartmentHospital())
        manager.registerTest(PoliceNoVehicles())
        manager.registerTest(PoliceCarWithWater())
        manager.registerTest(PoliceStationWithAmbulance())
        manager.registerTest(TwoBasesOneVertex())
        manager.registerTest(VehiclesWithIllegalValues())
        manager.registerTest(MixedVehicles())
        manager.registerTest(MixedBases())
        manager.registerTest(MixedEvents())
        manager.registerTest(EmergencyOnNonExistingRoad())
        manager.registerTest(EventOnNonExistingRoad())
        manager.registerTest(VehicleEventNoVehicle())
        manager.registerTest(SimpleTest())
    }

    /**
     * Second Section: Validation system tests
     * finished validation tests go here
     *
     * Description:
     * This second part is only concerned with the validation process of the configuration files, and will
     * therefore only result in possibly incorrect log messages before the 'Simulation start' message
     * (i.e. the simulation behaves exactly as in the reference implementation).
     *
     * Runs: Every 8 hours
     * If all tests of this section pass: Runs them on the validation-phase mutants
     */
    fun registerSystemTestsMutantValidation(manager: SystemTestManager) {
        manager
//        manager.registerTest(ExampleTest())
//        manager.registerTest(BaseWithNoVertex())
//        manager.registerTest(EmergencyDoctorWithLadder())
//        manager.registerTest(FireStationWithAmbulance())
//        manager.registerTest(FireTruckWithAmbulance())
//        manager.registerTest(HospitalWithFireTruck())
//        manager.registerTest(MissingDepartmentPolice())
//        manager.registerTest(MissingDepartmentFireStation())
//        manager.registerTest(MissingDepartmentHospital())
//        manager.registerTest(PoliceNoVehicles())
//        manager.registerTest(PoliceCarWithWater())
//        manager.registerTest(PoliceStationWithAmbulance())
//        manager.registerTest(TwoBasesOneVertex())
//        manager.registerTest(VehiclesWithIllegalValues())
//        manager.registerTest(MixedVehicles())
//        manager.registerTest(MixedBases())
//        manager.registerTest(MixedEvents())
//        manager.registerTest(EmergencyOnNonExistingRoad())
//        manager.registerTest(EventOnNonExistingRoad())
//        manager.registerTest(VehicleEventNoVehicle())
    }

    /**
     * Third Section: Simulation system Tests
     * finished simulation tests go here
     *
     * Description:
     * The third part is only concerned with the simulation process itself, and thus will only result in the wrong
     * log messages after the 'Simulation start' message (i.e. the validation process behaves exactly as in the
     * reference implementation).
     *
     * Runs: Every 8 Hours
     * If all tests of this section pass: Runs them on the simulation-phase mutants
     */
    fun registerSystemTestsMutantSimulation(manager: SystemTestManager) {
        manager
    }
}
