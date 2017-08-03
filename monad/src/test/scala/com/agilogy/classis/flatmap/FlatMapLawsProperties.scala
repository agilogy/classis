package com.agilogy.classis.flatmap

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsProperties
import org.scalacheck.Arbitrary

import scala.language.higherKinds
import scala.reflect.runtime.universe.TypeTag

class FlatMapLawsProperties[F[_]: FlatMap]
(implicit
 tt: TypeTag[F[Int]],
 eq: Equal[F[Int]],
 faa: Arbitrary[F[Int]],
 ifia: Arbitrary[Int => F[Int]]) extends LawsProperties{

  override def properties: LawChecks = {
    val laws = FlatMap.laws[F]
    Seq(
      laws.flatMapAssociative[Int, Int, Int]
    )
  }

  override def typeName: String = tt.tpe.typeConstructor.toString
}

object FlatMapLawsProperties{
  def apply[F[_]: FlatMap]
  (implicit
   tt: TypeTag[F[Int]],
   eq: Equal[F[Int]],
   faa: Arbitrary[F[Int]],
   ifia: Arbitrary[Int => F[Int]]): FlatMapLawsProperties[F] = new FlatMapLawsProperties[F]()
}
