package goblins

object MyApp extends App {

  val state = State.defaultRandom()
  while (true) {
    state.advance()
    if (state.iteration % 10 == 0) println(state.render())
  }

}
