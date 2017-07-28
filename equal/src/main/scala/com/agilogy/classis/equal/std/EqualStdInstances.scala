package com.agilogy.classis.equal.std

import com.agilogy.classis.equal.Equal

trait EqualStdInstances {

  implicit val booleanEqual: Equal[Boolean] = Equal.fromJavaEquals[Boolean]

  implicit val byteEqual: Equal[Byte] = Equal.fromJavaEquals[Byte]
  implicit val shortEqual: Equal[Short] = Equal.fromJavaEquals[Short]
  implicit val intEqual: Equal[Int] = Equal.fromJavaEquals[Int]
  implicit val longEqual: Equal[Long] = Equal.fromJavaEquals[Long]
  implicit val floatEqual: Equal[Float] = Equal.fromJavaEquals[Float]
  implicit val doubleEqual: Equal[Double] = Equal.fromJavaEquals[Double]

  implicit val charEqual: Equal[Char] = Equal.fromJavaEquals[Char]
  implicit val stringEqual: Equal[String] = Equal.fromJavaEquals[String]

  implicit def optionEq[T](implicit tEqual:Equal[T]):Equal[Option[T]] = Equal.create{
    case (Some(x),Some(y)) => tEqual.equal(x,y)
    case (None,None) => true
    case _ => false
  }

  implicit def eitherEq[L,R](implicit lEqual:Equal[L], rEqual:Equal[R]):Equal[Either[L,R]] = Equal.create{
    case (Left(x),Left(y)) => lEqual.equal(x,y)
    case (Right(x),Right(y)) => rEqual.equal(x,y)
    case _ => false
  }

}

object EqualStdInstances extends EqualStdInstances
