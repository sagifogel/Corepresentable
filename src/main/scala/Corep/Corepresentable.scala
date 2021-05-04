package Corep

import cats.arrow.Profunctor

import scala.annotation.implicitNotFound

@implicitNotFound("Could not find an instance of Corepresentable[${P}]")
trait Corepresentable[P[_, _], F[_]] extends Cosieve[P, F] with Costrong[P] {
  def P: Profunctor[P]

  type Corepresentation

  def cotabulate[A, B](f: Corepresentation => B): P[A, B]
}

abstract class CorepresentableInstances {
  implicit def corepresentableCostar[P[_, _], F[_]](implicit ev: Profunctor[Costar[F, *, *]]): Corepresentable.Aux[Costar[F, *, *], F] =
    new Corepresentable[Costar[F, *, *], F] {
      override def P: Profunctor[Costar[F, *, *]] = ev
      override type Corepresentation = F
      override def cotabulate[A, B](f: Corepresentation => B): Costar[F, A, B] = ???

      override def cosieve[A, B](pab: Costar[F, A, B])(fa: F[A]): B = ???

      override def dimap[A, B, C, D](fab: Costar[F, A, B])(f: C => A)(g: B => D): Costar[F, C, D] = ???

      override def map[A, B](fa: F[A])(f: A => B): F[B] = ???
    }
}

object Corepresentable extends CorepresentableInstances {
  type Aux[P[_, _], F[_]] = Corepresentable[P, F] { type Corepresentation = F }

  /** summon an instance of [[Corepresentable]] for `P` */
  @inline def apply[P[_, _], F[_]](implicit ev: Corepresentable[P, F]): Corepresentable.Aux[P, ev.Corepresentation] = ev
}
