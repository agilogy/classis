package com.agilogy.classis.monad

import com.agilogy.classis.laws.test.LawsSpecification

class MonadLawsSpecification extends LawsSpecification("Monad"){

  check(MonadLawsProperties[Option])
  check(MonadLawsProperties[List])
  check(MonadLawsProperties[Seq])
  check(MonadLawsProperties[Either[Int,?]])

}
