import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by gerardo.mendez on 4/20/16.
 */

class DirectionSpec extends FlatSpec with Matchers {

  "A Direction" should "calculate its numeric value based on a given cardinal letter" in {
    Direction('N').number should be(0)
    Direction('E').number should be(1)
    Direction('S').number should be(2)
    Direction('W').number should be(3)
  }

  it should "also calculate its letter value based on a given number" in {
    Direction(0).letter should be('N')
    Direction(1).letter should be('E')
    Direction(2).letter should be('S')
    Direction(3).letter should be('W')
  }

  it should "calculate direction based on numbers outside the range, acting like a compass" in {
    Direction(-3).letter should be('E')
    Direction(6).letter should be('S')
    Direction(-5).letter should be('W')
    Direction(12).letter should be('N')
  }

  it should "fail if a letter outside the mapping is provided" in {
    a [IllegalArgumentException] should be thrownBy {
      Direction('X')
    }
    a [IllegalArgumentException] should be thrownBy {
      Direction('n') // is not case sensitive
    }
    a [IllegalArgumentException] should be thrownBy {
      Direction('6') // Doesnt work with digit characters
    }
  }
}