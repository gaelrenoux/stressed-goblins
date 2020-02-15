package goblins.utils


import scala.math.Numeric.Implicits._

object CollectionMath {

  object ops {

    implicit class ArrayMathOps[A](val wrapped: Array[A]) {
      @inline final def mean(implicit numA: Numeric[A]): Double = wrapped.sum.toDouble / wrapped.length

      @inline final def median(implicit numA: Numeric[A]): A = {
        val half = math.floor(wrapped.length.toDouble / 2).toInt
        wrapped.toSeq.view.sorted.drop(half).head
      }
    }

    implicit class CollectionMathOps[A](val wrapped: Iterable[A]) {
      @inline final def mean(implicit numA: Numeric[A]): Double = wrapped.sum.toDouble / wrapped.size

      @inline final def median(implicit numA: Numeric[A]): A = {
        val half = math.floor(wrapped.size.toDouble / 2).toInt
        wrapped.toSeq.view.sorted.drop(half).head
      }
    }

  }

}
