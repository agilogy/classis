package com.agilogy.classis.order

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws._
import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}

import scala.language.implicitConversions

trait Order[T] extends Equal[T] {

  def order(x: T, y: T): Ordering

  final override def equal(x: T, y: T): Boolean = order(x, y) == Eq

  final def lt(x: T, y: T): Boolean = order(x, y) == Lt

  final def lteq(x: T, y: T): Boolean = Set[Ordering](Lt, Eq).contains(order(x, y))

  final def gteq(x: T, y: T): Boolean = Set[Ordering](Eq, Gt).contains(order(x, y))

  final def gt(x: T, y: T): Boolean = order(x, y) == Gt

  final def max(x: T, y: T): T = if (order(x, y) == Lt) y else x

  final def min(x: T, y: T): T = if (order(x, y) == Lt) x else y

}

object Order extends ProductTypeClassCompanion[Order] with OrderStdInstances{

  def apply[T](implicit instance: Order[T]): Order[T] = instance

  trait Syntax[T] {
    def self: T

    def typeClassInstance: Order[T]

    def lt(y: T): Boolean = typeClassInstance.lt(self, y)

    def <(y: T): Boolean = typeClassInstance.lt(self, y)

    def lteq(y: T): Boolean = typeClassInstance.lteq(self, y)

    def <=(y: T): Boolean = typeClassInstance.lteq(self, y)

    def gteq(y: T): Boolean = typeClassInstance.gteq(self, y)

    def >=(y: T): Boolean = typeClassInstance.gteq(self, y)

    def gt(y: T): Boolean = typeClassInstance.gt(self, y)

    def >(y: T): Boolean = typeClassInstance.gt(self, y)

    def cmp(y: T): Ordering = typeClassInstance.order(self, y)

    def max(y: T): T = typeClassInstance.max(self, y)

    def min(y: T): T = typeClassInstance.min(self, y)
  }

  trait ToOrderSyntax {

    implicit def toOrderSyntax[T](target: T)(implicit instance: Order[T]): Syntax[T] = new Syntax[T] {

      override def self: T = target

      override def typeClassInstance: Order[T] = instance
    }

  }

  trait ToAllOrderSyntax extends ToOrderSyntax with Equal.ToAllEqSyntax

  object syntax extends ToAllOrderSyntax

  object typeClass extends ProductTypeClass[Order] {

    def emptyProduct: Order[HNil] = Order.create((_, _) => Eq)

    def product[F, T <: HList](oh: Order[F], ot: Order[T]): Order[::[F, T]] = Order.create[F :: T] {
      case ((h1 :: t1), (h2 :: t2)) => oh.order(h1, h2) match {
        case Eq => ot.order(t1, t2)
        case o => o
      }
    }

    def project[F, G](instance: => Order[G], to: F => G, from: G => F): Order[F] = {
      Order.create[F]((x, y) => instance.order(to(x), to(y)))
    }
  }

  def create[T](f: (T, T) => Ordering): Order[T] = new Order[T] {
    override def order(x: T, y: T): Ordering = f(x, y)
  }

  def fromComparable[T <: Comparable[T]]: Order[T] = create {
    _ compareTo _ match {
      case n if n < 0 => Lt
      case n if n > 0 => Gt
      case _ => Eq
    }
  }

  @inline
  def fromComparison[T](isLessThan: (T, T) => Boolean, isEqual: => (T, T) => Boolean): Order[T] = create { (x, y) =>
    if (isLessThan(x, y)) Lt else if (isEqual(x, y)) Eq else Gt
  }

  trait Laws[T] extends Equal.Laws[T]{
    override def typeClassInstance: Order[T]
    val transitiveLteq: Law3[T, T, T] = Laws.transitive[T]("lteq", typeClassInstance.lteq)
    val totalLteq: Law2[T, T] = Laws.total[T]("lteq", typeClassInstance.lteq)
    val antisymmetryLteqEqual: Law2[T, T] = Law.law2[T, T]("antisymmetry", "lteq", "equal") { (x, y) =>
      !(typeClassInstance.lteq(x, y) && typeClassInstance.lteq(y, x)) || typeClassInstance.equal(x, y)
    }
  }

  def laws[T](implicit instance: Order[T]):Laws[T] = new Laws[T] {
    override def typeClassInstance: Order[T] = instance
  }

}
