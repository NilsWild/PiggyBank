package de.rwth.swc.piggybank.transfers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main application class for the PiggyBank Transfers service.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
class TransfersApplication

/**
 * The main method to run the Spring Boot application.
 *
 * @param args Command line arguments.
 */
fun main(args: Array<String>) {
    runApplication<TransfersApplication>(*args)
}