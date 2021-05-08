package Corep

import cats.Functor
import cats.arrow.Profunctor

import scala.annotation.implicitNotFound

@implicitNotFound("Could not find an instance of Corepresentable[${P}]")
trait Corepresentable[P[_, _]] extends Cosieve[P, Corep.Aux[P]#Corepresentation] with Costrong[P] {
  def P: Profunctor[P]

  def cotabulate[A, B](f: Corep.Aux[P]#Corepresentation[A] => B): P[A, B]
}

abstract class CorepresentableInstances {
  implicit def corepresentableCostar[P[_, _], F[_] : Functor](implicit ev: Profunctor[Costar[F, *, *]]): Corepresentable[Costar[F, *, *]] = new Corepresentable[Costar[F, *, *]] {
    override def P: Profunctor[Costar[F, *, *]] = ???

    override def cotabulate[A, B](f: Corep.Aux[Costar[F, *, *]]#Corepresentation[A] => B): Costar[F, A, B] = ???

    override def cosieve[A, B](pab: Costar[F, A, B])(fa: Corep.Aux[Costar[F, *, *]]#Corepresentation[A]): B = ???

    override def dimap[A, B, C, D](fab: Costar[F, A, B])(f: C => A)(g: B => D): Costar[F, C, D] = fab.dimap(f)(g)

    override def map[A, B](fa: Corep.Aux[Costar[F, *, *]]#Corepresentation[A])(f: A => B): Corep.Aux[Costar[F, *, *]]#Corepresentation[B] = ???
  }
}

object Corepresentable extends CorepresentableInstances {
  /** summon an instance of [[Corepresentable]] for `P` */
  @inline def apply[P[_, _]](implicit ev: Corepresentable[P]): Corepresentable[P] = ev
}
