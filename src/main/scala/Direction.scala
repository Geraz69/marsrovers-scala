/**
 * Created by gerardo.mendez on 4/20/16.
 */

case class Direction(letter: Char) {
  import Direction._

  require(VALID_DIRECTIONS.indexOf(letter) != -1, s"Provided symbol [$letter] is not a valid direction.")

  def number = VALID_DIRECTIONS.indexOf(letter)
}

object Direction {
  // Constant holding the possible values for the direction and its numeric values based on index
  // N -> 0, E -> 1, S -> 2, W -> 3
  val VALID_DIRECTIONS = "NESW"

  // Factory method to get a Direction from a numeric value.
  // We are representing directions both as letters and numbers. To rotate any direction to the right
  // we just take the numeric value of the direction and add 1 and subtract 1 if you want to rotate it
  // to the right. Subtracting or adding larger numbers means that its being rotated multiple times.
  // This method converts any numeric direction rotated any number of times into a proper Direction object.
  def apply(number: Int): Direction = {
    val index: Int = VALID_DIRECTIONS.size + (number % VALID_DIRECTIONS.size)
    this(VALID_DIRECTIONS.charAt(index % VALID_DIRECTIONS.size))
  }
  // NOTE: apply is a feature of scala language, it might be seen as the nameless method since it can
  // be invoked from the outside with just the parenthesis, in this case like Direction(-3)
  // Companion objects usually define them as helper or factory methods
}

