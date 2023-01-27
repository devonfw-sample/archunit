package com.devonfw.sample.archunit.client;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L2ViolationClientLayerDependsOnOtherLayerThanClient {
    // Violation! Client layer should not depend on service layer 
    @Inject
    TaskService taskService;
}
