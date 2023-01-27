package com.devonfw.sample.archunit.client;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.dataaccess.TaskItemRepository;

public class L4ViolationClientLayerDependsOnDataaccessLayer {
    // Violation! Client layer should not depend on dataaccess layer
    @Inject
    TaskItemRepository taskItemRepository;
}
