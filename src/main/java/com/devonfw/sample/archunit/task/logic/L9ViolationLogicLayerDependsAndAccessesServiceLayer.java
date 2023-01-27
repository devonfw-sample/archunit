package com.devonfw.sample.archunit.task.logic;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.service.TaskService;

public class L9ViolationLogicLayerDependsAndAccessesServiceLayer {
    // Violation! Logic layer should not depend on dataaccess layer nor acces it.
    @Inject
    TaskService taskService;

    public String thisMethodIsErroneouslyUsingTheServiceLayer()
    {
        String helloFromService = this.taskService.thisMethodIsUsedToViolateTheAccessOfItFromLogicLayer();
        return "Logic Layer calling service method...: " + helloFromService;
    }
}
