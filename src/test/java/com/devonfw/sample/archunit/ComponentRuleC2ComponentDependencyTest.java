package com.devonfw.sample.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.Slice;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameContaining;
import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption;
/**
 * This is an exemplary component dependency test. It is assumed that the business architecture defines two components:
 * 
 * {
 *  "architecture": {
 *  "components": [
 *        {"name":"task","dependencies":["componentb"]},
 *        {"name":"componentb","dependencies":[]}
 *      ]
 *  }
 *}
 * Component task is allowed to have dependencies towards componentb but not vice versa.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleC2ComponentDependencyTest {

    private static DescribedPredicate<Slice> containDescription(String descriptionPart) {
        return new DescribedPredicate<Slice>("contain description '%s'", descriptionPart) {
            @Override
            public boolean test(Slice input) {
                return input.getDescription().contains(descriptionPart);
            }
        };
    }

    // No components should depend on each other except the general component which can be used by any other component.
    static final ArchRule components_should_only_use_their_own_slice_with_custom_ignore =
            slices().matching("..archunit.(*)..").namingSlices("Component $1")
                    .as("Components").should().notDependOnEachOther()
                    // default business component general holds code which doesn't belong to a specific component
                    .ignoreDependency(alwaysTrue(), nameMatching(".*general.*"));

    // Components: componentb and task should not depend on each other (bi-directional).
    static final ArchRule specific_components_should_only_use_their_own_slice =
            slices().matching("..archunit.(*)..").namingSlices("Component $1")
                    .that(containDescription("Component task"))
                    .or(containDescription("Component componentb"))
                    .as("Component Task or componentb").should().notDependOnEachOther();

    // componentb should not depend on the task component but not vice versa (unidirectional).
    @ArchTest
    static final ArchRule component_task_may_depend_on_componentb =
            slices().matching("..archunit.(*)..").namingSlices("Component $1")
                    .that(containDescription("Component task"))
                    .or(containDescription("Component componentb"))
                    .should().notDependOnEachOther()
                    .as("Component task is allowed to depend on component componentb but not vice versa.")
                    .ignoreDependency(nameContaining("task"), nameContaining("componentb"));
}
