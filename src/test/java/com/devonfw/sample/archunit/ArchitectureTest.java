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
  private final ArchRule avoidCyclicDependenciesRule = CyclicDependenciesRules
      .cyclicDependenciesAreNotAllowed();

  // Component Rules
  @ArchTest
  private final ArchRule componentServiceToServiceRule = ComponentRules
      .serviceLayerOfOneComponentShouldNotDependOnTheServiceLayerOfAnotherComponent();

  @ArchTest
  private final ArchRule componentServiceToLogicRule = ComponentRules
      .serviceLayerOfOneComponentShouldNotDependOnTheLogicLayerOfAnotherComponent();

  @ArchTest
  private final ArchRule componentLogicToDataaccessRule = ComponentRules
      .logicLayerOfOneComponentShouldNotDependOnTheDataaccessLayerOfAnotherComponent();

  @ArchTest
  private final ArchRule componentDataaccessToDataaccessRule = ComponentRules
      .dataaccessLayerOfOneComponentShouldNotDependOnTheDataaccessLayerOfAnotherComponent();

  @ArchTest
  private final ArchRule componentBatchToLogicRule = ComponentRules
      .batchLayerOfOneComponentShouldNotDependOnTheLogicLayerOfAnotherComponent();

  @ArchTest
  private final ArchRule componentGeneralDoesNotDependOnAnyOtherComponentRule = ComponentRules
      .theDefaultProjectComponentShouldNotDependOnAnyOtherComponent();

  // Layer rules test
  @ArchTest
  private final ArchRule layerDependencyRule = LayerRules.shouldOnlyAccessValidLayers();

  // Naming conventions test
  @ArchTest
  private final ArchRule namingConventionCtoRule = NamingConventionRules.namingConventionCtoCheck();

  @ArchTest
  private final ArchRule namingConventionEntityRule = NamingConventionRules.namingConventionEntityCheck();

  @ArchTest
  private final ArchRule namingConventionEtoRule = NamingConventionRules.namingConventionEtoCheck();

  @ArchTest
  private final ArchRule namingConventionAbstractUcRule = NamingConventionRules.abstractUcCheck();

  @ArchTest
  private final ArchRule namingConventionMapperRule = NamingConventionRules.mapperCheck();

  @ArchTest
  private final ArchRule namingConventionPathRule = NamingConventionRules.pathCheck();

  @ArchTest
  private final ArchRule namingConventionJpaRepositoryRule = NamingConventionRules.jpaRepositoryCheck();

  @ArchTest
  private final ArchRule namingConventionUcRule = NamingConventionRules.namingConventionUcCheck();

  // Package Rules
  @ArchTest
  private final ArchRule validPackagesRule = PackageRule.shouldHaveValidLayers();

  // Security Rules
  @ArchTest
  private final ArchRule securityProperAnnotationRule = SecurityRules.shouldBeProperlyAnnotated();

  @ArchTest
  private final ArchRule securityAvoidCreateQueryRule = SecurityRules.shouldntUseCreateQuery();

  // Third-Party Rules
  @ArchTest
  private final ArchRule thirdpartyCheckObjectDependencyRule = ThirdPartyRules.checkObjectDependency();

  @ArchTest
  private final ArchRule thirdpartyCheckConverterDependencyRule = ThirdPartyRules.checkConverterDependency();

  @ArchTest
  private final ArchRule thirdpartyCheckMysemaDependencyRule = ThirdPartyRules.checkMysemaDependency();

  @ArchTest
  private final ArchRule thirdpartyAvoidSpringframeworkTransactionalRule = ThirdPartyRules
      .verifyingSpringframeworkTransactionalIsNotUsed();

  @ArchTest
  private final ArchRule thirdpartyAvoidTransactionalUseInsideApi = ThirdPartyRules
      .verifyingProperTransactionalUseFromJee();

  @ArchTest
  private final ArchRule thirdpartyAvoidJpaUseOutsideOfDataaccessOrEmbeddablesInCommonLayer = ThirdPartyRules
      .verifyingJpaUseInCompliantLayers();

  @ArchTest
  private final ArchRule thirdpartyAvoidMisuseOfHibernate = ThirdPartyRules.jpaIsUsedAsEncouraged();

}
