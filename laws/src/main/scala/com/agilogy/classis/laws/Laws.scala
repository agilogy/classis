package com.agilogy.classis.laws

object Laws {

  def reflexive[T](n: String, f: (T, T) => Boolean): Law1[T] = Law.law1("reflexive", n)(x => f(x, x))

  def transitive[T](n: String, f: (T, T) => Boolean): Law3[T] = Law.law3("transitive", n)((x, y, z) => !(f(x, y) && f(y, z)) || f(x, z))

  def total[T](n: String, f: (T, T) => Boolean): Law2[T] = Law.law2("total", n)((x, y) => f(x, y) || f(y, x))

}
