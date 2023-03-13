package com.devonfw.sample.archunit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link PackageStructure}.
 */
public class PackageStructureTest extends Assertions {

  /** Test of {@link PackageStructure#of(String)} with valid examples. */
  @Test
  public void testValid() {

    checkValid("com.customer.app.mycomponent.dataaccess.api.detail1.detail2", "mycomponent", "dataaccess", "api",
        "detail1.detail2");
    checkValid("com.customer.app.foo.logic.uc", "foo", "logic", "", "uc");
    checkValid("com.customer.app.bar.common", "bar", "common");
    checkValid("com.customer.app.some.service.rest", "some", "service", "", "rest");
  }

  /** Test of {@link PackageStructure#of(String)} with invalid examples. */
  @Test
  public void testInvalid() {

    checkInvalid("com.customer.app.mycomponent.dataaacceess.api.detail1.detail2");
    checkInvalid("com.customer.app.mycomponent.api");
    checkInvalid("com.customer.app.mycomponent");
    checkInvalid("com.customer.app");
    checkInvalid("com.customer");
    checkInvalid("com");
    checkInvalid(".");
  }

  private PackageStructure checkValid(String pkgName, String component, String layer) {

    return checkValid(pkgName, component, layer, "");
  }

  private PackageStructure checkValid(String pkgName, String component, String layer, String scope) {

    return checkValid(pkgName, component, layer, scope, "");
  }

  private PackageStructure checkValid(String pkgName, String component, String layer, String scope, String detail) {

    return checkValid(pkgName, component, layer, scope, detail, "com.customer.app");
  }

  // given
  private PackageStructure checkValid(String pkgName, String component, String layer, String scope, String detail,
      String root) {

    // when
    PackageStructure pkg = PackageStructure.of(pkgName);
    // then
    assertThat(pkg.getPackage()).isEqualTo(pkgName);
    assertThat(pkg.getRoot()).isEqualTo(root);
    assertThat(pkg.getComponent()).isEqualTo(component);
    assertThat(pkg.getLayer()).isEqualTo(layer);
    checkLayer(layer, pkg);
    assertThat(pkg.getScope()).isEqualTo(scope);
    checkScope(scope, pkg);
    assertThat(pkg.getDetail()).isEqualTo(detail);
    assertThat(pkg.hasDetail()).isEqualTo(!detail.isEmpty());
    assertThat(pkg.isValid()).isTrue();
    return pkg;
  }

  private void checkScope(String scope, PackageStructure pkg) {

    if (scope.isEmpty()) {
      assertThat(pkg.hasScope()).isFalse();
    } else {
      assertThat(pkg.hasScope()).isTrue();
      boolean isApi = false;
      boolean isBase = false;
      boolean isImpl = false;
      if (PackageStructure.SCOPE_API.equals(scope)) {
        isApi = true;
      } else if (PackageStructure.SCOPE_BASE.equals(scope)) {
        isBase = true;
      } else if (PackageStructure.SCOPE_IMPLEMENTATION.equals(scope)) {
        isImpl = true;
      } else {
        throw new IllegalStateException(scope);
      }
      assertThat(pkg.isScopeApi()).isEqualTo(isApi);
      assertThat(pkg.isScopeBase()).isEqualTo(isBase);
      assertThat(pkg.isScopeImpl()).isEqualTo(isImpl);
    }
  }

  private void checkLayer(String layer, PackageStructure pkg) {

    boolean isService = false;
    boolean isLogic = false;
    boolean isDataAccess = false;
    boolean isCommon = false;
    boolean isBatch = false;
    boolean isClient = false;
    if (PackageStructure.LAYER_SERVICE.equals(layer)) {
      isService = true;
    } else if (PackageStructure.LAYER_LOGIC.equals(layer)) {
      isLogic = true;
    } else if (PackageStructure.LAYER_DATA_ACCESS.equals(layer)) {
      isDataAccess = true;
    } else if (PackageStructure.LAYER_COMMON.equals(layer)) {
      isCommon = true;
    } else if (PackageStructure.LAYER_BATCH.equals(layer)) {
      isBatch = true;
    } else if (PackageStructure.LAYER_CLIENT.equals(layer)) {
      isClient = true;
    } else {
      throw new IllegalStateException(layer);
    }
    assertThat(pkg.isLayerService()).isEqualTo(isService);
    assertThat(pkg.isLayerLogic()).isEqualTo(isLogic);
    assertThat(pkg.isLayerDataAccess()).isEqualTo(isDataAccess);
    assertThat(pkg.isLayerCommon()).isEqualTo(isCommon);
    assertThat(pkg.isLayerBatch()).isEqualTo(isBatch);
    assertThat(pkg.isLayerClient()).isEqualTo(isClient);
  }

  // given
  private void checkInvalid(String pkgName) {

    // when
    PackageStructure pkg = PackageStructure.of(pkgName);
    // then
    assertThat(pkg.getPackage()).isEqualTo(pkgName);
    assertThat(pkg.getRoot()).isEqualTo("");
    assertThat(pkg.getComponent()).isEqualTo("");
    assertThat(pkg.getLayer()).isEqualTo("");
    assertThat(pkg.getScope()).isEqualTo("");
    assertThat(pkg.hasScope()).isFalse();
    assertThat(pkg.getDetail()).isEqualTo("");
    assertThat(pkg.hasDetail()).isFalse();
    assertThat(pkg.isValid()).isFalse();

  }

}
