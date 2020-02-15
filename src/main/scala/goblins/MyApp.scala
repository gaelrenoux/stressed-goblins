package goblins

object MyApp extends App {

  val state = State.defaultRandom()
  //val state = State.monoPlace(Place.All(4), Place.All(5), Place.All(0)).reinforce(10)

  while (true) {
    state.advance()
    if (state.iteration % 100 == 0)
      println(state.render())
    //if (state.iteration == 100) System.exit(0)
  }

}
