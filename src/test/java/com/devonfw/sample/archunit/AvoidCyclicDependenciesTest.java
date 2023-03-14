package com.devonfw.sample.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class AvoidCyclicDependenciesTest {
    @ArchTest
    static final ArchRule no_cyclic_dependencies_are_allowed =
    slices()
        .matching("..(*).(common|service|logic|dataaccess|batch|client)..")
        .namingSlices("$1 slice")
        .should()
        .beFreeOfCycles()
        //.ignoreDependency(alwaysTrue(), simpleNameEndingWith("Repository"))
        .because("Cyclic dependencies should be prevented.");

}
