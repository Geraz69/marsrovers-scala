import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by gerardo.mendez on 4/20/16.
 */
class RoverSpec extends FlatSpec with Matchers {

  "A Rover" should "move given individual instructions" in {
    val rover = new Rover(1,0,0,'N')

    rover.move("R")
    rover.direction should be (Direction('E'))
    rover.position should be (Position(0,0))

    rover.move("L")
    rover.direction should be (Direction('N'))
    rover.position should be (Position(0,0))

    rover.move("L")
    rover.direction should be (Direction('W'))
    rover.position should be (Position(0,0))

    rover.move("M")
    rover.direction should be (Direction('W'))
    rover.position should be (Position(-1,0))
  }


  it should "move given compounded instructions" in {
    val rover1 = new Rover(1,0,0,'N')
    val rover2 = new Rover(2,5,5,'W')

    rover1.move("MRMRMLMLM")
    rover1.direction should be (Direction('N'))
    rover1.position should be (Position(2,1))

    rover2.move("MMMMMLLLLLLRMMMMM")
    rover2.direction should be (Direction('S'))
    rover2.position should be (Position(0,0))
  }

  it should "fail if a unrecognized instruction is provided" in {
    val rover = new Rover(1,0,0,'N')
    a [IllegalArgumentException] should be thrownBy {
      rover.move("N")
    }
    a [IllegalArgumentException] should be thrownBy {
      rover.move("LRLr") // is not case sensitive
    }
    a [IllegalArgumentException] should be thrownBy {
      rover.move("5")
    }
  }
}


