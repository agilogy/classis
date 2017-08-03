package com.agilogy.classis.flatmap

trait FlatMapStdInstances {

  implicit lazy val optionFlatMap = new FlatMap[Option] {
    override def flatMap[A, B](fa: Option[A])(f: (A) => Option[B]): Option[B] = fa.flatMap(f)
  }

  implicit lazy val listFlatMap = new FlatMap[List] {
    override def flatMap[A, B](fa: List[A])(f: (A) => List[B]): List[B] = fa.flatMap(f)
  }

  implicit lazy val seqFlatMap = new FlatMap[Seq] {
    override def flatMap[A, B](fa: Seq[A])(f: (A) => Seq[B]): Seq[B] = fa.flatMap(f)
  }

  implicit lazy val setFlatMap = new FlatMap[Set] {
    override def flatMap[A, B](fa: Set[A])(f: (A) => Set[B]): Set[B] = fa.flatMap(f)
  }

  implicit def eitherFlatMap[L] = new FlatMap[Either[L,?]] {
    override def flatMap[A, B](fa: Either[L, A])(f: (A) => Either[L, B]): Either[L, B] = fa.flatMap(f)
  }

}
