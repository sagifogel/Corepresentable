package Corep

import scala.annotation.implicitNotFound

import cats.arrow.Profunctor

/** The [[Corepresentable]] is a [[cats.arrow.Profunctor]] of P[_, _], if there exists a [[cats.Functor]] of F[_] such that P[A, B] c is isomorphic to F[B] => A.
 *
 * @tparam P [[cats.arrow.Profunctor]]
 */
@implicitNotFound("Could not find an instance of Corepresentable[${P}]")
trait Corepresentable[P[_, _]] extends Serializable {
  def P: Profunctor[P]

  type Corepresentation[_]

  def cotabulate[A, B](f: Corepresentation[A] => B): P[A, B]

  def cosieve[A, B](pab: P[A, B])(fa: Corepresentation[A]): B
}

object Corepresentable {
  type Aux[P[_, _], Corepresentation0[_]] = Corepresentable[P] { type Corepresentation[x] = Corepresentation0[x] }

  /** summon an instance of [[Corepresentable]] for `P` */
  @inline def apply[P[_, _]](implicit ev: Corepresentable[P]): Corepresentable.Aux[P, ev.Corepresentation] = ev
}

