package com.devonfw.sample.archunit.task.dataaccess;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.logic.TaskItemMapper;

public class L12ViolationDataaccessLayerDependsOnLogicLayer {
    // Violation! Dataaccess layer should not depend on logic layer.
    @Inject
    TaskItemMapper taskItemMapper;
}
