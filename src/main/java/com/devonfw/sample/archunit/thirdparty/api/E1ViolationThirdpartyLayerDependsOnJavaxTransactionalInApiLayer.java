package com.devonfw.sample.archunit.thirdparty.api;

import javax.transaction.Transactional; // Noncompliant

@Transactional
public class E1ViolationThirdpartyLayerDependsOnJavaxTransactionalInApiLayer {
    
}
