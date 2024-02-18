package cajavaassessment.service;

import cajavaassessment.dto.TaskDTO;
import cajavaassessment.model.Task;
import cajavaassessment.model.User;
import cajavaassessment.repository.UserRepository;
import cajavaassessment.request.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserTaskService {

    private final UserRepository repository;
    @Autowired
    public UserTaskService(UserRepository repository) {
        this.repository = repository;
    }

    public List<TaskDTO> createTask(Long userId, TaskRequest request) {
        List<TaskDTO> tasksDTO = new ArrayList<>();

        Optional<User> fromDB = repository.findById(userId);
        if (fromDB.isPresent()) {
            User user = fromDB.get();
            List<Task> tasks = user.getTasks();
            Task task = new Task();
            task.setName(request.getName());
            task.setDescription(request.getDescription());
            task.setDateTime(request.getDateTime());
            task.setStatus(request.getStatus());
            tasks.add(task);
            user.setTasks(tasks);
            repository.save(user);

            int lastIndex = tasks.size() - 1;
            if (lastIndex >= 0) {
                Task lastTask = tasks.get(lastIndex);
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setTaskId(lastTask.getId());
                taskDTO.setName(lastTask.getName());
                taskDTO.setDescription(lastTask.getDescription());
                taskDTO.setDateTime(lastTask.getDateTime());
                taskDTO.setStatus(lastTask.getStatus());
                tasksDTO.add(taskDTO);
            }

        }
        return tasksDTO;
    }

    public List<TaskDTO> getTasks(Long userId) {
        List<TaskDTO> tasksDTO = new ArrayList<>();
        Optional<User> fromDB = repository.findById(userId);
        if (fromDB.isPresent()) {
            User user = fromDB.get();
            List<Task> tasks = user.getTasks();
            for (Task task : tasks) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setTaskId(task.getId());
                taskDTO.setName(task.getName());
                taskDTO.setDescription(task.getDescription());
                taskDTO.setDateTime(task.getDateTime());
                taskDTO.setStatus(task.getStatus());

                tasksDTO.add(taskDTO);
            }
        }
        return tasksDTO;
    }

    public List<TaskDTO> updateTask(Long userId, Long taskId, TaskRequest request) {
        List<TaskDTO> tasksDTO = new ArrayList<>();

        Optional<User> fromDB = repository.findById(userId);
        if (fromDB.isPresent()) {
            User user = fromDB.get();
            List<Task> tasks = user.getTasks();
            boolean hasChanges = false;
            for (Task task : tasks) {
                if (task.getId().equals(taskId)) {
                    if (!Objects.equals(task.getName(), request.getName())) {
                        task.setName(request.getName());
                    }
                    if (!Objects.equals(task.getDescription(), request.getDescription())) {
                        task.setDescription(request.getDescription());
                    }
                    if (!Objects.equals(task.getDateTime(), request.getDateTime())) {
                        task.setDateTime(request.getDateTime());
                    }
                    if (!Objects.equals(task.getStatus(), request.getStatus())) {
                        task.setStatus(request.getStatus());
                    }
                    hasChanges = true;
                    break;
                }
            }
            if (hasChanges) {
                user.setTasks(tasks);
                repository.save(user);
                for (Task task : tasks) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setTaskId(task.getId());
                    taskDTO.setName(task.getName());
                    taskDTO.setDescription(task.getDescription());
                    taskDTO.setDateTime(task.getDateTime());
                    taskDTO.setStatus(task.getStatus());
                    tasksDTO.add(taskDTO);
                }
            }
        }
        return tasksDTO;
    }

    public TaskDTO getTask(Long userId, Long taskId) {
        TaskDTO taskDTO = null;
        Optional<User> fromDB = repository.findById(userId);
        if (fromDB.isPresent()) {
            User user = fromDB.get();
            List<Task> tasks = user.getTasks();
            Optional<Task> found = tasks.stream().filter(task-> Objects.equals(task.getId(), taskId)).findFirst();
            if (found.isPresent()) {
                Task task = found.get();
                taskDTO = new TaskDTO();
                taskDTO.setTaskId(task.getId());
                taskDTO.setName(task.getName());
                taskDTO.setDescription(task.getDescription());
                taskDTO.setDateTime(task.getDateTime());
                taskDTO.setStatus(task.getStatus());
            }
        }
        return taskDTO;
    }

    public List<TaskDTO> deleteTask(Long userId, Long taskId) {
        List<TaskDTO> tasksDTO = new ArrayList<>();

        Optional<User> fromDB = repository.findById(userId);
        if (fromDB.isPresent()) {
            User user = fromDB.get();
            List<Task> tasks = user.getTasks();
            List<Task> newTasks = new ArrayList<>();
            for (Task task : tasks) {
                if (!Objects.equals(task.getId(), taskId)) {
                    newTasks.add(task);
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setTaskId(task.getId());
                    taskDTO.setName(task.getName());
                    taskDTO.setDescription(task.getDescription());
                    taskDTO.setDateTime(task.getDateTime());
                    taskDTO.setStatus(task.getStatus());

                    tasksDTO.add(taskDTO);
                }
            }
            user.setTasks(newTasks);
            repository.save(user);
        }
        return tasksDTO;
    }

}
