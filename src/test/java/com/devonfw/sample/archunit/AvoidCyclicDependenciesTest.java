package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.lang.ArchRule;

public class AvoidCyclicDependenciesTest {
  static final ArchRule no_cyclic_dependencies_are_allowed = slices()
      .matching("..(*).(common|service|logic|dataaccess|batch|client)..").namingSlices("$1 slice").should()
      .beFreeOfCycles()
      // .ignoreDependency(alwaysTrue(), simpleNameEndingWith("Repository"))
      .because("Cyclic dependencies should be prevented.");

}
