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

          .whereLayer("common").mayOnlyAccessLayers("common")
          // L01: Common Layer doesnt depend on any other layer
          //.whereLayer("client").mayOnlyBeAccessedByLayers("client")
          // L02: verifying that only client layer code may depend on client layer.
          //.whereLayer("client").mayOnlyAccessLayers("client")
          // L03: verifying that client layer does not depend on logic layer.
          // L04: verifying that client layer does not depend on dataaccess layer.
          // L05: verifying that client layer does not depend on batch layer.
          .whereLayer("service").mayOnlyBeAccessedByLayers("client")
          .whereLayer("service").mayOnlyAccessLayers("logic", "common")
          // L08: verifying that code from service layer does not depend on dataaccess layer.
          // L06: verifying that service layer does not on depend batch layer.
          //.whereLayer("batch").mayOnlyAccessLayers("java")
          // L07: verifying that batch layer does not depend on service layer.
          // L11: verifying that batch layer does not depend on dataaccess layer.
          .whereLayer("logic").mayOnlyBeAccessedByLayers("service")
          .whereLayer("logic").mayOnlyAccessLayers("dataaccess", "common")
          // L09: verifying that code from logic layer does not depend on service layer (of same app).
          .whereLayer("dataaccess").mayOnlyBeAccessedByLayers("logic")
          .whereLayer("dataaccess").mayOnlyAccessLayers("dataaccess", "common")
          // L10: verifying that dataaccess layer does not depend on service layer.
          // L12: verifying that dataaccess layer does not depend on logic layer.
          .withOptionalLayers(true)
          .because("Dependency of technical layers violates architecture rules.");
  // ...

}
