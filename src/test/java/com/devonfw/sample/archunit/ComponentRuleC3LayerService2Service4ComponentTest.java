package com.devonfw.sample.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;

/**
 * verifying that the service layer of one component does not depend on the service layer of
 * another component.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleC3LayerService2Service4ComponentTest {
    @ArchTest
    static final ArchRule no_service_layer_depends_on_service_layer_of_another_component =
        slices()
        .matching("..archunit.(*).service..")
        .namingSlices("$1 service").should().notDependOnEachOther()
        .as("Code from service layer shall not depend on service layer of a different component");
}
