package com.devonfw.sample.archunit.task.dataaccess;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L10ViolationDataaccessLayerDependsOnServiceLayer {
    @Inject
    TaskService taskService;
}
