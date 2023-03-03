package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/**
 * verifying that the service layer of a component may not depend on the logic layer of another component.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleC4LayerService2Logic4ComponentTest {

  static final String PROJECT_NAME = "com.devonfw.sample.archunit";

  static final String DEFAULT_COMPONENT_SIMPLE_NAME = "general";

  static final String DEFAULT_COMPONENT_FULL_NAME = PROJECT_NAME + "." + DEFAULT_COMPONENT_SIMPLE_NAME;

  static ArchCondition<JavaClass> haveNonCompliantComponentDependencies = new ArchCondition<JavaClass>(
      "have dependencies, towards another components logic layer (Rule-C4).") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      String sourceClassName = sourceClass.getFullName();
      String sourceClassLayer = getClassLayer(sourceClass);
      String sourceClassComponent = getComponentNameOfClass(sourceClass);

      // Check all project components' service layers except the default component for noncompliant dependencies towards
      // other components' logic layers.
      if (sourceClassLayer.equals("service") && !sourceClassComponent.equals("")
          && !sourceClassComponent.equals(DEFAULT_COMPONENT_SIMPLE_NAME)) {
        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {
          JavaClass targetClass = dependency.getTargetClass();
          String targetClassName = targetClass.getFullName();
          String targetClassComponent = getComponentNameOfClass(targetClass);
          String targetClassLayer = getClassLayer(targetClass);
          boolean dependencyAllowed = isAllowedDependency(sourceClass, targetClass);

          // WARNING: Dependency of a components service layer towards another components layer other than logic wont be
          // registered as a violation.
          // (Other rules cover these violations.)
          if (targetClassName.startsWith(PROJECT_NAME) && targetClassLayer.equals("logic") && !dependencyAllowed) {
            String message = String.format(
                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourceClassComponent,
                sourceClassLayer, targetClassComponent, targetClassLayer, sourceClassName,
                targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, true, message));
          }
        }
      }
    }
  };

  @ArchTest
  static final ArchRule no_dependencies_from_a_components_service_layer_to_anothers_component_logic_layer = noClasses()
      .should(haveNonCompliantComponentDependencies)
      .as("Code from service layer of a component shall not depend on logic layer of a different component.")
      .allowEmptyShould(true);

  /**
   * Dependency of a components service layer towards the same components logic or common layer is allowed.
   */
  private static boolean isAllowedDependency(JavaClass sourceClass, JavaClass targetClass) {

    boolean isAllowed = false;
    String targetClassName = targetClass.getFullName();

    // Components service layer can depend on their own lower layers: logic and common.
    if (classesAreInSameComponent(sourceClass, targetClass)
        && (targetClassName.contains("logic") || targetClassName.contains("common"))) {
      isAllowed = true;
    }
    // Components may always depend on the default business component.
    if (targetClassName.startsWith(DEFAULT_COMPONENT_FULL_NAME)
        && (targetClassName.contains("logic") || targetClassName.contains("common"))) {
      isAllowed = true;
    }
    return isAllowed;
  }

  /**
   * Returns the name of the layer of a given class, if the class does lie in a projects component. Otherwise returns a
   * blank string "".
   */
  private static String getClassLayer(JavaClass clazz) {

    String classLayerName = "";
    String className = clazz.getFullName();
    if (className.startsWith(PROJECT_NAME)) {
      String classPackageName = clazz.getPackageName();
      String classComponentAndLayerName = classPackageName.substring(PROJECT_NAME.length() + 1,
          classPackageName.length());
      int beginOfLayerName = classComponentAndLayerName.indexOf(".") + 1;
      int endOfLayerName = classComponentAndLayerName.length();
      classLayerName = classComponentAndLayerName.substring(beginOfLayerName, endOfLayerName);
    }
    return classLayerName;
  }

  /**
   * Returns the name of the component of a given class, if the class does lie in a projects component. Otherwise
   * returns a blank string "".
   */
  private static String getComponentNameOfClass(JavaClass clazz) {

    String classComponentName = "";
    String className = clazz.getFullName();
    if (className.startsWith(PROJECT_NAME)) {
      String classPackageName = clazz.getPackageName();
      String classComponentAndLayerName = classPackageName.substring(PROJECT_NAME.length() + 1,
          classPackageName.length());
      int endOfComponentName = classComponentAndLayerName.indexOf(".");
      classComponentName = classComponentAndLayerName.substring(0, endOfComponentName);
    }
    return classComponentName;
  }

  /**
   * Returns true, if both given classes lie in the same projects component. In case at least one of the given classes
   * does not lie in any of the project components false will be returned.
   *
   * @param sourceClass JavaClass A to check against another JavaClass B.
   * @param targetClass JavaClass B to check against another JavaClass A.
   */
  private static boolean classesAreInSameComponent(JavaClass sourceClass, JavaClass targetClass) {

    String sourceComponent = getComponentNameOfClass(sourceClass);
    String targetComponent = getComponentNameOfClass(targetClass);
    if (targetComponent == "" || sourceComponent == "") {
      return false;
    }
    return sourceComponent.equals(targetComponent) ? true : false;
  }

}
