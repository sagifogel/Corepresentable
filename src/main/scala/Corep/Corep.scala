package Corep

trait Corep[P[_, _]] {
  type Corepresentation[_]
}

object Corep {
  type Aux[P[_, _]] = Corep[P] { type F[A] }

  @inline def apply[P[_, _], F[_]](implicit ev: Corep.Aux[P]): Corep.Aux[P] = ev
}
