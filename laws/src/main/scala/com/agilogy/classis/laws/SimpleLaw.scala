package com.agilogy.classis.laws

sealed trait Law{
  def name:String
  def functionNames:Seq[String]
}

case class Law1[A](name: String, functionNames: Seq[String], check: (A) => Boolean) extends Law

case class Law2[A,B](name: String, functionNames: Seq[String], check: (A, B) => Boolean) extends Law

case class Law3[A,B,C](name: String, functionNames: Seq[String], check: (A,B,C) => Boolean) extends Law

object Law {

  def law1[A](name: String, functionName: String*)(f: A => Boolean) = Law1(name, functionName, f)

  def law2[A, B](name: String, functionName: String*)(f: (A, B) => Boolean) = Law2(name, functionName, f)

  def law3[A, B, C](name: String, functionName: String*)(f: (A, B, C) => Boolean) = Law3(name, functionName, f)

}
