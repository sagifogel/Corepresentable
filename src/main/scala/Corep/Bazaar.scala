package Corep

import cats.Applicative

trait Bazaar[P[_, _], A, B, S, T] {
  def runBazaar: RunBazaar[P, A, B, S, T]
}

trait RunBazaar[P[_, _], A, B, S, T] {
  def apply[F[_]](pafb: P[A, F[B]])(s: S)(implicit ev: Applicative[F]): F[T]
}

object Bazaar {
  implicit final def applicativeBazaar[C, D]: Applicative[Bazaar[* => *, C, D, Unit, *]] = new Applicative[Bazaar[* => *, C, D, Unit, *]] {
    override def pure[A](x: A): Bazaar[* => *, C, D, Unit, A] = new Bazaar[* => *, C, D, Unit, A] {
      override def runBazaar: RunBazaar[* => *, C, D, Unit, A] = new RunBazaar[* => *, C, D, Unit, A] {
        override def apply[F[_]](pafb: C => F[D])(s: Unit)(implicit ev: Applicative[F]): F[A] = ev.pure(x)
      }
    }

    override def ap[A, B](bazaarff: Bazaar[* => *, C, D, Unit, A => B])(bazaarfa: Bazaar[* => *, C, D, Unit, A]): Bazaar[* => *, C, D, Unit, B] =
      new Bazaar[* => *, C, D, Unit, B] {
        override def runBazaar: RunBazaar[* => *, C, D, Unit, B] = new RunBazaar[* => *, C, D, Unit, B] {
          override def apply[F[_]](pafb: C => F[D])(s: Unit)(implicit ev: Applicative[F]): F[B] = {
            val ff = bazaarff.runBazaar.apply(pafb)(())
            val fa = bazaarfa.runBazaar.apply(pafb)(())
            ev.ap(ff)(fa)
          }
        }
      }
  }
}