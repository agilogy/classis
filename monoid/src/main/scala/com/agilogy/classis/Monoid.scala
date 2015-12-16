package com.agilogy.classis

trait Monoid[T] extends Semigroup[T] {

  def zero:T

}
