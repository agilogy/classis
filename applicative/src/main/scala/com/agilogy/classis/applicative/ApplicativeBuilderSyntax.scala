package com.agilogy.classis.applicative

import com.agilogy.classis.apply.Apply

import scala.language.higherKinds

object ApplicativeBuilderSyntax {

  implicit class ApplicativeBuilderSyntaxOps[M[_], A](self: M[A])(implicit ap: Apply[M]) {
    def |@|[B](b: M[B]) = new ApplicativeBuilder(self,b)
  }

}
