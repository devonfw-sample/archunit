package com.devonfw.sample.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.lang.ArchRule;

/**
 * Contains the {@link ArchRule}s to verify the {@link com.devonfw.sample.archunit.PackageStructure#getLayer() layer}s.
 */
class LayerRules extends AbstractRules {
  /**
   * @return {@link ArchRule} verifying that the {@link com.devonfw.sample.archunit.PackageStructure#getLayer() layer}s
   *         only have valid dependencies to other layers.
   */
  static ArchRule layersShouldOnlyHaveValidDependencies() {

    return layeredArchitecture().consideringAllDependencies() //
        .layer(PackageStructure.LAYER_COMMON).definedBy(".." + PackageStructure.LAYER_COMMON + "..") //
        .layer(PackageStructure.LAYER_LOGIC).definedBy(".." + PackageStructure.LAYER_LOGIC + "..") //
        .layer(PackageStructure.LAYER_DATA_ACCESS).definedBy(".." + PackageStructure.LAYER_DATA_ACCESS + "..") //
        .layer(PackageStructure.LAYER_SERVICE).definedBy(".." + PackageStructure.LAYER_SERVICE + "..") //
        .layer(PackageStructure.LAYER_CLIENT).definedBy(".." + PackageStructure.LAYER_CLIENT + "..") //
        .layer(PackageStructure.LAYER_BATCH).definedBy(".." + PackageStructure.LAYER_BATCH + "..") //
        .whereLayer(PackageStructure.LAYER_CLIENT).mayNotBeAccessedByAnyLayer() //
        .whereLayer(PackageStructure.LAYER_BATCH).mayNotBeAccessedByAnyLayer() //
        .whereLayer(PackageStructure.LAYER_SERVICE).mayOnlyBeAccessedByLayers(PackageStructure.LAYER_CLIENT) //
        .whereLayer(PackageStructure.LAYER_LOGIC).mayOnlyBeAccessedByLayers(PackageStructure.LAYER_SERVICE, PackageStructure.LAYER_BATCH) //
        .whereLayer(PackageStructure.LAYER_DATA_ACCESS).mayOnlyBeAccessedByLayers(PackageStructure.LAYER_LOGIC) //
        .withOptionalLayers(true) //
        .because("dependencies between layers have to follow layered architecture");
  }
}
