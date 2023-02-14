package com.devonfw.sample.archunit.batch;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.dataaccess.TaskItemRepository;

public class L11ViolationBatchLayerDependsOnDataaccessLayer {
    // Violation! Batch layer should not depend on dataaccess layer 
    @Inject
    TaskItemRepository taskItemRepository;
}
