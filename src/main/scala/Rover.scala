/**
 * Created by gerardo.mendez on 4/20/16.
 */


// NOTE: Scala prefers immutable objects whenever possible. This class is immutable with mutable attributes,
// meaning that a Rover has a position and a direction that can change over time. We could go the other way
// and make it immutable, but the class should be named RoverLocation or something like that.


case class Rover(id: Int) {

  private var _position: Position = Position (0,0)
  private var _direction: Direction = Direction('N')

  // only getters are public
  def position = _position
  def direction = _direction

  def this(id: Int, x: Int, y: Int, d: Char) = {
    this(id)
    this._position = Position(x, y)
    this._direction = Direction(d)
  }

  def move(instructions: String) = {
    var x = _position.x
    var y = _position.y
    var d = _direction.number
    instructions.foreach {
      case 'L' => d -= 1 // Go left counterclockwise
      case 'R' => d += 1 // Go right clockwise
      case 'M' => Direction(d).number match {
        case 0 => y += 1
        case 1 => x += 1
        case 2 => y -= 1
        case 3 => x -= 1
      }
      case unknown => throw new IllegalArgumentException(s"Unrecognised instruction [$unknown]")
    }
    _position = Position(x, y)
    _direction = Direction(d)
  }
}