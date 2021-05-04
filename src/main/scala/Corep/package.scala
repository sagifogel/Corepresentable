import cats.data.Cokleisli

package object Corep {
  type Costar[F[_], A, B] = Cokleisli[F, A, B]
}
