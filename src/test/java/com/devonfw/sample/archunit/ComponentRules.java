package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;
import com.tngtech.archunit.lang.Priority;

/**
 * Contains the {@link ArchRule}s to verify dependencies between components.
 */
class ComponentRules extends AbstractRules {

  static ArchRule shouldNotHaveCyclicDependencies() {

    return CompositeArchRule.priority(Priority.HIGH).of(slices().matching("..(*).(" + PackageStructure.PATTERN_LAYERS + ")..")
        .namingSlices("$1 slice").should().beFreeOfCycles().because("cyclic dependencies should be prevented."));
  }

  static ArchRule serviceLayerShouldNotDependOnServiceLayerOfAnotherComponent() {

    return priority(Priority.HIGH).noClasses().that(haveServiceLayer()).should(dependOnOtherComponentsServiceLayer()).allowEmptyShould(true)
        .because("code from service layer of a component shall not depend on service layer of a different component");
  }

  static ArchRule serviceLayerShouldNotDependOnLogicLayerOfAnotherComponent() {

    return priority(Priority.HIGH).noClasses().that(haveServiceLayer()).should(dependOnOtherComponentsLogicLayer())
        .as("code from service layer of a component shall not depend on logic layer of a different component.").allowEmptyShould(true);
  }

  static ArchRule logicLayerShouldNotDependOnDataaccessLayerOfAnotherComponent() {

    return priority(Priority.HIGH).noClasses().that(haveLogicLayer()).should(dependOnOtherComponentsDataaccessLayer())
        .as("code from logic layer shall not depend on dataaccess layer of a different component.").allowEmptyShould(true);
  }

  static ArchRule dataaccessLayerShouldNotDependOnDataaccessLayerOfAnotherComponent() {

    return priority(Priority.MEDIUM).noClasses().that(haveDataaccessLayer()).should(dependOnOtherComponentsDataaccessLayer())
        .as("code from dataaccess layer shall not depend on dataaccess layer of a different component.").allowEmptyShould(true);
  }

  static ArchRule batchLayerShouldNotDependOnLogicLayerOfAnotherComponent() {

    return priority(Priority.MEDIUM).noClasses().that(haveBatchLayer()).should(dependOnOtherComponentsLogicLayer())
        .as("Code from batch layer of a component shall not depend on logic layer of a different component.").allowEmptyShould(true);
  }

  static ArchRule generalDoesNotDependOnAnotherComponent() {

    return priority(Priority.MEDIUM).noClasses().that(haveGeneralComponent()).should().accessClassesThat(notHaveGeneralComponent())
        .as("general component must not depend on any other component.").allowEmptyShould(true);
  }

}
