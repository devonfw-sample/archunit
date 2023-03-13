package com.devonfw.sample.archunit.componentA;

import com.devonfw.sample.archunit.componentB.CyclicDependencyComponentB;

//violation of avoid cyclic dependencies 
public class CyclicDependencyComponentA {
    CyclicDependencyComponentB cyclicDependencyComponentB; 
}
