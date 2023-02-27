package com.devonfw.sample.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;

/**
 * verifying that the dataaccess layer of one component does not depend on the dataaccess layer of
 * another component.
 */

// medium severity

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleC6LayerDataaccess2Dataaccess4ComponentTest {
    @ArchTest
    static final ArchRule no_dataaccess_layer_depends_on_dataaccess_layer_of_another_component =
    slices()
        .matching("..archunit.(*).dataaccess..")
        .namingSlices("$1 dataaccess")
        .should()
        .notDependOnEachOther();
}