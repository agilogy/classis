package com.agilogy.classis.laws

sealed trait Law[T]{
  def name:String
  def functionNames:Seq[String]
}

case class Law1[T](name: String, functionNames: Seq[String], check: (T) => Boolean) extends Law[T]

case class Law2[T](name: String, functionNames: Seq[String], check: (T, T) => Boolean) extends Law[T]

case class Law3[T](name: String, functionNames: Seq[String], check: (T, T, T) => Boolean) extends Law[T]

object Law {

  def law1[T](name: String, functionName: String*)(f: T => Boolean) = Law1(name, functionName, f)

  def law2[T](name: String, functionName: String*)(f: (T, T) => Boolean) = Law2(name, functionName, f)

  def law3[T](name: String, functionName: String*)(f: (T, T, T) => Boolean) = Law3(name, functionName, f)

}
