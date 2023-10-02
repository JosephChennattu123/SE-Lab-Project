package de.unisaarland.cs.se.selab.systemtest

// import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleTest
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager

object SystemTestRegistration {
    fun registerSystemTests(manager: SystemTestManager) {
        // might not be used anymore
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
        manager
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
