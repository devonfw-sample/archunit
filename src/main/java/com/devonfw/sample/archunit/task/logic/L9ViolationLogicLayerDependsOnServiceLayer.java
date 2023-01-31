package com.devonfw.sample.archunit.task.logic;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L9ViolationLogicLayerDependsOnServiceLayer {
    // Violation! Logic layer should not depend on service layer
    @Inject
    TaskService taskService;

}
