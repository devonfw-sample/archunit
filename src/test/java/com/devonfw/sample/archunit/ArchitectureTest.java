package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

/**
 * JUnit test that validates the architecture of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  private final ArchTests CYCLIC_DEPENDENCIES_TEST = ArchTests.in(AvoidCyclicDependenciesTest.class);

  @ArchTest
  private final ArchTests COMPONENT_RULES = ArchTests.in(ComponentRuleTest.class);

  @ArchTest
  private final ArchTests LAYER_RULES = ArchTests.in(LayerRulesTest.class);

  @ArchTest
  private final ArchTests NAMING_CONVENTION_RULES = ArchTests.in(NamingConventionTest.class);

  @ArchTest
  private final ArchTests PACKAGE_RULES = ArchTests.in(PackageRuleTest.class);

  @ArchTest
  private final ArchTests SECURITY_RULES = ArchTests.in(SecurityTest.class);

  @ArchTest
  private final ArchTests THIRD_PARTY_RULES = ArchTests.in(ThirdPartyRulesTest.class);
}
