package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.config.DotParser
import de.unisaarland.cs.se.selab.config.JsonParser
import de.unisaarland.cs.se.selab.config.ValidatorManager
import de.unisaarland.cs.se.selab.util.Logger
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.io.PrintWriter

/**
 * This is the entry point of the simulation.
 */
fun main(args: Array<String>) {
    // parse command line arguments.
    val parser = ArgParser("Saarvive and Thrive")
    PrintWriter(System.out).println(args)
    val map by parser.option(
        ArgType.String,
        description = "Path to the DOT file."
    ).required()
    val assets by parser.option(
        ArgType.String,
        description = "Path to the JSON file with assets."
    ).required()
    val scenario by parser.option(
        ArgType.String,
        description = "Path to the scenario JSON file with emergencies and events."
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
    val dotParser = DotParser(map)
    val jsonParse = JsonParser(assets, scenario)
    val validator = ValidatorManager()
    val controlCenter = validator.validate(dotParser, jsonParse, ticks)

    // run the simulation.
    if (controlCenter != null) {
        controlCenter.simulate()
    } else {
        return // controlCenter null
    }

    // throw UnsupportedOperationException()
}
