package Corep

import cats.Functor
import cats.arrow.Profunctor

import scala.annotation.implicitNotFound

@implicitNotFound("Could not find an instance of Cosieve[${P}, ${F}]")
trait Cosieve[P[_, _], F[_]] extends Profunctor[P] with Functor[F] {
  def cosieve[A, B](pab: P[A, B])(fa: F[A]): B
}

object Cosieve {
  /** summon an instance of [[Cosieve]] for `P` and  `F` */
  @inline def apply[P[_, _], F[_]](implicit ev: Cosieve[P, F]): Cosieve[P, F] = ev
}
