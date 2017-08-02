package com.agilogy.classis.equal

import scala.annotation.tailrec
import scala.collection.TraversableLike

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

  def traversableEqual[A, T <: TraversableLike[A,T]](implicit ea:Equal[A]):Equal[T] = new Equal[T] {

    @tailrec
    override def equal(x: T, y: T): Boolean = (x.headOption, y.headOption) match {
      case (None, None) => true
      case (Some(xh),Some(yh)) => ea.equal(xh, yh) && equal(x.tail, y.tail)
      case _ => false
    }
  }

  implicit def listEqual[T: Equal]: Equal[List[T]] = traversableEqual[T,List[T]]
  implicit def seqEqual[T: Equal]: Equal[Seq[T]] = traversableEqual[T,Seq[T]]
  implicit def setEqual[T: Equal]: Equal[Set[T]] = traversableEqual[T,Set[T]]
  implicit def mapEqual[K: Equal, V: Equal]:Equal[Map[K,V]] = traversableEqual[(K,V),Map[K,V]]

}

object EqualStdInstances extends EqualStdInstances
