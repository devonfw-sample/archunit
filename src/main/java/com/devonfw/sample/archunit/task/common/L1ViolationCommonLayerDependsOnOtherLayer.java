package com.devonfw.sample.archunit.task.common;

import javax.inject.Inject;

import com.devonfw.sample.archunit.task.logic.TaskItemMapper;

public class L1ViolationCommonLayerDependsOnOtherLayer {
    @Inject
    TaskItemMapper taskItemMapper;
}
