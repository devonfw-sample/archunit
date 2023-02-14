package com.devonfw.sample.archunit.task.service;

import javax.inject.Inject;

import com.devonfw.sample.archunit.batch.SampleBatchClass;

public class L6ViolationServiceLayerDependsOnBatchLayer {
    // Violation! Service layer should not depend on batch layer
    @Inject
    SampleBatchClass sampleBatchClass;
}
