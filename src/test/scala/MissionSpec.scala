import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by gerardo.mendez on 4/20/16.
 */

class MissionSpec extends FlatSpec with Matchers{

  "A Mission" should "fail if the size of the plateau us too small or not logical" in {
    a [IllegalArgumentException] should be thrownBy {
      new Mission(0,5)
    }
    a [IllegalArgumentException] should be thrownBy {
      new Mission(4,-1)
    }
  }

  it should "fail if a rover goes out of bounds" in {
    def rover = new Rover(1,0,0,'N')
    a [IllegalArgumentException] should be thrownBy {
      new Mission(2,2).deploy(rover,"LM")
    }
    a [IllegalArgumentException] should be thrownBy {
      new Mission(2,2).deploy(rover,"LLM")
    }
    a [IllegalArgumentException] should be thrownBy {
      new Mission(2,2).deploy(rover,"RMMM")
    }
    a [IllegalArgumentException] should be thrownBy {
      new Mission(2,2).deploy(rover,"MMM")
    }
  }

  it should "fail if two rovers crash" in {
    val mission = new Mission(2,2)
    val rover1 = new Rover(1,5,5,'W')
    val rover2 = new Rover(2,0,0,'W')
    mission.deploy(rover2,"") // empty instructions. Rover wont move
    a [IllegalArgumentException] should be thrownBy {
      mission.deploy(rover1,"MMMMMLLLLLLRMMMMM")
    }
  }

}