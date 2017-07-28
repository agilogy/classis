package com.agilogy.classis.order

sealed trait Ordering
case object Lt extends Ordering
case object Gt extends Ordering
case object Eq extends Ordering
