package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Task;
import exercise.repository.TaskRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "")
    public List<Task> index() {
        return taskRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task show(@PathVariable long id) {

        var task =  taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        return task;
    }

    // BEGIN
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Task make(@RequestBody Task newTask) {
        System.out.println("§§§§§ new task is: " + newTask.getId() + " / "+ newTask.getTitle() + " / "
                + newTask.getDescription() + " / "+ newTask.getCreatedAt() + " / "+ newTask.getUpdatedAt());
        taskRepository.save(newTask);
        System.out.println("§§§§§ after save is: " + newTask.getId() + " / "+ newTask.getTitle() + " / "
                + newTask.getDescription() + " / "+ newTask.getCreatedAt() + " / "+ newTask.getUpdatedAt());
        return newTask;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task modify(@PathVariable long id, @RequestBody Task newTask) {
        System.out.println("±±±±± edit #" + id + " new task is: " + newTask.getId() + " / "+ newTask.getTitle() + " / "
                + newTask.getDescription() + " / "+ newTask.getCreatedAt() + " / "+ newTask.getUpdatedAt());
        var task =  taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        taskRepository.save(task);
        System.out.println("±±±±± after save task is: " + task.getId() + " / "+ task.getTitle() + " / "
                + task.getDescription() + " / "+ task.getCreatedAt() + " / "+ task.getUpdatedAt());
        return task;
    }
    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
