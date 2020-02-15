package goblins

import goblins.Color._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GoblinTest extends AnyFlatSpec with Matchers {

  "preferredPlace" should "take into account memories" in {
    val green = Goblin("Sinople", Green)


    val place0 = Place.All(0)
    val place5 = Place.All(5)
    Place.All.foreach(green.addMemory(_, -5))
    green.addMemory(place0, 1)
    green.addMemory(place5, 15)

    green.preferredPlace() should be (place5)
    green.satiety = -100
    green.preferredPlace() should be (place0)
  }

  println(Goblin.all(Color.Green).map(_.fullName))
}
