package goblins

import goblins.Color._
import goblins.utils.CollectionMath.ops._
import goblins.utils.RandomUtil.ops._

import scala.collection.mutable
import scala.util.Random

class State(
    val goblins: Array[Goblin],
    var occupations: Array[mutable.Set[Goblin]]
)(implicit val matrix: GoblinRelationsMatrix = GoblinRelationsMatrix.Default) {

  private var _iteration: Long = 0

  def iteration: Long = _iteration

  def advance(): Unit = {
    _iteration = _iteration + 1
    occupations.foreach(_.clear())
    goblins.foreach { g =>
      g.timePasses()
      occupations(g.preferredPlace()).add(g)
    }

    for {
      place <- Place.All
      goblinPresents = occupations(place.key)
      gob <- goblinPresents
      otherGob <- Random.pick(goblinPresents, 5)
    } {
      gob.meet(otherGob, place)
    }

    occupations.zip(Place.All).foreach {
      case (goblins, place) if place.servesFood => goblins.foreach(_.eat())
      case _ => ()
    }
  }

  private val headers = Place.All.map(p => f"${p.name}%-9s").mkString(" | ")

  def render(): String = {
    val state = f"State ${_iteration}%-5d"
    val redLine = occupations.view.map(_.count(Red)).map(c => f"Red:  $c%3d").mkString(" | ")
    val blueLine = occupations.view.map(_.count(Blue)).map(c => f"Blue: $c%3d").mkString(" | ")
    val greenLine = occupations.view.map(_.count(Green)).map(c => f"Green:$c%3d").mkString(" | ")
    val redHappiness = goblins.filter(Red).map(_.happiness)
    val blueHappiness = goblins.filter(Blue).map(_.happiness)
    val greenHappiness = goblins.filter(Green).map(_.happiness)

    def m(happiness: Array[Double]) = f"${happiness.mean}%3f1 / ${happiness.median}%3f1"

    s""" ---------- $state --------------------
        |$headers
        |$redLine
        |$blueLine
        |$greenLine
        |Mean/median happiness:  Red (${m(redHappiness)})  Blue (${m(blueHappiness)})  Green (${m(greenHappiness)})
        |""".stripMargin
  }

}

object State {
  def defaultRandom()(implicit matrix: GoblinRelationsMatrix = GoblinRelationsMatrix.Default): State = {
    val goblins = for {
      name <- Goblin.Names ++ Goblin.Names
      color <- Color.All
    } yield Goblin(name, color)
    new State(goblins, Array.fill(Place.Count)(mutable.Set[Goblin]()))
  }
}
