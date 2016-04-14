/**
 * Created by gerardo.mendez on 4/13/16.
 */

import scala.io.Source
import scala.collection.mutable.Set
import language.postfixOps

object MarsRovers extends App {
  // All errors are fatal
  // Assuming we are dealing with real rovers the following scenarios cause termination of the program:
  // - Rovers walk out of the bounds of the plateau
  // - Rovers crash into each other
  // - Instructions are incomplete or non existing

  // Load data from input file
  val input = args match {
    case Array(file) => Source.fromFile(file).getLines
    case _ =>
      throw new IllegalArgumentException("Please especify a path to a file containing the data as the first argument")
  }

  // read first line of the file to know the size of the plateau
  val List(width, height) = input.buffered.head.split(" ").map(_.toInt).toList

  // read the rest of the lines, each pair is the data for one rover
  val initialPositions: List[(Position, String)] = input.sliding(2, 2).map {
    case Seq(position: String, instructions: String) =>
      val initialPosition = position.split(" ").toList match {
        case List(x, y, d) => Position(x.toInt, y.toInt, d.charAt(0))
      }
      (initialPosition, instructions)
  } toList

  // Pass the variables to the main
  val plateau = new Plateau(width, height, initialPositions)

  plateau.finalPositions foreach { p =>
    println(s"${p.x} ${p.y} ${p.d.letter}")
  }

}

object Direction {
  val mapping = "NESW"

  def apply(number: Int): Direction = {
    val index: Int = mapping.size + (number % mapping.size)
    // Applying modulo twice for when the number is negative
    Direction(mapping.charAt(index % mapping.size), index % mapping.size)
  }

  def apply(letter: Char): Direction = {
    if (mapping.contains(letter)) {
      Direction(letter, mapping.indexOf(letter))
    } else {
      throw new NoSuchElementException("The specified direction doesn't exists")
    }
  }
}

case class Direction(letter: Char, number: Int)



object Position {
  def apply(x: Int, y: Int, d: Char): Position = {
    Position(x, y, Direction(d))
  }
  def apply(x: Int, y: Int, d: Int): Position = {
    Position(x, y, Direction(d))
  }
  def apply(previous: Position, change: Char): Position = {
    // NOTE: we probably can take a string of changes, but then collition validations become trickier
    change match {
      case 'L' => Position(previous.x, previous.y, Direction.apply(previous.d.number - 1)) // Go left counterclockwise
      case 'R' => Position(previous.x, previous.y, Direction.apply(previous.d.number + 1)) // Go right clockwise
      case 'M' => previous.d.number match {
        //Module will correct any overflow in direction
        case 0 => Position(previous.x, previous.y + 1, previous.d)
        case 1 => Position(previous.x + 1, previous.y, previous.d)
        case 2 => Position(previous.x, previous.y - 1, previous.d)
        case 3 => Position(previous.x - 1, previous.y, previous.d)
      }
      case _ => throw new NoSuchElementException("Unrecognised instruction")
    }
  }
}

case class Position(x: Int, y: Int, d: Direction)



class Plateau(width: Int, height: Int, initialPositions: List[(Position, String)],
              trackCollisions: Boolean = true, trackBoundaries: Boolean = true) {
  require(width > 0, "Width should be greater than 0")
  require(height > 0, "Height should be greater than 0")

  // create a mutable set to hold the positions of the rovers through the whole process
  val roverState: Set[Position] = Set() ++= initialPositions.map(_._1)

  // Take each of the initial positions and calculate the final one based on the instructions
  val finalPositions: List[Position] = initialPositions.map { case (initialPosition, instructions: String) =>

    // Take the initial position of each rover out of the current state, since only one rover is allowed to move at a time
    roverState -= initialPosition

    // Go through each one of the instructions
    val finalPosition = instructions.foldLeft(initialPosition) { case (p: Position, i: Char) =>
      val newPosition = Position(p, i)
      // Fail if rover is out of the plateau and we are tracking boundaries
      if ((newPosition.y < 0 || newPosition.y > height || newPosition.x < 0 || newPosition.x > width) && trackBoundaries) {
        throw new IllegalStateException("Rover is out of bounds")
      }
      // Fail if rover arrived at the same spot that other rover and we are tracking collisions
      if (roverState.contains(newPosition) && trackCollisions) {
        throw new IllegalStateException("Rover just crashed with another rover")
      }
      newPosition
    }
    roverState += finalPosition
    finalPosition
  }
}

