package com.devonfw.sample.archunit.thirdparty.service.api;

import javax.transaction.Transactional; // Noncompliant

@Transactional
public class E1ViolationThirdpartyLayerDependsOnJavaxTransactionalInApiLayer {

}
