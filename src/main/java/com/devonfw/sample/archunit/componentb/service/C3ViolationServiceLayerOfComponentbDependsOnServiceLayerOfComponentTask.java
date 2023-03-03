package com.devonfw.sample.archunit.componentb.service;

import com.devonfw.sample.archunit.task.service.TaskService;

public class C3ViolationServiceLayerOfComponentbDependsOnServiceLayerOfComponentTask {
    TaskService taskService; // noncompliant
}
