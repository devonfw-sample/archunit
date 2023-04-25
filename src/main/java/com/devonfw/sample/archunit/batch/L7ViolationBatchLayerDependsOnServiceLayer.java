package com.devonfw.sample.archunit.batch;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L7ViolationBatchLayerDependsOnServiceLayer {
    // Violation! Batch layer should not depend on service layer 
    @Inject
    TaskService taskService;
}
