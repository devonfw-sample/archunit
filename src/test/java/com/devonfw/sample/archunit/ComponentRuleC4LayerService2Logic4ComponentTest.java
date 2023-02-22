package com.devonfw.sample.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
/**
 * verifying that the service layer of a component may not depend on the
 * logic layer of another component.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleC4LayerService2Logic4ComponentTest {

    static final String PROJECT_NAME = "com.devonfw.sample.archunit";
    static final String DEFAULT_COMPONENT = PROJECT_NAME + ".general";
    @ArchTest
    static final ArchRule no_dependencies_from_componentb_to_any_other_component =
            slices().assignedFrom(inComponentServiceOrLogicLayer())
                    .namingSlices("$1")
                    .should().notDependOnEachOther();

    @ArchTest
    static final ArchRule no_cycles_by_method_calls_between_slices =
            slices().matching("..archunit.(*).(service)..").namingSlices("$2 of $1").should().notDependOnEachOther();

    private static SliceAssignment inComponentServiceOrLogicLayer() {
        return new SliceAssignment() {
            @Override
            public String getDescription() {
                return "the service layer of one component and a different components logic layer";
            }

            @Override
            public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
                String sourcePackageName = javaClass.getPackageName();
                // All components except the default component.
                if(sourcePackageName.contains(PROJECT_NAME) && !sourcePackageName.contains(DEFAULT_COMPONENT)){
                    for (JavaAccess<?> access : javaClass.getAccessesFromSelf()) {
                        String targetPackageName = access.getTargetOwner().getName();
                        if (sourcePackageName.contains(".service") && !targetPackageName.contains(".logic")) {
                            String componentName = javaClass.getPackageName().substring(PROJECT_NAME.length() + 1, sourcePackageName.length()).replace(".service", "");
                            if(!targetPackageName.contains(componentName)){
                                return SliceIdentifier.of(componentName);
                            }
                        }
                        if(!sourcePackageName.contains("service") && targetPackageName.contains(".logic")){
                            String componentName = javaClass.getPackageName().substring(PROJECT_NAME.length() + 1, sourcePackageName.length()).replace(".logic", "");
                            if(!targetPackageName.contains(componentName)) {
                                return SliceIdentifier.of(componentName);
                            }
                            
                        }
                    }
                }

                return SliceIdentifier.ignore();
            }
        };
    }
}
