package com.agilogy.classis.functor.std

import scala.collection.generic.CanBuildFrom

import scala.language.{higherKinds,existentials}

// Directly from ScalaZ CanBuildAnySelf
trait CanBuildSelf[CC[_]] {
  import CanBuildSelf._

  def builder[A, B]: CanBuildFromHigherKinds[CC, A, B]

//  final def apply[A, B](): mutable.Builder[B, CC[B]] = builder[A, B].apply

//  final def apply[A, B](f: CC[A]): mutable.Builder[B, CC[B]] = builder[A, B].apply(f)
}

object CanBuildSelf {

  type CanBuildFromHigherKinds[CC[_], A, B] = CanBuildFrom[CC[A], B, CC[B]]

  type CanBuildSelfExistential[CC[_]] = CanBuildFrom[CC[A], B, CC[B]] forSome {type A; type B}

  implicit def GenericCanBuildSelf[CC[_] : CanBuildSelfExistential]: CanBuildSelf[CC] = new CanBuildSelf[CC] {
    def builder[A, B]: CanBuildFromHigherKinds[CC, A, B] = implicitly[CanBuildSelfExistential[CC]].asInstanceOf[CanBuildFromHigherKinds[CC, A, B]]
  }
}

