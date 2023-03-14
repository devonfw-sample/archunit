package com.devonfw.sample.archunit.componentA.logic;

import com.devonfw.sample.archunit.componentB.logic.CyclicDependencyComponentB;

//violation of avoid cyclic dependencies 
public class CyclicDependencyComponentA {
    CyclicDependencyComponentB cyclicDependencyComponentB; 
}
