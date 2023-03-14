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

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleTest {

  public static final ArchCondition<JavaClass> haveComponentServiceLayerDependingOnDiffComponentsServiceLayer = new ArchCondition<JavaClass>(
      "have dependencies, towards another components service layer (Rule-C3).") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);
      String sourceClassLayer = sourcePkg.getLayer();
      String sourceClassComponent = sourcePkg.getComponent();

      // Check all project components' service layers except the default component for noncompliant dependencies towards
      // other components' service layers.
      if (sourcePkg.isLayerService() && !sourcePkg.isComponentGeneral()) {
        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

          JavaClass targetClass = dependency.getTargetClass();
          PackageStructure targetPkg = PackageStructure.of(targetClass);
          String targetClassComponent = targetPkg.getComponent();
          String targetClassLayer = targetPkg.getLayer();
          boolean isAllowedDependency = isComponentServiceLayerCompliantTowardsComponenetsServiceLayer(sourcePkg,
              targetPkg);

          if (targetPkg.isLayerService() && !isAllowedDependency) {
            String message = String.format(
                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourceClassComponent,
                sourceClassLayer, targetClassComponent, targetClassLayer, sourceClass.getDescription(),
                targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, true, message));
          }
        }
      }
    }
  };

  /**
   * verifying that the service layer of one component does not depend on the service layer of another component.
   */
  @ArchTest
  public static final ArchRule C3_no_service_layer_depends_on_service_layer_of_another_component = noClasses()
      .should(haveComponentServiceLayerDependingOnDiffComponentsServiceLayer)
      .as("Code from service layer of a component shall not depend on service layer of a different component.")
      .allowEmptyShould(true);

  public static final ArchCondition<JavaClass> haveComponentServiceLayerDependingOnDiffComponentsLogicLayer = new ArchCondition<JavaClass>(
      "have dependencies, towards another components logic layer (Rule-C4).") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);
      String sourceClassLayer = sourcePkg.getLayer();
      String sourceClassComponent = sourcePkg.getComponent();

      // Check all project components' service layers except the default component for noncompliant dependencies towards
      // other components' logic layers.
      if (sourcePkg.isLayerService() && !sourcePkg.isComponentGeneral()) {
        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

          JavaClass targetClass = dependency.getTargetClass();
          PackageStructure targetPkg = PackageStructure.of(targetClass);
          String targetClassComponent = targetPkg.getComponent();
          String targetClassLayer = targetPkg.getLayer();
          boolean isAllowedDependency = isComponentServiceLayerCompliantTowardsComponentsLogicLayer(sourcePkg,
              targetPkg);

          if (targetPkg.isLayerLogic() && !isAllowedDependency) {
            String message = String.format(
                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourceClassComponent,
                sourceClassLayer, targetClassComponent, targetClassLayer, sourceClass.getDescription(),
                targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, true, message));
          }
        }
      }
    }
  };

  /**
   * verifying that the service layer of one component does not depend on the logic layer of another component.
   */
  @ArchTest
  public static final ArchRule C4_no_dependencies_from_a_components_service_layer_to_anothers_component_logic_layer = noClasses()
      .should(haveComponentServiceLayerDependingOnDiffComponentsLogicLayer)
      .as("Code from service layer of a component shall not depend on logic layer of a different component.")
      .allowEmptyShould(true);

  public static final ArchCondition<JavaClass> haveComponentLogicLayerDependingOnDiffComponentsDataaccessLayer = new ArchCondition<JavaClass>(
      "have dependencies, towards another components dataaccess layer (Rule-C5).") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);
      String sourceClassLayer = sourcePkg.getLayer();
      String sourceClassComponent = sourcePkg.getComponent();

      // Check all project components' logic layers except the default component for noncompliant dependencies towards
      // other components' dataaccess layers.
      if (sourcePkg.isLayerLogic() && !sourcePkg.isComponentGeneral()) {
        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

          JavaClass targetClass = dependency.getTargetClass();
          PackageStructure targetPkg = PackageStructure.of(targetClass);
          String targetClassComponent = targetPkg.getComponent();
          String targetClassLayer = targetPkg.getLayer();
          boolean isAllowedDependency = isComponentLogicLayerCompliant(sourcePkg, targetPkg);

          if (targetPkg.isLayerDataAccess() && !isAllowedDependency) {
            String message = String.format(
                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourceClassComponent,
                sourceClassLayer, targetClassComponent, targetClassLayer, sourceClass.getDescription(),
                targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, true, message));
          }
        }
      }
    }
  };

  /**
   * verifying that the logic layer of a component may not depend on the dataaccess layer of another component.
   */
  @ArchTest
  public static final ArchRule C5_no_dependencies_from_a_components_logic_layer_to_anothers_component_dataaccess_layer = noClasses()
      .should(haveComponentLogicLayerDependingOnDiffComponentsDataaccessLayer)
      .as("Code from logic layer of a component shall not depend on dataaccess layer of a different component.")
      .allowEmptyShould(true);

  public static final ArchCondition<JavaClass> haveComponentDataaccessLayerDependingOnDiffComponentsDataaccessLayer = new ArchCondition<JavaClass>(
      "have dependencies, towards another dataaccess layer (Rule-C6).") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);
      String sourceClassLayer = sourcePkg.getLayer();
      String sourceClassComponent = sourcePkg.getComponent();

      // Check all project components' dataaccess layers except the default component for noncompliant dependencies
      // towards
      // other components' dataaccess layers.
      if (sourcePkg.isLayerDataAccess() && !sourcePkg.isComponentGeneral()) {
        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

          JavaClass targetClass = dependency.getTargetClass();
          PackageStructure targetPkg = PackageStructure.of(targetClass);
          String targetClassComponent = targetPkg.getComponent();
          String targetClassLayer = targetPkg.getLayer();
          boolean isAllowedDependency = isComponentDataaccessLayerCompliant(sourcePkg, targetPkg);

          if (targetPkg.isLayerDataAccess() && !isAllowedDependency) {
            String message = String.format(
                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourceClassComponent,
                sourceClassLayer, targetClassComponent, targetClassLayer, sourceClass.getDescription(),
                targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, true, message));
          }
        }
      }
    }
  };

  // medium severity
  /**
   * verifying that the dataaccess layer of one component does not depend on the dataaccess layer of another component.
   */
  @ArchTest
  public static final ArchRule C6_no_dataaccess_layer_depends_on_dataaccess_layer_of_another_component = noClasses()
      .should(haveComponentDataaccessLayerDependingOnDiffComponentsDataaccessLayer)
      .as("Code from dataaccess layer shall not depend on dataaccess layer of a different component")
      .allowEmptyShould(true);

  public static final ArchCondition<JavaClass> haveComponentBatchLayerDependingOnDiffComponentsLogicLayer = new ArchCondition<JavaClass>(
      "have dependencies, towards another components logic layer (Rule-C7).") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);
      String sourceClassLayer = sourcePkg.getLayer();
      String sourceClassComponent = sourcePkg.getComponent();

      // Check all project components' batch layers except the default component for noncompliant dependencies
      // towards
      // other components' logic layers.
      if (sourcePkg.isLayerBatch() && !sourcePkg.isComponentGeneral()) {
        for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

          JavaClass targetClass = dependency.getTargetClass();
          PackageStructure targetPkg = PackageStructure.of(targetClass);
          String targetClassComponent = targetPkg.getComponent();
          String targetClassLayer = targetPkg.getLayer();
          boolean isAllowedDependency = isComponentBatchLayerCompliant(sourcePkg, targetPkg);

          if (targetPkg.isLayerLogic() && !isAllowedDependency) {
            String message = String.format(
                "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourceClassComponent,
                sourceClassLayer, targetClassComponent, targetClassLayer, sourceClass.getDescription(),
                targetClass.getDescription());
            events.add(new SimpleConditionEvent(sourceClass, true, message));
          }
        }
      }
    }
  };

  /**
   * verifying that the batch layer of a component may not depend on the logic layer of another component.
   */
  @ArchTest
  public static final ArchRule C7_no_dependencies_from_a_components_batch_layer_to_anothers_component_logic_layer = noClasses()
      .should(haveComponentBatchLayerDependingOnDiffComponentsLogicLayer)
      .as("Code from batch layer of a component shall not depend on logic layer of a different component.")
      .allowEmptyShould(true);

  /**
   * Dependency of a components batch layer towards the same components logic or common layer is allowed. In addition a
   * dependency towards the projects default component is allowed too.
   */
  private static boolean isComponentBatchLayerCompliant(PackageStructure sourcePkg, PackageStructure targetPkg) {

    boolean isAllowed = false;
    // Components batch layer can depend on their own lower layers: logic and
    // common.
    if (sourcePkg.hasSameComponent(targetPkg) && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    // Components may always depend on the default business component.
    if (targetPkg.isComponentGeneral() && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    return isAllowed;
  }

  /**
   * Dependency of a components dataaccess layer towards the same components common layer is allowed. In addition a
   * dependency towards the projects default component is allowed too.
   */
  private static boolean isComponentDataaccessLayerCompliant(PackageStructure sourcePkg, PackageStructure targetPkg) {

    boolean isAllowed = false;
    // Components dataaccess layer can depend on their own dataaccess and common layer.
    if (sourcePkg.hasSameComponent(targetPkg) && (targetPkg.isLayerDataAccess() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    // Components may always depend on the default business component.
    if (targetPkg.isComponentGeneral() && (targetPkg.isLayerDataAccess() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    return isAllowed;
  }

  /**
   * Dependency of a components logic layer towards the same components dataaccess or common layer is allowed. In
   * addition a dependency towards the projects default component is allowed too.
   */
  private static boolean isComponentLogicLayerCompliant(PackageStructure sourcePkg, PackageStructure targetPkg) {

    boolean isAllowed = false;
    // Components logic layer can depend on their own lower layers: dataaccess and
    // common.
    if (sourcePkg.hasSameComponent(targetPkg) && (targetPkg.isLayerDataAccess() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    // Components may always depend on the default business component.
    if (targetPkg.isComponentGeneral() && (targetPkg.isLayerDataAccess() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    return isAllowed;
  }

  /**
   * Dependency of a components service layer towards the same components logic or common layer is allowed. In addition
   * a dependency towards the projects default component is allowed too.
   *
   * @param sourceClass Source JavaClass to check if dependencies from itself towards targetClass are allowed.
   * @param targetClass Target JavaClass to check if dependencies from sourceClass towards it are allowed.
   */
  private static boolean isComponentServiceLayerCompliantTowardsComponentsLogicLayer(PackageStructure sourcePkg,
      PackageStructure targetPkg) {

    boolean isAllowed = false;
    // Components service layer can depend on their own lower layers: logic and
    // common.
    if (sourcePkg.hasSameComponent(targetPkg) && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    // Components may always depend on the default business component.
    if (targetPkg.isComponentGeneral() && (targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    return isAllowed;
  }

  /**
   * Dependency of a components service layer towards the same components logic and common layer is allowed. In addition
   * a dependency towards the projects default component is allowed too.
   */
  private static boolean isComponentServiceLayerCompliantTowardsComponenetsServiceLayer(PackageStructure sourcePkg,
      PackageStructure targetPkg) {

    boolean isAllowed = false;
    // Components service layer can depend on their own logic and common layer.
    if (sourcePkg.hasSameComponent(targetPkg)
        && (targetPkg.isLayerService() || targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    // Components may always depend on the default business component.
    if (targetPkg.isComponentGeneral()
        && (targetPkg.isLayerService() || targetPkg.isLayerLogic() || targetPkg.isLayerCommon())) {
      isAllowed = true;
    }
    return isAllowed;
  }

}
