package com.agilogy.classis.applicative.test

import com.agilogy.classis.applicative.ApplicativeBuilder
import com.agilogy.classis.applicative.ApplicativeBuilderSyntax
import org.scalatest.FunSpec

class ApplicativeBuilderTest extends FunSpec {

  it("should build an ApplicativeBuilder") {
    assert(ApplicativeBuilder(Option(23), Option(34)).tupled === Some((23, 34)))
    assert(ApplicativeBuilder(Option(23), Option(34)).apply(_ + _) === Some(23 + 34))
  }

  it("should build an ApplicativeBuilder3") {
    assert(ApplicativeBuilder(Option(23), Option(34)).|@|(Option(45)).tupled === Some((23, 34, 45)))
    assert(ApplicativeBuilder(Option(23), Option(34)).|@|(Option(45)).apply(_ + _ - _) === Some(23 + 34 - 45))
  }

  it("should build an ApplicativeBuilder using syntax") {
    import ApplicativeBuilderSyntax._
    assert((Option(23) |@| Option(34)).tupled === Some((23, 34)))
    assert((Option(23) |@| Option(34) |@| Option(45))(_ + _ - _) === Some(23 + 34 - 45))
  }

}
