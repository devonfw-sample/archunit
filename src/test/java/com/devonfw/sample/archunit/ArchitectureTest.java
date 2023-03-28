package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * JUnit test that validates the architecture of this application.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  private static final ArchRule AVOID_CYCLIC_DEPENDENCIES_RULE = CyclicDependenciesRules
      .cyclicDependenciesAreNotAllowed();

  // Component Rules
  @ArchTest
  private static final ArchRule COMPONENT_SERVICE_TO_SERVICE_RULE = ComponentRules
      .serviceLayerOfOneComponentShouldNotDependOnTheServiceLayerOfAnotherComponent();

  @ArchTest
  private static final ArchRule COMPONENT_SERVICE_TO_LOGIC_RULE = ComponentRules
      .serviceLayerOfOneComponentShouldNotDependOnTheLogicLayerOfAnotherComponent();

  @ArchTest
  private static final ArchRule COMPONENT_LOGIC_TO_DATAACCESS_RULE = ComponentRules
      .logicLayerOfOneComponentShouldNotDependOnTheDataaccessLayerOfAnotherComponent();

  @ArchTest
  private static final ArchRule COMPONENT_DATAACCESS_TO_DATAACCESS_RULE = ComponentRules
      .dataaccessLayerOfOneComponentShouldNotDependOnTheDataaccessLayerOfAnotherComponent();

  @ArchTest
  private static final ArchRule COMPONENT_BATCH_TO_LOGIC_RULE = ComponentRules
      .batchLayerOfOneComponentShouldNotDependOnTheLogicLayerOfAnotherComponent();

  @ArchTest
  private static final ArchRule COMPONENT_GENERAL_DOES_NOT_DEPEND_ON_ANY_OTHER_COMPONENT_RULE = ComponentRules
      .theDefaultProjectComponentShouldNotDependOnAnyOtherComponent();

  // Layer rules test
  @ArchTest
  private static final ArchRule LAYER_DEPENDENCY_RULES = LayerRules.shouldOnlyAccessValidLayers();

  // Naming conventions test
  @ArchTest
  private static final ArchRule NAMING_CONVENTION_CTO_RULE = NamingConventionRules.namingConventionCtoCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_ENTITY_RULE = NamingConventionRules.namingConventionEntityCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_ETO_RULE = NamingConventionRules.namingConventionEtoCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_ABSTRACT_UC_RULE = NamingConventionRules.abstractUcCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_MAPPER_RULE = NamingConventionRules.mapperCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_PATH_RULE = NamingConventionRules.pathCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_JPA_REPOSITORY_RULE = NamingConventionRules.jpaRepositoryCheck();

  @ArchTest
  private static final ArchRule NAMING_CONVENTION_UC_RULE = NamingConventionRules.namingConventionUcCheck();

  // Package Rules
  @ArchTest
  private static final ArchRule VALID_PACKAGES_RULE = PackageRule.shouldHaveValidLayers();

  // Security Rules
  /*
   * @ArchTest
   * private final ArchRule security1 = SecurityTest.shouldBeProperlyAnnotated;
   * 
   * @ArchTest
   * private final ArchRule security2 = SecurityTest.shouldnTUseCreateQuery;
   * 
   * // Third-Party Rules
   * 
   * @ArchTest
   * private final ArchRule thirdparty1 =
   * ThirdPartyRulesTest.check_object_dependency;
   * 
   * @ArchTest
   * private final ArchRule thirdparty2 =
   * ThirdPartyRulesTest.check_converter_dependency;
   * 
   * @ArchTest
   * private final ArchRule thirdparty3 =
   * ThirdPartyRulesTest.check_mysema_dependency;
   * 
   * @ArchTest
   * private final ArchRule thirdparty4 =
   * ThirdPartyRulesTest.verifyingSpringframeworkTransactionalIsNotUsed;
   * 
   * @ArchTest
   * private final ArchRule thirdparty5 =
   * ThirdPartyRulesTest.verifyingProperTransactionalUseFromJee;
   * 
   * @ArchTest
   * private final ArchRule thirdparty6 =
   * ThirdPartyRulesTest.verifyingProperJpaUse;
   * 
   * @ArchTest
   * private final ArchRule thirdparty7 =
   * ThirdPartyRulesTest.jpaIsUsedAsEncouraged;
   * 
   */

  /*
   * @ArchTest
   * private final ArchTests CYCLIC_DEPENDENCIES_TEST =
   * ArchTests.in(AvoidCyclicDependenciesTest.class);
   * 
   * @ArchTest
   * private final ArchTests COMPONENT_RULES =
   * ArchTests.in(ComponentRuleTest.class);
   * 
   * @ArchTest
   * private final ArchTests LAYER_RULES = ArchTests.in(LayerRulesTest.class);
   * 
   * @ArchTest
   * private final ArchTests NAMING_CONVENTION_RULES =
   * ArchTests.in(NamingConventionTest.class);
   * 
   * @ArchTest
   * private final ArchTests PACKAGE_RULES = ArchTests.in(PackageRuleTest.class);
   * 
   * @ArchTest
   * private final ArchTests SECURITY_RULES = ArchTests.in(SecurityTest.class);
   * 
   * @ArchTest
   * private final ArchTests THIRD_PARTY_RULES =
   * ArchTests.in(ThirdPartyRulesTest.class);
   */
}
