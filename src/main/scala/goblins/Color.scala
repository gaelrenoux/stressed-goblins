package goblins

sealed abstract class Color(val key: Int) {
  override lazy val toString: String = getClass.getSimpleName.dropRight(1)
}

object Color {

  val Count = 3

  val All = Seq(Red, Blue, Green)

  case object Red extends Color(0)

  case object Blue extends Color(1)

  case object Green extends Color(2)

}
