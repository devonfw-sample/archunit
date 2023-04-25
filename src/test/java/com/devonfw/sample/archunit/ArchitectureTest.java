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

  // Component rules
  @ArchTest
  private static ArchRule componentsShouldNotHaveCyclicDependencies = ComponentRules.shouldNotHaveCyclicDependencies();

  @ArchTest
  private static ArchRule serviceLayerShouldNotDependOnServiceLayerOfAnotherComponent = ComponentRules
      .serviceLayerShouldNotDependOnServiceLayerOfAnotherComponent();

  @ArchTest
  private static ArchRule serviceLayerShouldNotDependOnLogicLayerOfAnotherComponent = ComponentRules
      .serviceLayerShouldNotDependOnLogicLayerOfAnotherComponent();

  @ArchTest
  private static ArchRule logicLayerShouldNotDependOnDataaccessLayerOfAnotherComponent = ComponentRules
      .logicLayerShouldNotDependOnDataaccessLayerOfAnotherComponent();

  @ArchTest
  private static ArchRule dataaccessLayerShouldNotDependOnDataaccessLayerOfAnotherComponent = ComponentRules
      .dataaccessLayerShouldNotDependOnDataaccessLayerOfAnotherComponent();

  @ArchTest
  private static ArchRule batchLayerShouldNotDependOnLogicLayerOfAnotherComponent = ComponentRules
      .batchLayerShouldNotDependOnLogicLayerOfAnotherComponent();

  @ArchTest
  private static ArchRule componentGeneralDoesNotDependOnAnotherComponent = ComponentRules.generalDoesNotDependOnAnotherComponent();

  // Layer rules
  @ArchTest
  private static ArchRule layersShouldOnlyHaveValidDependencies = LayerRules.layersShouldOnlyHaveValidDependencies();

  // Convention rules
  @ArchTest
  private static ArchRule compositeTransferObjectsShouldHaveCtoSuffixAndCommonLayer = ConventionRules
      .compositeTransferObjectsShouldHaveCtoSuffixAndCommonLayer();

  @ArchTest
  private static ArchRule entitiesShouldHaveEntitySuffixAndDataaccessLayer = ConventionRules
      .entitiesShouldHaveEntitySuffixAndDataaccessLayer();

  @ArchTest
  private static ArchRule entityTransferObjectsShouldHaveEtoSuffixAndCommonLayerAndImplementEntityInterface = ConventionRules
      .entityTransferObjectsShouldHaveEtoSuffixAndCommonLayerAndImplementEntityInterface();

  @ArchTest
  private static ArchRule useCasesShouldHaveLogicLayer = ConventionRules.useCasesShouldHaveLogicLayer();

  @ArchTest
  private static ArchRule mappersShouldHaveMapperSuffixAndLogicLayer = ConventionRules.mappersShouldHaveMapperSuffixAndLogicLayer();

  @ArchTest
  private static ArchRule restServicesShouldHaveServiceSuffixAndServiceLayer = ConventionRules
      .restServicesShouldHaveServiceSuffixAndServiceLayer();

  @ArchTest
  private static ArchRule jpaRepositoriesShouldHaveEntityNamePrefixRepositorySuffixAndDataaccessLayer = ConventionRules
      .jpaRepositoriesShouldHaveEntityNamePrefixRepositorySuffixAndDataaccessLayer();

  @ArchTest
  private static ArchRule useCasesShouldHaveUcPrefix = ConventionRules.useCasesShouldHaveUcPrefix();

  @ArchTest
  private static ArchRule embeddablesShouldHaveEmbeddableSuffixAndCommonLayer = ConventionRules
      .embeddablesShouldHaveEmbeddableSuffixAndCommonLayer();

  // Package Rules
  @ArchTest
  private static ArchRule allPackagesShouldHaveValidStructure = PackageRules.allPackagesShouldHaveValidStructure();

  // Security Rules
  @ArchTest
  private static ArchRule useCaseMethodsShouldBeAnnotatedWithSecurityAnnotation = SecurityRules
      .useCaseMethodsShouldBeAnnotatedWithSecurityAnnotation();

  @ArchTest
  private static ArchRule shouldNotCreateQueryFromString = SecurityRules.shouldNotCreateQueryFromString();

  // Third-Party Rules
  @ArchTest
  private static ArchRule shouldNotUseGuavaObjects = ThirdPartyRules.shouldNotUseGuavaObjects();

  @ArchTest
  private static ArchRule shouldNotUseJpaConvert = ThirdPartyRules.shouldNotUseJpaConvert();

  @ArchTest
  private static ArchRule shouldNotUseMysemaDependency = ThirdPartyRules.shouldNotUseMysemaDependency();

  @ArchTest
  private static ArchRule shouldNotUseSpringTransactional = ThirdPartyRules.shouldNotUseSpringTransactional();

  @ArchTest
  private static ArchRule shouldNotUseTransactionalInApi = ThirdPartyRules.shouldNotUseTransactionalInApi();

  @ArchTest
  private static ArchRule shouldUseJpaOnlyInDataaccessOrEmbeddables = ThirdPartyRules.shouldUseJpaOnlyInDataaccessExceptEmbeddables();

  @ArchTest
  private static ArchRule shouldUseHibernateOnlyInDataaccess = ThirdPartyRules.shouldUseHibernateOnlyInDataaccess();

  @ArchTest
  private static ArchRule shouldUseJpaInsteadOfHibernate = ThirdPartyRules.shouldUseJpaInsteadOfHibernate();

  @ArchTest
  private static ArchRule jpaIsUsedAsEncouraged = ThirdPartyRules.jpaIsUsedAsEncouraged();

}
