package com.agilogy.classis

trait Semigroup[T] extends Any with Serializable {

  def append(x: T, y: T): T

}

