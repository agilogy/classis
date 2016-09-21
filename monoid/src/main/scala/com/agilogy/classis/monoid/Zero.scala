package com.agilogy.classis.monoid

import simulacrum._

import scala.language.implicitConversions

@typeclass trait Zero[T] {

  def zero:T

}
