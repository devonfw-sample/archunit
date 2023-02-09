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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JUnit test that validates the Packages of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class PackageRuleTest {

/*Layer */
private static final String LAYER_COMMON = "common";

private static final String LAYER_DATA_ACCESS = "dataaccess";

private static final String LAYER_LOGIC = "logic";

private static final String LAYER_SERVICE = "service";

private static final String LAYER_CLIENT = "client";

private static final String LAYER_BATCH = "batch";

/* Pattern */
private static final String PATTERN_SEGMENT = "[a-z0-9_]+";

private static final String PATTERN_LAYERS = LAYER_COMMON + "|"
    + LAYER_DATA_ACCESS + "|" + LAYER_SERVICE  + "|" + LAYER_LOGIC + "|" + LAYER_BATCH + "|"
    + LAYER_CLIENT;
                       
private static final String ROOT_PACKAGE =
  // ....1..........................2
  "(" + PATTERN_SEGMENT +")\\.(" + PATTERN_SEGMENT +".)*";

private static final String DEFAULT_PATTERN = 
  // ....1......................2...........................3...........................4
  "(" + ROOT_PACKAGE + ")\\.(" + PATTERN_SEGMENT + ")\\.(" + PATTERN_LAYERS + ")\\.?(" + PATTERN_SEGMENT + ")*";

private static final Pattern pattern = Pattern.compile(DEFAULT_PATTERN);

/* ArchRule and Condition */
  @ArchTest
  public ArchRule shouldHaveValidLayers = classes().should(containsValidPackageStructureCondition());
  
 
  private static ArchCondition<JavaClass> containsValidPackageStructureCondition() {
    return new ArchCondition<JavaClass>("check for the package structure to be valid", new Object(){}) {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {
          Matcher matcher = pattern.matcher(javaClass.getPackageName());
          String message = javaClass.getSimpleName() + "test result is" + matcher.matches();
          events.add(new SimpleConditionEvent(javaClass,
                  matcher.matches(), message));
      }
    };
  };
}