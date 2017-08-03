package com.agilogy.classis.bind

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsProperties
import org.scalacheck.Arbitrary

import scala.language.higherKinds
import scala.reflect.runtime.universe.TypeTag

class BindLawsProperties[F[_]: Bind]
(implicit
 tt: TypeTag[F[Int]],
 eq: Equal[F[Int]],
 faa: Arbitrary[F[Int]],
 ifia: Arbitrary[Int => F[Int]],
 fiia: Arbitrary[F[Int=>Int]]) extends LawsProperties{

  override def properties: LawChecks = {
    val laws = Bind.laws[F]
    Seq(
      laws.functorComposite[Int,Int,Int],
      laws.functorIdentity[Int],
      laws.applyComposite[Int, Int, Int],
      laws.flatMapAssociative[Int, Int, Int],
      laws.bindFlatMapConsistentApply[Int, Int]
    )
  }

  override def typeName: String = tt.tpe.typeConstructor.toString
}

object BindLawsProperties{
  def apply[F[_]: Bind]
  (implicit
   tt: TypeTag[F[Int]],
   eq: Equal[F[Int]],
   faa: Arbitrary[F[Int]],
   ifia: Arbitrary[Int => F[Int]],
   fiia: Arbitrary[F[Int=>Int]]): BindLawsProperties[F] = new BindLawsProperties[F]()
}
