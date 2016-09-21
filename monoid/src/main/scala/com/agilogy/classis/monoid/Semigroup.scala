package com.agilogy.classis.monoid

import simulacrum._

import scala.language.implicitConversions

@typeclass trait Semigroup[T] extends Any with Serializable {

  @op("append") def append(x: T, y: T): T

}

