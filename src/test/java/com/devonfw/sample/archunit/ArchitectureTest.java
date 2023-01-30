package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * JUnit test that validates the architecture of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  private static final ArchRule shouldOnlyAccessValidLayers = //
      layeredArchitecture().consideringAllDependencies() //
          .layer("common").definedBy("com.devonfw.sample.archunit.common..") //
          .layer("logic").definedBy("com.devonfw.sample.archunit.logic..") //
          .layer("dataaccess").definedBy("com.devonfw.sample.archunit.dataaccess..") //
          .layer("service").definedBy("com.devonfw.sample.archunit.service..") //
          .layer("client").definedBy("com.devonfw.sample.archunit.client..")

          .withOptionalLayers(true)
          .because("Dependency of technical layers violates architecture rules.");
  // ...

}
