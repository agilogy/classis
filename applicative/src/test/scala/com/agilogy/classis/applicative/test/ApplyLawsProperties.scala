package com.agilogy.classis.applicative.test

import com.agilogy.classis.applicative.Apply
import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsProperties
import org.scalacheck.Arbitrary

import scala.reflect.runtime.universe.TypeTag
import scala.language.higherKinds

class ApplyLawsProperties[F[_] : Apply, A: Arbitrary, B: Arbitrary, C: Arbitrary]
(implicit
 aba: Arbitrary[A => B],
 bca: Arbitrary[B => C],
 faa: Arbitrary[F[A]],
 faeq: Equal[F[A]],
 fceq: Equal[F[C]],
 faba: Arbitrary[F[A => B]],
 fbc: Arbitrary[F[B => C]],
 tt: TypeTag[F[A]]
) extends LawsProperties {

  override def properties: LawChecks = {
    val laws = Apply.laws[F]
    Seq(
      law2Property1(laws.functorIdentity[A]),
      law2Property3(laws.functorComposite[A, B, C]),
      law2Property3(laws.applyComposite[A, B, C])
    )
  }

  override def typeName: String = tt.tpe.typeConstructor.toString

}

object ApplyLawsProperties{

  def apply[F[_] : Apply, A: Arbitrary, B: Arbitrary, C: Arbitrary]
  (implicit
   aba: Arbitrary[A => B],
   bca: Arbitrary[B => C],
   faa: Arbitrary[F[A]],
   faeq: Equal[F[A]],
   fceq: Equal[F[C]],
   faba: Arbitrary[F[A => B]],
   fbc: Arbitrary[F[B => C]],
   tt: TypeTag[F[A]]
  ): ApplyLawsProperties[F, A, B, C] = new ApplyLawsProperties[F,A,B,C]
}
