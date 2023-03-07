package com.devonfw.sample.archunit.thirdparty.dataaccess;

import org.springframework.transaction.annotation.Transactional; // Noncompliant the use of JEE standard javax.transaction.Transactional is encouraged.

@Transactional
public class E1ViolationThirdpartyLayerDependsOnDiscouragedTransactionalAnnotation {
    
}
