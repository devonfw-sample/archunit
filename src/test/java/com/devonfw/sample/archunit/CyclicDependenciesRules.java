package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.lang.ArchRule;

public class CyclicDependenciesRules {
  static ArchRule cyclicDependenciesAreNotAllowed() {

    return slices().matching("..(*).(common|service|logic|dataaccess|batch|client)..")
        .namingSlices("$1 slice")
        .should()
        .beFreeOfCycles()
        .because("Cyclic dependencies should be prevented.");
  }
}
