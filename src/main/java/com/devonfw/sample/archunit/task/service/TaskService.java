package com.devonfw.sample.archunit.task.service;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.devonfw.sample.archunit.task.common.TaskItemEto;
import com.devonfw.sample.archunit.task.common.TaskListEto;
import com.devonfw.sample.archunit.task.logic.UcDeleteTaskItem;
import com.devonfw.sample.archunit.task.logic.UcDeleteTaskList;
import com.devonfw.sample.archunit.task.logic.UcFindTaskItem;
import com.devonfw.sample.archunit.task.logic.UcFindTaskList;
import com.devonfw.sample.archunit.task.logic.UcSaveTaskItem;
import com.devonfw.sample.archunit.task.logic.UcSaveTaskList;

/**
 * Rest service for task component with {@link com.devonfw.sample.archunit.task.common.TaskList} and
 * {@link com.devonfw.sample.archunit.task.common.TaskItem}.
 */
@Path("/task")
public class TaskService {

  @Inject
  private UcFindTaskList ucFindTaskList;

  @Inject
  private UcSaveTaskList ucSaveTaskList;

  @Inject
  private UcDeleteTaskList ucDeleteTaskList;

  @Inject
  private UcFindTaskItem ucFindTaskItem;

  @Inject
  private UcSaveTaskItem ucSaveTaskItem;

  @Inject
  private UcDeleteTaskItem ucDeleteTaskItem;

  /**
   * @param taskList the {@link TaskListEto} to save (insert or update).
   * @return response
   */
  @POST
  @Path("/list")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response saveTask(@Valid TaskListEto taskList) {

    Long taskListId = this.ucSaveTaskList.save(taskList);
    if (taskList.getId() == null || taskList.getId() != taskListId) {
      return Response.created(URI.create("/task/list/" + taskListId)).build();
    }
    return Response.ok().build();
  }

  /**
   * @param id the {@link TaskListEto#getId() primary key} of the requested {@link TaskListEto}.
   * @return the {@link TaskListEto} for the given {@code id}.
   */
  @GET
  @Path("/list/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public TaskListEto findTaskList(@PathParam("id") Long id) {

    TaskListEto task = this.ucFindTaskList.findById(id);
    if (task == null) {
      throw new NotFoundException("TaskList with id " + id + " does not exist.");
    }
    return task;
  }

  /**
   * @param id the {@link TaskListEto#getId() primary key} of the {@link TaskListEto} to delete.
   */
  @DELETE
  @Path("/list/{id}")
  public void deleteTaskList(@PathParam("id") Long id) {

    this.ucDeleteTaskList.delete(id);
  }

  /**
   * @param item the {@link TaskItemEto} to save (insert or update).
   * @return response
   */
  @POST
  @Path("/item")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response saveTaskItem(@Valid TaskItemEto item) {

    Long taskItemId = this.ucSaveTaskItem.save(item);
    if (item.getId() == null || item.getId() != taskItemId) {
      return Response.created(URI.create("/task/item/" + taskItemId)).entity(taskItemId).build();
    }
    return Response.ok(taskItemId).build();
  }

  /**
   * @param id the {@link TaskItemEto#getId() primary key} of the {@link TaskItemEto} to find.
   * @return the {@link TaskItemEto} for the given {@code id}.
   */
  @GET
  @Path("/item/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public TaskItemEto findTaskItem(@PathParam("id") Long id) {

    TaskItemEto item = this.ucFindTaskItem.findById(id);
    if (item == null) {
      throw new NotFoundException("TaskItem with id " + id + " does not exist.");
    }
    return item;
  }

  /**
   * @param id the {@link TaskItemEto#getId() primary key} of the {@link TaskItemEto} to delete.
   */
  @DELETE
  @Path("/item/{id}")
  public void deleteTaskItem(@PathParam("id") Long id) {

    this.ucDeleteTaskItem.delete(id);
  }

  public String thisMethodIsUsedToViolateTheAccessOfItFromLogicLayer() {
    return "Hello from Service";
  }
}
