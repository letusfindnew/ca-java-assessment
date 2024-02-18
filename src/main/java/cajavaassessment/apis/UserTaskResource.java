package cajavaassessment.apis;

import cajavaassessment.dto.TaskDTO;
import cajavaassessment.request.TaskRequest;
import cajavaassessment.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User Task resource
 */
@RestController
public class UserTaskResource {

    @Autowired
    private UserTaskService taskService;

    /**
     * Create a Task for a User
     *
     * @param taskRequest description
     * @return created user task
     */
    @PostMapping("/users/{userId}/tasks")
    public List<TaskDTO> createTask(@PathVariable Long userId, @RequestBody TaskRequest taskRequest) {
        return taskService.createTask(userId, taskRequest);
    }

    /**
     * List all tasks for a user
     *
     * @return list of tasks
     */
    @GetMapping("/users/{userId}/tasks")
    public List<TaskDTO> getTasks(@PathVariable Long userId) {
        return taskService.getTasks(userId);
    }

    /**
     * Update a Task
     *
     * @param taskId description
     * @return updated task
     */
    @PutMapping("/users/{userId}/tasks/{taskId}")
    public List<TaskDTO> updateTask(@PathVariable Long userId, @PathVariable Long taskId, @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(userId, taskId, taskRequest);
    }

    /**
     * Get a User Task
     *
     * @param taskId description
     * @return task info
     */
    @GetMapping("/users/{userId}/tasks/{taskId}")
    public List<TaskDTO> getTask(@PathVariable Long userId, @PathVariable Long taskId) {
        TaskDTO taskDTO = taskService.getTask(userId, taskId);
        List<TaskDTO> tasksDTO = new ArrayList<>();
        if (Objects.nonNull(taskDTO)) {
            tasksDTO.add(taskDTO);
        }
        return tasksDTO;
    }

    /**
     * Delete a Task
     *
     * @param taskId description
     * @return task info
     */
    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public List<TaskDTO> deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        return taskService.deleteTask(userId, taskId);
    }

}
