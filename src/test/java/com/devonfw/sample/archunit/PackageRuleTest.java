package com.devonfw.sample.archunit;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * JUnit test that validates the Packages of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class PackageRuleTest {

  /* ArchRule and Condition */
  @ArchTest
  public ArchRule shouldHaveValidLayers = classes().should(containsValidPackageStructureCondition());

  private static ArchCondition<JavaClass> containsValidPackageStructureCondition() {

    return new ArchCondition<JavaClass>("check for the package structure to be valid", new Object() {
    }) {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {
        PackageStructure packageStructure = PackageStructure.of(javaClass);
        String message = javaClass.getSimpleName() + " test result is " + packageStructure.isValid();
        events.add(new SimpleConditionEvent(javaClass, packageStructure.isValid(), message));
      }
    };
  };
}