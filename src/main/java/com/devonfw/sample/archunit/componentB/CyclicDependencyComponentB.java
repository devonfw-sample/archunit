package com.devonfw.sample.archunit.componentB;

import com.devonfw.sample.archunit.componentA.CyclicDependencyComponentA;

//violation of avoid cyclic dependencies
public class CyclicDependencyComponentB {
    CyclicDependencyComponentA cyclicDependencyComponentA;
}