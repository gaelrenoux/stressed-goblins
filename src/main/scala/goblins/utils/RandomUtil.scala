package goblins.utils

import scala.util.Random


object RandomUtil {

  object ops {

    implicit class RandomOps(wrapped: Random) {
      /** Picks randomly an element in a set. */
      @inline
      final def pick[A](s: collection.Set[A]): A = {
        val i = wrapped.nextInt(s.size)
        s.toIndexedSeq(i)
      }

      /** Picks randomly several distinct elements in a Set */
      @inline
      final def pick[A](s: collection.Set[A], count: Int): Seq[A] = wrapped.shuffle(s.toSeq).take(count)
    }

  }

}
