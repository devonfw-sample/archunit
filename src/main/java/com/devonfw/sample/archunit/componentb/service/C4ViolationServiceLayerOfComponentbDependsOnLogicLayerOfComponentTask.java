package com.devonfw.sample.archunit.componentb.service;

import com.devonfw.sample.archunit.componentb.logic.C4PositiveTest;
import com.devonfw.sample.archunit.general.common.ApplicationEntity;
import com.devonfw.sample.archunit.general.logic.AbstractUc;
import com.devonfw.sample.archunit.task.common.TaskItem;
import com.devonfw.sample.archunit.task.dataaccess.TaskItemEntity;
import com.devonfw.sample.archunit.task.logic.UcDeleteTaskItem;

public class C4ViolationServiceLayerOfComponentbDependsOnLogicLayerOfComponentTask {
  // Forbidden dependency covered
  UcDeleteTaskItem ucDeleteTaskItem; // noncompliant (different components logic layer)

  // Forbidden dependencies uncovered

  TaskItem taskItem; // noncompliant (wont violate C4 test. Should be caught by other rules)

  TaskItemEntity taskItemEntity; // noncompliant (wont violate C4 test. Should be caught by layer rules)

  // Allowed dependencies
  C4PositiveTest c4PositiveTest; // compliant (same components logic layer)

  ApplicationEntity applicationEntity; // compliant (projects default component)

  AbstractUc abstractUc; // compliant (projects default component)

}
