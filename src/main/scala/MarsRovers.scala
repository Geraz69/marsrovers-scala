/**
 * Created by gerardo.mendez on 4/13/16.
 */

import scala.io.Source
import language.postfixOps

// All errors are fatal
// Assuming we are dealing with real rovers the following scenarios cause termination of the program:
// - Rovers walk out of the bounds of the plateau
// - Rovers crash into each other when deployed
// - Instructions are incomplete or non existing

object MarsRovers extends App {
  try {
    // Load data from input file
    // NOTE: getLines() does not load all the file in memory, instead creates an Stream iterator object that
    // reads data line by line, and only progress
    val input: Iterator[String] = args match {
      case Array(file) => Source.fromFile(file).getLines
      case _ =>
        throw new IllegalArgumentException("Please specify a path to a file containing the data as the first argument")
    }

    // read first line of the file to know the size of the plateau
    // Adding 1 because the first line marks the upper right corner of the plateau and our counting is 0 based
    val List(width, height) = input.buffered.head.split(" ").map(_.toInt + 1).toList

    val mission = new Mission(width, height)

    // read the rest of the lines, each pair is the data for one rover
    val rovers: List[Rover] = input.sliding(2, 2).zipWithIndex.map {
      case (List(position: String, instructions: String), index: Int) =>
        val rover = position.split(" ").toList match {
          case List(x, y, d) => new Rover(index, x.toInt, y.toInt, d.charAt(0))
        }
        mission.deploy(rover, instructions)
        rover
    } toList

    rovers foreach { rover =>
      println(s"${rover.position.x} ${rover.position.y} ${rover.direction.letter}")
    }
  } catch {
    // If there is an exception we catch it and only show the message
    case e: RuntimeException => throw e;
  }
}






