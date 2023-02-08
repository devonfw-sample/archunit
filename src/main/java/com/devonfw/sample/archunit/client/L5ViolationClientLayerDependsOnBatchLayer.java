package com.devonfw.sample.archunit.client;

import javax.inject.Inject;

import com.devonfw.sample.archunit.batch.SampleBatchClass;

public class L5ViolationClientLayerDependsOnBatchLayer {
    // Violation! Client layer should not depend on batch layer
    @Inject
    SampleBatchClass sampleBatchClass;
}
