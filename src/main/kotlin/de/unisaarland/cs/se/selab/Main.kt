package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.config.DotParser
import de.unisaarland.cs.se.selab.config.JsonParser
import de.unisaarland.cs.se.selab.config.ValidatorManager
import de.unisaarland.cs.se.selab.util.Logger
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

/**
 * This is the entry point of the simulation.
 */
fun main(args: Array<String>) {
    // parse command line arguments.
    val parser = ArgParser("Saarvive and Thrive")
    val mapPath by parser.option(
        ArgType.String,
        description = "Path to the DOT file. (always required)"
    ).required()
    val assetsPath by parser.option(
        ArgType.String,
        description = "Path to the JSON file with assets. (always required)"
    ).required()
    val scenarioPath by parser.option(
        ArgType.String,
        description = "Path to the scenario JSON file with emergencies and events. (always required)"
    ).required()
    val ticks by parser.option(
        ArgType.Int,
        description = "Maximum allowed number of simulation ticks"
    )
    val outPath: String? by parser.option(
        ArgType.String,
        description = "Path to output file. Uses 'stdout' by default"
    )
    parser.parse(args)

    Logger.outputFile = outPath

    // validate the input files and create the control center.
    val dotParser = DotParser(mapPath)
    val jsonParse = JsonParser(assetsPath, scenarioPath)
    val validator = ValidatorManager()
    val controlCenter = validator.validate(dotParser, jsonParse, ticks)

    // run the simulation.
    if (controlCenter != null) {
        controlCenter.simulate()
    } else {
        error("controlCenter is null")
    }

    // throw UnsupportedOperationException()
}
