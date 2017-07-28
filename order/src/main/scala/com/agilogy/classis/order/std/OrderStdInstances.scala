package com.agilogy.classis.order.std

import com.agilogy.classis.order.{Eq, Gt, Lt, Order, Ordering}

trait OrderStdInstances {

  implicit val booleanOrder: Order[Boolean] = Order.fromComparison[Boolean](_ < _, _ == _)
  implicit val byteOrder: Order[Byte] = Order.fromComparison[Byte](_ < _, _ == _)
  implicit val shortOrder: Order[Short] = Order.fromComparison[Short](_ < _, _ == _)
  implicit val intOrder: Order[Int] = Order.fromComparison[Int](_ < _, _ == _)
  implicit val longOrder: Order[Long] = Order.fromComparison[Long](_ < _, _ == _)
  implicit val floatOrder: Order[Float] = Order.fromComparison[Float](_ < _, _ == _)
  implicit val doubleOrder: Order[Double] = Order.fromComparison[Double](_ < _, _ == _)

  implicit val orderingOrder: Order[Ordering] = Order.create[Ordering]{
    case (x, y) if x == y => Eq
    case (Lt, _) => Lt
    case (Eq, Lt) => Gt
    case (Eq, Gt) => Lt
    case (Gt, _) => Gt
  }

  implicit def optionOrder[T](implicit tOrder: Order[T]): Order[Option[T]] = Order.create {
    case (None, None) => Eq
    case (None, Some(_)) => Lt
    case (Some(_), None) => Gt
    case (Some(x), Some(y)) => tOrder.order(x, y)
  }

  implicit def eitherOrder[L, R](implicit lOrder: Order[L], rOrder: Order[R]): Order[Either[L, R]] = Order.create {
    case (Left(x), Left(y)) => lOrder.order(x, y)
    case (Left(_), Right(_)) => Lt
    case (Right(_), Left(_)) => Gt
    case (Right(x), Right(y)) => rOrder.order(x, y)

  }

}

object OrderStdInstances extends OrderStdInstances
