package com.devonfw.sample.archunit.componentB.logic;

import com.devonfw.sample.archunit.componentA.logic.CyclicDependencyComponentA;

//violation of avoid cyclic dependencies
public class CyclicDependencyComponentB {
    CyclicDependencyComponentA cyclicDependencyComponentA;
}