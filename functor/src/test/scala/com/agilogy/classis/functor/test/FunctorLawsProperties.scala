package com.agilogy.classis.functor.test

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.functor.Functor
import com.agilogy.classis.laws.test.LawsProperties
import org.scalacheck.Arbitrary
import scala.reflect.runtime.universe.TypeTag

import scala.language.higherKinds

class FunctorLawsProperties[F[_] : Functor, A: Arbitrary, B: Arbitrary, C: Arbitrary]
(implicit
 faa: Arbitrary[F[A]],
 faeq: Equal[F[A]],
 fceq: Equal[F[C]],
 aba: Arbitrary[A => B],
 bca: Arbitrary[B => C],
 tt:TypeTag[F[A]])
  extends LawsProperties {
  override def properties: LawChecks = {
    val laws = Functor.laws[F]
    Seq(
      law2Property3(laws.compositeLaw[A, B, C]),
      law2Property1(laws.rightIdentityMapId[A])
    )
  }

  override def typeName: String = tt.tpe.typeConstructor.toString
}

object FunctorLawsProperties{
  def apply[F[_] : Functor, A: Arbitrary, B: Arbitrary, C: Arbitrary]
  (implicit ceq: Equal[F[C]],
   faa: Arbitrary[F[A]],
   faeq: Equal[F[A]],
   aba: Arbitrary[A => B],
   bca: Arbitrary[B => C],
   tt:TypeTag[F[A]]):FunctorLawsProperties[F,A,B,C] = new FunctorLawsProperties[F,A,B,C]
}
