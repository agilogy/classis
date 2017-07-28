package com.agilogy.classis.equal

import com.agilogy.classis.laws.{Law, Law1, Law2, Law3}

import Equal.syntax._

object EqualBasedLaws {

  def commutative[T, R: Equal](n: String, f: (T, T) => R): Law2[T] = Law.law2("commutative", n)((x, y) => f(x, y) === f(y, x))

  def associative[T: Equal](n: String, f: (T, T) => T): Law3[T] = Law.law3("associative", n)((x, y, z) => f(f(x, y), z) === f(x, f(y, z)))

  def leftIdentity[T: Equal](functionName: String, zeroName: String, f: (T, T) => T, id: T): Law1[T] = Law.law1("leftIdentity", functionName, zeroName)(x => f(id, x) === x)

  def rightIdentity[T: Equal](functionName: String, zeroName: String, f: (T, T) => T, id: T): Law1[T] = Law.law1("rightdentity", functionName, zeroName)(x => f(x, id) === x)

}
