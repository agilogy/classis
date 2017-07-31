package com.agilogy.classis.applicative.std

import com.agilogy.classis.applicative.{Applicative, Apply, Pure}

import scala.language.higherKinds

trait ApplicativeStdInstancesLow extends ApplyStdInstances with PureStdInstances{

  def applicativeInstance[F[_]](implicit p:Pure[F],a:Apply[F]):Applicative[F] = new Applicative[F] {
    override def apply[A, B](fab: F[(A) => B])(fa: F[A]): F[B] = a.apply(fab)(fa)
    override def pure[A](a: => A): F[A] = p.pure(a)
  }

}

trait ApplicativeStdInstances extends ApplicativeStdInstancesLow{

  implicit lazy val optionApplicativeInstance:Applicative[Option] = applicativeInstance[Option](optionPureInstance,optionApplyInstance)
  implicit def eitherApplicativeInstance[L]:Applicative[Either[L,?]] = applicativeInstance(eitherPureInstance[L],eitherApplyInstance[L])
  implicit lazy val listApplicativeInstance: Applicative[List] = applicativeInstance[List](listPureInstance,listApplyInstance)
  implicit lazy val seqApplicativeInstance: Applicative[Seq] = applicativeInstance[Seq](seqPureInstance,seqApplyInstance)

}

object ApplicativeStdInstances extends ApplicativeStdInstances