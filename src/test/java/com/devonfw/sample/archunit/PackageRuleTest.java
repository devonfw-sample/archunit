package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * JUnit test that validates the Packages of this application.
 */


@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class PackageRuleTest {

/*Components */
public static final String COMPONENT_GENERAL = "general";

public static final String COMPONENT_TASK = "task";

/*Layer */
public static final String LAYER_COMMON = "common";

public static final String LAYER_DATA_ACCESS = "dataaccess";

public static final String LAYER_LOGIC = "logic";

public static final String LAYER_SERVICE = "service";

public static final String LAYER_CLIENT = "client";

public static final String LAYER_BATCH = "batch";

public static final List<String> LAYERS = Arrays.asList(LAYER_CLIENT, LAYER_COMMON, LAYER_DATA_ACCESS,
LAYER_LOGIC, LAYER_SERVICE, LAYER_BATCH);

/* Pattern */
private static final String PATTERN_SEGMENT = "[a-zA-Z0-9_]+";

private static final String PATTERN_LAYERS = LAYER_COMMON + "|"
    + LAYER_DATA_ACCESS + "|" + LAYER_SERVICE  + "|" + LAYER_LOGIC + "|" + LAYER_BATCH + "|"
    + LAYER_CLIENT;

private static final String COMPONENT_LAYERS = COMPONENT_GENERAL + "|" + COMPONENT_TASK;

public static final String DEFAULT_PATTERN = "(" + PATTERN_SEGMENT + ")\\.(" + COMPONENT_LAYERS + ")\\.(" + PATTERN_LAYERS + ")\\.(" + PATTERN_SEGMENT + ")*";

/* ArchRule and Condition */
  @ArchTest
  public ArchRule shouldHaveValidLayers = classes().should(containsValidPackageStructureCondition());
  
 
  private static ArchCondition<JavaClass> containsValidPackageStructureCondition() {
    return new ArchCondition<JavaClass>("check for the package structure to be valid", new Object(){}) {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {
          Pattern pattern = Pattern.compile(DEFAULT_PATTERN);
          Matcher matcher = pattern.matcher(javaClass.getName());
          events.add(new SimpleConditionEvent(javaClass,
                  matcher.find(), "true if valid, flase if invalid"));
      }
    };
  };
}