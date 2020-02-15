package goblins

case class Place(
    key: Int,
    name: String,
    servesFood: Boolean = false
)

object Place {
  val Count = 10

  val All: Array[Place] = Array(
    Place(0, "Pub", true),
    Place(1, "Mess", true),
    Place(2, "Grotto"),
    Place(3, "Hall"),
    Place(4, "Forge"),
    Place(5, "Barracks"),
    Place(6, "Bazaar"),
    Place(7, "Funhouse"),
    Place(8, "Lab"),
    Place(9, "School")
  )

  val WithFood: Array[Place] = All.filter(_.servesFood)
}
