package com.devonfw.sample.archunit.task.dataaccess;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L10ViolationDataaccessLayerDependsOnServiceLayer {
    // Violation! Dataaccess layer should not depend on service layer.
    @Inject
    TaskService taskService;
}
