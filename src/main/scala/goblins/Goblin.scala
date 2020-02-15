package goblins

import scala.util.Random

final case class Goblin(
    name: String,
    color: Color
) {

  import Ordering.Double.IeeeOrdering

  var happiness: Double = 0
  var satiety: Int = 0

  /** Tells if a place has good memories or not. Higher score means better memories. */
  private val memories: Array[Double] = Array.fill(Place.Count)(0)

  @inline def fullName = s"$color $name"

  @inline def addMemory(place: Place, happiness: Int): Unit = memories(place.key) += happiness

  @inline def timePasses() = {
    val _ = memories.mapInPlace(_ * 0.9)
    happiness = happiness * 0.9
    satiety -= 10
  }

  @inline def meet(that: Goblin, place: Place)(implicit matrix: GoblinRelationsMatrix): Unit = {
    val bonus = matrix(that.color)(color)
    this.happiness += bonus
    addMemory(place, bonus)
  }

  def eat() = {
    satiety += 50
    if (satiety <= 0) satiety = 0
  }

  def preferredPlace(): Int =
    if (satiety <= -100) Place.WithFood.maxBy(p => memories(p.key)).key
    else {
      val minimum = math.min(memories.max - 1, -1)
      val goodPlaces = memories.view.map(_ - minimum).zipWithIndex.filter(_._1 >= 0).toSet
      val total = goodPlaces.view.map(_._1).sum
      var pick = total * Random.nextDouble()
      goodPlaces.find {
        case (score, _) => if (score > pick) true else {
          pick -= score
          false
        }
      }.get._2
    }

}

object Goblin {
  val Names = Array(
    "Akka", "Bok", "Crud", "Dun", "Egg", "Finn", "Grok", "Heck", "Ick", "Jock",
    "Ken", "Lol", "Mock", "Non", "Ook", "Pawn", "Qin", "Ron", "Sock", "Tedd",
    "Usul", "Var", "Why", "Xor", "Yawn", "Zed"
  )

  def all(color: Color): Array[Goblin] = Names.map(Goblin(_, color))
}
