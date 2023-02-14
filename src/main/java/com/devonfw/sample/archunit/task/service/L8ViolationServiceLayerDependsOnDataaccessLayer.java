package com.devonfw.sample.archunit.task.service;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.dataaccess.TaskItemRepository;

public class L8ViolationServiceLayerDependsOnDataaccessLayer {
    // Violation! Service layer should not depend on dataaccess layer
    @Inject
    TaskItemRepository taskItemRepository;
}
