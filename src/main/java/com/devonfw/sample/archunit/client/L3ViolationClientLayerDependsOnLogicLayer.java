package com.devonfw.sample.archunit.client;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.logic.TaskItemMapper;

public class L3ViolationClientLayerDependsOnLogicLayer {
    // Violation! Client layer should not depend on logic layer
    @Inject
    TaskItemMapper taskItemMapper;
}
