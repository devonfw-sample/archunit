package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.lang.ArchRule;

public class LayerRules {
  static ArchRule shouldOnlyAccessValidLayers() {

    return layeredArchitecture().consideringAllDependencies()
        .layer("common").definedBy("..common..")
        .layer("logic").definedBy("..logic..")
        .layer("dataaccess").definedBy("..dataaccess..")
        .layer("service").definedBy("..service..")
        .layer("client").definedBy("..client..")
        .layer("batch").definedBy("..batch..")

        .whereLayer("client").mayNotBeAccessedByAnyLayer()
        .whereLayer("batch").mayNotBeAccessedByAnyLayer()
        .whereLayer("service").mayOnlyBeAccessedByLayers("client")
        .whereLayer("logic").mayOnlyBeAccessedByLayers("service", "batch")
        .whereLayer("dataaccess").mayOnlyBeAccessedByLayers("logic")
        .withOptionalLayers(true).because("Dependency of technical layers violates architecture rules.");
  }
}
