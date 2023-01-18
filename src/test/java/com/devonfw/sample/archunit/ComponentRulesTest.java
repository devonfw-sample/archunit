package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * JUnit test that validates the component rules architecture of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRulesTest {

  @ArchTest
  private static final ArchRule DevonArchitectureComponentDeclarationCheck = //
      layeredArchitecture().consideringAllDependencies().;

  @ArchTest
  private static final ArchRule DevonArchitectureComponentDependencyCheck = //
      layeredArchitecture().consideringAllDependencies() //
          .layer("common").definedBy("..common..") //
          .layer("logic").definedBy("..logic..") //
          .layer("dataaccess").definedBy("..dataaccess..") //
          .layer("service").definedBy("..service..") //
          .layer("batch").definedBy("..batch..") //
          .layer("ui").definedBy("..ui..") //
          // TODO
          .because("Dependency of technical layers violates architecture rules.");
  
  @ArchTest
  private static final ArchRule DevonArchitectureLayerService2Service4ComponentCheck = //
      components();

  @ArchTest
  private static final ArchRule DevonArchitectureLayerService2Logic4ComponentCheck = //
      layeredArchitecture();

  @ArchTest
  private static final ArchRule DevonArchitectureLayerLogic2Dataaccess4ComponentCheck = //
      layeredArchitecture();

  @ArchTest
  private static final ArchRule DevonArchitectureLayerDataaccess2Dataaccess4ComponentCheck = //
      layeredArchitecture();

  @ArchTest
  private static final ArchRule DevonArchitectureLayerBatch2Logic4ComponentCheck = //
      layeredArchitecture();

}
