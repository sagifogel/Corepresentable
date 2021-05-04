package Corep

import cats.arrow.Profunctor

import scala.annotation.implicitNotFound

@implicitNotFound("Could not find an instance of Costrong[${P}]")
trait Costrong[P[_, _]] extends Profunctor[P] {
  def unfirst[A, B, C](pab: P[(A, C), (B, C)]): P[A, B] =
    unsecond(dimap[(A, C), (B, C), (C, A), (C, B)](pab)(_.swap)(_.swap))

  def unsecond[A, B, C](pab: P[(C, A), (C, B)]): P[A, B] =
    unfirst(dimap[(C, A), (C, B), (A, C), (B, C)](pab)(_.swap)(_.swap))
}

object Costrong {
  /** summon an instance of [[Costrong]] for `P` */
  @inline def apply[P[_, _]](implicit ev: Costrong[P]): Costrong[P] = ev
}
