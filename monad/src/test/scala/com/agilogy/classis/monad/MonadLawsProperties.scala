package com.agilogy.classis.monad

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsProperties
import org.scalacheck.Arbitrary
import scala.reflect.runtime.universe.TypeTag

import scala.language.higherKinds

class MonadLawsProperties[F[_] : Monad]
(implicit
 tt: TypeTag[F[Int]],
 eq: Equal[F[Int]],
 faa: Arbitrary[F[Int]],
 fiia: Arbitrary[F[Int => Int]],
 ifia: Arbitrary[Int => F[Int]]) extends LawsProperties {

  override def properties: LawChecks = {
    val laws = Monad.laws[F]
    Seq(
      laws.functorComposite[Int, Int, Int],
      laws.functorIdentity[Int],
      laws.applyComposite[Int, Int, Int],
      laws.flatMapAssociative[Int, Int, Int],
      laws.bindFlatMapConsistentApply[Int, Int],
      laws.applicativeHomomorphism[Int, Int],
      laws.applicativeMap[Int, Int],
      laws.applicativePureIdentity[Int, Int],
      laws.applicativePureIdIdentity[Int]
    )
  }

  override def typeName: String = tt.tpe.typeConstructor.toString
}

object MonadLawsProperties{
  def apply[F[_] : Monad]
  (implicit
   tt: TypeTag[F[Int]],
   eq: Equal[F[Int]],
   faa: Arbitrary[F[Int]],
   fiia: Arbitrary[F[Int => Int]],
   ifia: Arbitrary[Int => F[Int]]): MonadLawsProperties[F] = new MonadLawsProperties[F]
}
