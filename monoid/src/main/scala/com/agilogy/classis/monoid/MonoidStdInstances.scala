package com.agilogy.classis.monoid

import com.agilogy.classis.semigroup.SemigroupStdInstances
import com.agilogy.classis.zero.ZeroStdInstances

trait MonoidStdInstances extends ZeroStdInstances with SemigroupStdInstances

object MonoidStdInstances extends MonoidStdInstances
