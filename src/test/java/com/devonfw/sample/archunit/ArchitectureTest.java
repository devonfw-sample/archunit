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
          .layer("common").definedBy("..common..") //
          .layer("logic").definedBy("..logic..") //
          .layer("dataaccess").definedBy("..dataaccess..") //
          .layer("service").definedBy("..service..") //
          .layer("batch").definedBy("..batch..") //
          .layer("ui").definedBy("..ui..")
          .layer("java").definedBy("..java..") //
          .layer("javax").definedBy("..javax..")
          .layer("mapper").definedBy("..mapstruct..")
          .layer("logger").definedBy("..org.slf4j..")
          .layer("database").definedBy("..com.querydsl..")
          .layer("spring").definedBy("..org.springframework..")
          .layer("client").definedBy("..client")
          
          // TODO
          //.whereLayer("ui").mayNotBeAccessedByAnyLayer()
          .whereLayer("common").mayOnlyAccessLayers("java", "javax") 
          // L01: Common Layer doesnt depend on any other layer (except obv. java classes)
          .whereLayer("client").mayOnlyBeAccessedByLayers("client", "java") 
          // L02: verifying that only client layer code may depend on client layer.
          .whereLayer("client").mayOnlyAccessLayers("client", "java") 
          // L03: verifying that client layer does not depend on logic layer.
          // L04: verifying that client layer does not depend on dataaccess layer.
          // L05: verifying that client layer does not depend on batch layer.
          .whereLayer("service").mayOnlyBeAccessedByLayers("ui", "client")
          .whereLayer("service").mayOnlyAccessLayers("logic", "java", "javax", "common")
          // L08: verifying that code from service layer does not depend on dataaccess layer.
          // L06: verifying that service layer does not on depend batch layer.
          .whereLayer("batch").mayOnlyAccessLayers("java")
          // L07: verifying that batch layer does not depend on service layer.
          // L11: verifying that batch layer does not depend on dataaccess layer.
          .whereLayer("logic").mayOnlyBeAccessedByLayers("service") 
          .whereLayer("logic").mayOnlyAccessLayers("dataaccess", "java", "javax", "mapper", "common", "logger")
          // L09: verifying that code from logic layer does not depend on service layer (of same app).
          .whereLayer("dataaccess").mayOnlyBeAccessedByLayers("logic")
          .whereLayer("dataaccess").mayOnlyAccessLayers("dataaccess", "java", "javax", "common", "spring", "database")
          // L10: verifying that dataaccess layer does not depend on service layer.
          // L12: verifying that dataaccess layer does not depend on logic layer.
          .because("Dependency of technical layers violates architecture rules.");
  // ...

}
