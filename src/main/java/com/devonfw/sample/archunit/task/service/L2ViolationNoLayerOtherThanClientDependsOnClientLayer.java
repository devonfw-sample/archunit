package com.devonfw.sample.archunit.task.service;

import javax.inject.Inject;

import com.devonfw.sample.archunit.client.SampleClient;

public class L2ViolationNoLayerOtherThanClientDependsOnClientLayer {
    // Violation: service layer may not depend on client layer.
    @Inject
    SampleClient sampleClient;
}
