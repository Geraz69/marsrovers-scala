import scala.collection.mutable.Set

/**
 * Created by gerardo.mendez on 4/20/16.
 */
class Mission(width: Int, height: Int) {

  require(width > 0, "Width should be greater than 0")
  require(height > 0, "Height should be greater than 0")

  // Mutable set holding rovers that are part of the mission
  val rovers: Set[Rover] = Set()

  def deploy(rover: Rover, instructions: String) {

    // positions of the already deployed rovers
    val currentPositions: Set[Position] = rovers.map(_.position)

    // Go through each one of the instructions
    instructions.split("").foreach { case (instruction: String) =>
      rover.move(instruction)
      // Fail if rover is out of the bounds
      require(rover.position.y >= 0 && rover.position.y < height && rover.position.x >= 0 && rover.position.x < width,
        s"Rover [${rover.id}] is out of bounds at ${rover.position}")
      require (!currentPositions.contains(rover.position),
        s"Rover [${rover.id}] just crashed with another rover at ${rover.position}")
    }
    rovers += rover
  }
}