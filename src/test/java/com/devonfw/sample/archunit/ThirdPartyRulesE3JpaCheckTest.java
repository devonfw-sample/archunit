package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ThirdPartyRulesE3JpaCheckTest {
  private static final Pattern PATTERN_COMMON = Pattern.compile(PackageRuleTest.COMMON_PATTERN);

  private static final Pattern PATTERN_DATAACCESS = Pattern.compile(PackageRuleTest.DATAACCESS_PATTERN);

  private static boolean isUsingJavaxPersistenceDataAccessOrEmbeddablesInCommon(JavaClass source,
      String targetPackageFullName) {

    if (targetPackageFullName.startsWith("javax.persistence")) {
      Matcher commonMatcher = PATTERN_COMMON.matcher(source.getPackageName());
      Matcher dataaccessMatcher = PATTERN_DATAACCESS.matcher(source.getPackageName());
      if (dataaccessMatcher.matches()) {
        return true;
      }
      if (commonMatcher.matches() && source.getSimpleName().contains("Embeddable")) {
        return true;
      }
      return false;
    }
    return true;
  }

  static final ArchCondition<JavaClass> misuse_jpa = new ArchCondition<JavaClass>(
      "use JPA outside of dataaccess layer or embeddables in common layer (Rule-E3)") {
    @Override
    public void check(JavaClass item, ConditionEvents events) {

      for (Dependency access : item.getDirectDependenciesFromSelf()) {
        String targetPackageFullName = access.getTargetClass().getFullName();
        String targetClassDescription = access.getDescription();
        if (isUsingJavaxPersistenceDataAccessOrEmbeddablesInCommon(item, targetPackageFullName) == false) {
          String message = String.format(
              "JPA (%s) shall only be used in dataaccess layer or for embeddables in common layer. Violated in (%s)",
              targetPackageFullName, targetClassDescription);
          events.add(new SimpleConditionEvent(item, true, message));
        }
      }
    }
  };

  @ArchTest
  static final ArchRule verifying_proper_jpa_use = noClasses().should(misuse_jpa).allowEmptyShould(true);
}
