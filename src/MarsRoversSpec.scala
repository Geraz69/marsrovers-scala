import org.scalatest._

class MarsRoversSpec extends FlatSpec with Matchers {

  "A Direction" should "calculate its numeric value based on a given cardinal letter" in {
    Direction('N').number should be (0)
    Direction('E').number should be (1)
    Direction('S').number should be (2)
    Direction('W').number should be (3)
  }

  it should "also calculate its letter value based on a given number" in {
    Direction(0).letter should be ('N')
    Direction(1).letter should be ('E')
    Direction(2).letter should be ('S')
    Direction(3).letter should be ('W')
  }

  it should "calculate direction based on numbers outside the range, acting like a compass" in {
    Direction(-3).letter should be ('E')
    Direction(6).letter should be ('S')
    Direction(-5).letter should be ('W')
    Direction(12).letter should be ('N')
  }

  it should "throw NoSuchElementException if a letter outside the mapping is provided" in {
    a [NoSuchElementException] should be thrownBy {
      Direction('X')
    }
    a [NoSuchElementException] should be thrownBy {
      Direction('n') // is not case sensitive
    } 
    a [NoSuchElementException] should be thrownBy {
      Direction('6') // is not case sensitive
    } 
  }

  "A Position" should "be created from a previus position and a instruction" in {
    val initialPosition = Position(0,0,'N')
    Position(initialPosition, 'R') should be (Position(0,0,'E'))
    Position(initialPosition, 'L') should be (Position(0,0,'W'))
    Position(initialPosition, 'M') should be (Position(0,1,'N'))
    // Nested changes
    Position(Position(initialPosition, 'R'), 'M') should be (Position(1,0,'E'))
    Position(Position(initialPosition, 'M'), 'L') should be (Position(0,1,'W'))
    Position(Position(initialPosition, 'L'), 'R') should be (Position(0,0,'N'))
  }

  it should "throw NoSuchElementException if a unrecognized instruction is provided" in {
    val initialPosition = Position(0,0,'N')
    a [NoSuchElementException] should be thrownBy {
      Position(initialPosition, 'N')
    }
    a [NoSuchElementException] should be thrownBy {
      Position(initialPosition, 'r') // is not case sensitive
    }
    a [NoSuchElementException] should be thrownBy {
      Position(initialPosition, '5')
    } 
  }

  "A plateau" should "find the final positions of a set of rovers given the initial ones" in {
    val initialPositions = List(
      (Position(0,0,'N'), "MRMRMLMLM"),
      (Position(5,5,'W'), "MMMMMLLLLLLRMMMMM")
    )
    new Plateau(5,5,initialPositions).finalPositions should be (List(
      Position(2,1,'N'),
      Position(0,0,'S')
    ))
  }

  it should "fail if the size of the plateau us too small or not logical" in {
    a [IllegalArgumentException] should be thrownBy {
      new Plateau(0,5, List())
    }
    a [IllegalArgumentException] should be thrownBy {
      new Plateau(4,-1, List())
    }
  }

  it should "fail if a rover goes out of bounds" in {
    val initialPosition = Position(0,0,'N')
    a [IllegalStateException] should be thrownBy {
      new Plateau(2,2, List((initialPosition, "LM")))
    }
    a [IllegalStateException] should be thrownBy {
      new Plateau(2,2, List((initialPosition, "LLM")))
    }
    a [IllegalStateException] should be thrownBy {
      new Plateau(2,2, List((initialPosition, "RMMM")))
    }
    a [IllegalStateException] should be thrownBy {
      new Plateau(2,2, List((initialPosition, "MMM")))
    }
  }

  it should "fail if two rovers crash" in {
    val initialPositions = List(
      (Position(5,5,'W'), "MMMMMLLLLLLRMMMMM"),
      (Position(0,0,'S'), "")
    )
    a [IllegalStateException] should be thrownBy {
       new Plateau(5,5, initialPositions)
    }
  }

}
