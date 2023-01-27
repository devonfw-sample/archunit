package com.devonfw.sample.archunit.task.logic;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L9ViolationLogicLayerDependsAccessesServiceLayer {
    @Inject
    TaskService taskService;

    public String thisMethodIsErroneouslyUsingTheServiceLayer()
    {
        String helloFromService = this.taskService.thisMethodIsUsedToViolateTheAccessOfItFromLogicLayer();
        return "Logic Layer calling service method...: " + helloFromService;
    }
}
