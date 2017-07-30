package com.agilogy.classis.laws.test

import org.scalacheck.Properties

class LawsSpecification(name:String) extends Properties(name){

  def propertyName[T](name:String, typeName:String):String = s"$name[$typeName]"

  def check(lp:LawsProperties): Unit = lp.properties.foreach {
    case (n, p) => property(propertyName(n,lp.typeName)) = p
  }

}
