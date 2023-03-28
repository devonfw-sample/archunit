package com.devonfw.sample.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ComponentRules {

  private static DescribedPredicate<JavaClass> resideInServiceLayerOfAComponent = new DescribedPredicate<JavaClass>(
      "lie inside the service layer of a custom component different from the business architectures default general component") {
    @Override
    public boolean test(JavaClass input) {

      PackageStructure inputPkg = PackageStructure.of(input);
      boolean someCustomComponentServiceClass = inputPkg.isLayerService() && !inputPkg.isComponentGeneral()
          && inputPkg.isValid();
      return someCustomComponentServiceClass;
    }
  };

  private static DescribedPredicate<JavaClass> resideInLogicLayerOfAComponent = new DescribedPredicate<JavaClass>(
      "lie inside the logic layer of a custom component different from the business architectures default general component") {
    @Override
    public boolean test(JavaClass input) {

      PackageStructure inputPkg = PackageStructure.of(input);
      boolean someCustomComponentLogicClass = inputPkg.isLayerLogic() && !inputPkg.isComponentGeneral()
          && inputPkg.isValid();
      return someCustomComponentLogicClass;
    }
  };

  private static DescribedPredicate<JavaClass> resideInDataaccessLayerOfAComponent = new DescribedPredicate<JavaClass>(
      "lie inside the dataaccess layer of a custom component different from the business architectures default general component") {
    @Override
    public boolean test(JavaClass input) {

      PackageStructure inputPkg = PackageStructure.of(input);
      boolean someCustomComponentDataaccessClass = inputPkg.isLayerDataAccess() && !inputPkg.isComponentGeneral()
          && inputPkg.isValid();
      return someCustomComponentDataaccessClass;
    }
  };

  private static DescribedPredicate<JavaClass> resideInBatchLayerOfAComponent = new DescribedPredicate<JavaClass>(
      "lie inside the batch layer of a custom component different from the business architectures default general component") {
    @Override
    public boolean test(JavaClass input) {

      PackageStructure inputPkg = PackageStructure.of(input);
      boolean someCustomComponentBatchClass = inputPkg.isLayerBatch() && !inputPkg.isComponentGeneral()
          && inputPkg.isValid();
      return someCustomComponentBatchClass;
    }
  };

  private static DescribedPredicate<JavaClass> resideInTheGeneralProjectComponent = new DescribedPredicate<JavaClass>(
      "lie inside the business architectures default general component") {
    @Override
    public boolean test(JavaClass input) {

      PackageStructure inputPkg = PackageStructure.of(input);
      boolean someGeneralComponentClass = inputPkg.isComponentGeneral() && inputPkg.isValid();
      return someGeneralComponentClass;
    }
  };

  private static ArchCondition<JavaClass> dependOnDiffCustomComponents = new ArchCondition<JavaClass>(
      "depend on a different custom component") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);

      // Check for noncompliant dependencies towards other custom components'.
      for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

        JavaClass targetClass = dependency.getTargetClass();
        PackageStructure targetPkg = PackageStructure.of(targetClass);
        boolean isAllowedDependency = isDependingOnAnotherCustomComponent(sourcePkg, targetPkg);

        if (!isAllowedDependency) {
          String message = composeViolationMessage(sourceClass, targetClass, sourcePkg, targetPkg);
          events.add(new SimpleConditionEvent(sourceClass, true, message));
        }
      }
    }
  };

  private static ArchCondition<JavaClass> dependOnDiffComponentsServiceLayerClasses = new ArchCondition<JavaClass>(
      "depend on the service layer of a different custom component") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);

      // Check for noncompliant dependencies towards other components' service layers.
      for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

        JavaClass targetClass = dependency.getTargetClass();
        PackageStructure targetPkg = PackageStructure.of(targetClass);
        boolean isAllowedDependency = isDependingOnAnotherComponentsServiceLayer(sourcePkg, targetPkg);

        if (!isAllowedDependency) {
          String message = composeViolationMessage(sourceClass, targetClass, sourcePkg, targetPkg);
          events.add(new SimpleConditionEvent(sourceClass, true, message));
        }
      }
    }
  };

  private static ArchCondition<JavaClass> dependOnDiffComponentsLogicLayer = new ArchCondition<JavaClass>(
      "depend on the logic layer of a different custom component") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);

      // Check for noncompliant dependencies towards other components' logic layers.
      for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

        JavaClass targetClass = dependency.getTargetClass();
        PackageStructure targetPkg = PackageStructure.of(targetClass);
        boolean isAllowedDependency = isDependingOnAnotherComponentsLogicLayer(sourcePkg, targetPkg);

        if (!isAllowedDependency) {
          String message = composeViolationMessage(sourceClass, targetClass, sourcePkg, targetPkg);
          events.add(new SimpleConditionEvent(sourceClass, true, message));
        }
      }
    }
  };

  private static ArchCondition<JavaClass> dependOnDiffComponentsDataaccessLayer = new ArchCondition<JavaClass>(
      "depend on the dataacces layer of a different custom component") {
    @Override
    public void check(JavaClass sourceClass, ConditionEvents events) {

      PackageStructure sourcePkg = PackageStructure.of(sourceClass);

      // Check for noncompliant dependencies towards other components' dataaccess
      // layers.
      for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {

        JavaClass targetClass = dependency.getTargetClass();
        PackageStructure targetPkg = PackageStructure.of(targetClass);
        boolean isAllowedDependency = isDependingOnAnotherComponentsDataaccessLayer(sourcePkg, targetPkg);

        if (!isAllowedDependency) {
          String message = composeViolationMessage(sourceClass, targetClass, sourcePkg, targetPkg);
          events.add(new SimpleConditionEvent(sourceClass, true, message));
        }
      }
    }
  };

  /**
   * verifying that the service layer of one component does not depend on the service layer of another component.
   */
  static ArchRule serviceLayerOfOneComponentShouldNotDependOnTheServiceLayerOfAnotherComponent() {

    return noClasses().that(resideInServiceLayerOfAComponent).should(dependOnDiffComponentsServiceLayerClasses)
        .as("Code from service layer of a component shall not depend on service layer of a different component.")
        .allowEmptyShould(true);
  }

  /**
   * verifying that the service layer of one component does not depend on the logic layer of another component.
   */
  static ArchRule serviceLayerOfOneComponentShouldNotDependOnTheLogicLayerOfAnotherComponent() {

    return noClasses().that(resideInServiceLayerOfAComponent).should(dependOnDiffComponentsLogicLayer)
        .as("Code from service layer of a component shall not depend on logic layer of a different component.")
        .allowEmptyShould(true);
  }

  /**
   * verifying that the logic layer of a component may not depend on the dataaccess layer of another component.
   */
  static ArchRule logicLayerOfOneComponentShouldNotDependOnTheDataaccessLayerOfAnotherComponent() {

    return noClasses().that(resideInLogicLayerOfAComponent).should(dependOnDiffComponentsDataaccessLayer)
        .as("Code from logic layer of a component shall not depend on dataaccess layer of a different component.")
        .allowEmptyShould(true);
  }

  // medium severity
  /**
   * verifying that the dataaccess layer of one component does not depend on the dataaccess layer of another component.
   */
  static ArchRule dataaccessLayerOfOneComponentShouldNotDependOnTheDataaccessLayerOfAnotherComponent() {

    return noClasses().that(resideInDataaccessLayerOfAComponent).should(dependOnDiffComponentsDataaccessLayer)
        .as("Code from dataaccess layer shall not depend on dataaccess layer of a different component.")
        .allowEmptyShould(true);
  }

  /**
   * verifying that the batch layer of a component may not depend on the logic layer of another component.
   */
  static ArchRule batchLayerOfOneComponentShouldNotDependOnTheLogicLayerOfAnotherComponent() {

    return noClasses().that(resideInBatchLayerOfAComponent).should(dependOnDiffComponentsLogicLayer)
        .as("Code from batch layer of a component shall not depend on logic layer of a different component.")
        .allowEmptyShould(true);
  }

  /**
   * verifying that the business architectures default general component does not depend on any other component.
   */
  static ArchRule theDefaultProjectComponentShouldNotDependOnAnyOtherComponent() {

    return noClasses().that(resideInTheGeneralProjectComponent).should(dependOnDiffCustomComponents)
        .as("Code from the business architecture general component must not depend on any other component.")
        .allowEmptyShould(true);
  }

  private static boolean isDependingOnAnotherCustomComponent(PackageStructure sourcePkg, PackageStructure targetPkg) {

    boolean isAllowed = true;
    if (isDifferentCustomComponent(sourcePkg, targetPkg)) {
      isAllowed = false;
    }
    return isAllowed;
  }

  private static boolean isDependingOnAnotherComponentsDataaccessLayer(PackageStructure sourcePkg,
      PackageStructure targetPkg) {

    boolean isAllowed = true;
    if (isDifferentCustomComponent(sourcePkg, targetPkg) && targetPkg.isLayerDataAccess()) {
      isAllowed = false;
    }
    return isAllowed;
  }

  private static boolean isDependingOnAnotherComponentsLogicLayer(PackageStructure sourcePkg,
      PackageStructure targetPkg) {

    boolean isAllowed = true;
    if (isDifferentCustomComponent(sourcePkg, targetPkg) && targetPkg.isLayerLogic()) {
      isAllowed = false;
    }
    return isAllowed;
  }

  private static boolean isDependingOnAnotherComponentsServiceLayer(PackageStructure sourcePkg,
      PackageStructure targetPkg) {

    boolean isAllowed = true;
    if (isDifferentCustomComponent(sourcePkg, targetPkg) && targetPkg.isLayerService()) {
      isAllowed = false;
    }
    return isAllowed;
  }

  /**
   * Check whether the given PackageStructures do not share the same component name and if the target package is not the
   * default component.
   *
   * @param sourcePkg
   * @param targetPkg
   * @return Return {@code true} if the given {@code targetPkg} is not the default {@link PackageStructure} "general"
   *         component and both of the parameters do not belong to the same component. Otherwise, return {@code false}.
   */
  private static boolean isDifferentCustomComponent(PackageStructure sourcePkg, PackageStructure targetPkg) {

    return !sourcePkg.hasSameComponent(targetPkg) && !targetPkg.isComponentGeneral() && targetPkg.isValid();
  }

  private static String composeViolationMessage(JavaClass sourceClass, JavaClass targetClass,
      PackageStructure sourcePkg, PackageStructure targetPkg) {

    String violationMessage = String.format(
        "'%s.%s' is dependend on '%s.%s'. Violated in: (%s). Dependency towards (%s)", sourcePkg.getComponent(),
        sourcePkg.getLayer(), targetPkg.getComponent(), targetPkg.getLayer(), sourceClass.getDescription(),
        targetClass.getDescription());
    return violationMessage;
  }

}
