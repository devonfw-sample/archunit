package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.priority;

import java.util.HashSet;
import java.util.Set;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.Priority;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * Contains the {@link ArchRule}s to verify the {@link PackageStructure}.
 */
class PackageRules {

  /**
   * @return {@link ArchRule} verifying that all packages follow the conventions of {@link PackageStructure}.
   */
  static ArchRule allPackagesShouldHaveValidStructure() {

    return priority(Priority.HIGH).classes().should(containsValidPackageStructureCondition());
  }

  private static ArchCondition<JavaClass> containsValidPackageStructureCondition() {

    return new ArchCondition<>(
        "reside only in packages following the convention «root».«component».«layer»[.«scope»][.«detail»] where layer is "
            + PackageStructure.PATTERN_LAYERS) {

      private final Set<String> invalidPackages = new HashSet<>();

      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {

        PackageStructure pkg = PackageStructure.of(javaClass);
        if (!pkg.isValid()) {
          String packageName = javaClass.getPackageName();
          boolean added = this.invalidPackages.add(packageName);
          if (added) {
            String message = packageName + " is an invalid package - check for for typo in layer";
            events.add(new SimpleConditionEvent(javaClass, false, message));
          }
        }
      }
    };
  };
}