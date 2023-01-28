package com.devonfw.sample.archunit.task.service;

import javax.inject.Inject;

import com.devonfw.sample.archunit.batch.SampleTaskImport;

public class L6ViolationServiceLayerDependsOnBatchLayer {
    // Violation! Service layer should not depend on batch layer
    @Inject
    SampleTaskImport sampleTaskImport;
}
