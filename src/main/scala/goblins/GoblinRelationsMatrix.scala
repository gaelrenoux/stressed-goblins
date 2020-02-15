package goblins

final class GoblinRelationsMatrix(
    setup: ((Color, Color), Int)*
) {

  private val setupAsMap: Map[(Int, Int), Int] = setup.map {
    case ((src, vic), stress) => (src.key, vic.key) -> stress
  }.toMap

  /** First index is the color of the source, second index is the color of the target. */
  private val wrapped: Array[Array[Int]] = Array.tabulate(Color.Count, Color.Count) { (src, target) => setupAsMap((src, target)) }

  @inline def apply(src: Color)(vic: Color): Int = wrapped(src.key)(vic.key)

}

object GoblinRelationsMatrix {

  import Color._

  val Default = new GoblinRelationsMatrix(
    (Red, Red) -> 2,
    (Red, Blue) -> 0,
    (Red, Green) -> -10,
    (Blue, Red) -> 0,
    (Blue, Blue) -> 2,
    (Blue, Green) -> -10,
    (Green, Red) -> -2,
    (Green, Blue) -> -2,
    (Green, Green) -> 2,
  )
}
