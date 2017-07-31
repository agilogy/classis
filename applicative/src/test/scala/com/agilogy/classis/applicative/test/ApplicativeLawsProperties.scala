package com.agilogy.classis.applicative.test

import com.agilogy.classis.applicative.Applicative
import com.agilogy.classis.equal.Equal
import org.scalacheck.Arbitrary
import scala.reflect.runtime.universe.TypeTag

import scala.language.higherKinds

class ApplicativeLawsProperties[F[_] : Applicative, A: Arbitrary, B: Arbitrary, C: Arbitrary]
(implicit
 aba: Arbitrary[A => B],
 bca: Arbitrary[B => C],
 faa: Arbitrary[F[A]],
 faeq: Equal[F[A]],
 fbeq: Equal[F[B]],
 faba: Arbitrary[F[A => B]],
 fbc: Arbitrary[F[B => C]],
 tt: TypeTag[F[A]],
 eq: Equal[F[C]]
)
  extends ApplyLawsProperties[F, A, B, C] {

  override def properties: LawChecks = super.properties ++ {
    val laws = Applicative.laws[F]
    Seq(
      law2Property1(laws.applicativePureIdIdentity[A]),
      law2Property2(laws.applicativeHomomorphism[A,B]),
      law2Property2(laws.applicativePureIdentity[A,B]),
      law2Property2(laws.applicativeMap[A,B])
    )
  }

}

object ApplicativeLawsProperties {
  def apply[F[_] : Applicative, A: Arbitrary, B: Arbitrary, C: Arbitrary]
  (implicit
   aba: Arbitrary[A => B],
   bca: Arbitrary[B => C],
   faa: Arbitrary[F[A]],
   faeq: Equal[F[A]],
   fbeq: Equal[F[B]],
   faba: Arbitrary[F[A => B]],
   fbc: Arbitrary[F[B => C]],
   tt: TypeTag[F[A]],
   eq: Equal[F[C]]
  ): ApplicativeLawsProperties[F, A, B, C] = new ApplicativeLawsProperties[F, A, B, C]
}