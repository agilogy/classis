package com.agilogy.classis.applicative

import com.agilogy.classis.apply.Apply

import scala.language.higherKinds

final class ApplicativeBuilder[M[_], A, B](a: M[A], b: M[B])(implicit ap: Apply[M]) {
  def apply[C](f: (A, B) => C): M[C] = ap.applyU(ap.map(a)(f.curried), b)

  def tupled: M[(A, B)] = apply(Tuple2.apply)

  def |@|[C](c: M[C]) = new ApplicativeBuilder3[C](c)

  final class ApplicativeBuilder3[C](c: M[C]) {
    def apply[D](f: (A, B, C) => D): M[D] = ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c)

    def tupled: M[(A, B, C)] = apply(Tuple3.apply)

    def |@|[D](d: M[D]) = new ApplicativeBuilder4[D](d)

    final class ApplicativeBuilder4[D](d: M[D]) {
      def apply[E](f: (A, B, C, D) => E): M[E] = ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d)

      def tupled: M[(A, B, C, D)] = apply(Tuple4.apply)

      def |@|[E](e: M[E]) = new ApplicativeBuilder5[E](e)

      final class ApplicativeBuilder5[E](e: M[E]) {
        def apply[F](f: (A, B, C, D, E) => F): M[F] = ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e)

        def tupled: M[(A, B, C, D, E)] = apply(Tuple5.apply)

        def |@|[F](f: M[F]) = new ApplicativeBuilder6[F](f)

        final class ApplicativeBuilder6[F](ff: M[F]) {
          def apply[G](f: (A, B, C, D, E, F) => G): M[G] = ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff)

          def tupled: M[(A, B, C, D, E, F)] = apply(Tuple6.apply)

          def |@|[G](g: M[G]) = new ApplicativeBuilder7[G](g)

          final class ApplicativeBuilder7[G](g: M[G]) {
            def apply[H](f: (A, B, C, D, E, F, G) => H): M[H] = ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g)

            def tupled: M[(A, B, C, D, E, F, G)] = apply(Tuple7.apply)

            def |@|[H](h: M[H]) = new ApplicativeBuilder8[H](h)

            final class ApplicativeBuilder8[H](h: M[H]) {
              def apply[I](f: (A, B, C, D, E, F, G, H) => I): M[I] = ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g), h)

              def tupled: M[(A, B, C, D, E, F, G, H)] = apply(Tuple8.apply)

              def |@|[I](i: M[I]) = new ApplicativeBuilder9[I](i)

              final class ApplicativeBuilder9[I](i: M[I]) {
                def apply[J](f: (A, B, C, D, E, F, G, H, I) => J): M[J] =
                  ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g), h), i)

                def tupled: M[(A, B, C, D, E, F, G, H, I)] = apply(Tuple9.apply)

                def |@|[J](j: M[J]) = new ApplicativeBuilder10[J](j)

                final class ApplicativeBuilder10[J](j: M[J]) {
                  def apply[K](f: (A, B, C, D, E, F, G, H, I, J) => K): M[K] =
                    ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g), h), i), j)

                  def tupled: M[(A, B, C, D, E, F, G, H, I, J)] = apply(Tuple10.apply)

                  def |@|[K](k: M[K]) = new ApplicativeBuilder11[K](k)

                  final class ApplicativeBuilder11[K](k: M[K]) {
                    def apply[L](f: (A, B, C, D, E, F, G, H, I, J, K) => L): M[L] =
                      ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g), h), i), j), k)

                    def tupled: M[(A, B, C, D, E, F, G, H, I, J, K)] = apply(Tuple11.apply)

                    def |@|[L](l: M[L]) = new ApplicativeBuilder12[L](l)

                    final class ApplicativeBuilder12[L](l: M[L]) {
                      def apply[MM](f: (A, B, C, D, E, F, G, H, I, J, K, L) => MM): M[MM] =
                        ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g), h), i), j), k), l)

                      def tupled: M[(A, B, C, D, E, F, G, H, I, J, K, L)] = apply(Tuple12.apply)

                      def |@|[N](n: M[N]) = new ApplicativeBuilder13[N](n)

                      final class ApplicativeBuilder13[N](n: M[N]) {
                        def apply[MM](f: (A, B, C, D, E, F, G, H, I, J, K, L, N) => MM): M[MM] =
                          ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.applyU(ap.map(a)(f.curried), b), c), d), e), ff), g), h), i), j), k), l), n)

                        def tupled: M[(A, B, C, D, E, F, G, H, I, J, K, L, N)] = apply(Tuple13.apply)
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

object ApplicativeBuilder{
  def apply[M[_], A, B](a: M[A], b: M[B])(implicit ap: Apply[M]): ApplicativeBuilder[M, A, B] = new ApplicativeBuilder(a,b)
}
