package goblins

import goblins.Color._
import goblins.utils.RandomImplicits._

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
      g.fadeMemories()
      occupations(g.preferredPlace().key).add(g)
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
    val redLine = occupations.view.map(_.count(_.color == Red)).map(c => f"Red:  $c%3d").mkString(" | ")
    val blueLine = occupations.view.map(_.count(_.color == Blue)).map(c => f"Blue: $c%3d").mkString(" | ")
    val greenLine = occupations.view.map(_.count(_.color == Green)).map(c => f"Green:$c%3d").mkString(" | ")
    s""" ---------- $state --------------------
       |$headers
       |$redLine
       |$blueLine
       |$greenLine
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