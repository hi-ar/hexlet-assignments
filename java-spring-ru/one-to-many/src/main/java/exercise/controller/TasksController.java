package exercise.controller;

import java.util.List;
import java.util.Optional;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskMapper taskMapper;

    @GetMapping
    public List<TaskDTO> showAll() {
        return taskRepository.findAll().stream().map(t -> taskMapper.map(t)).toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return taskMapper.map(t);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskDTO create(@RequestBody @Valid TaskCreateDTO dto) {
        System.out.println("§§§DTO: " + dto.getAssigneeId() + " / " +  dto.getTitle() +" / " + dto.getDescription());
        var t = taskMapper.map(dto);
        System.out.println("§§§Task: " + t.getAssignee().getId() +" / " + t.getTitle() + " / " + t.getDescription());
        //
        User u = userRepository.findById(dto.getAssigneeId()).orElseThrow();
        System.out.println("§§§ Size of list before: " + u.getTasks().size());
        //
        taskRepository.save(t);
        u.addTask(t);
        System.out.println("§§§ Size of list after: " + u.getTasks().size());
        System.out.println("new task now is: " + u.getTasks().get(0).getTitle());
        return taskMapper.map(t);
    }

    @PutMapping(path = "/{id}")
    public TaskDTO edit(@PathVariable long id, @RequestBody @Valid TaskUpdateDTO dto) {
        System.out.println("§§§id of task is: " + id);
        System.out.println("§§§DTO " + dto.getAssigneeId() + " / " +  dto.getTitle() +" / " + dto.getDescription());
        Task t = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("T not found"));
        //проверяем была ли эта задача поручена другому юзеру? Если да, удаляем ее у него
//        Optional<User> oldExec = userRepository.findById(t.getAssignee().getId());
//        if (oldExec.isPresent()) {
//            oldExec.get().removeTask(t);
//        }
        System.out.println("§§§Task before: " + t.getAssignee().getId() +" / " + t.getTitle() + " / " + t.getDescription());
        taskMapper.update(dto, t);
        t.setAssignee(userRepository.getReferenceById(dto.getAssigneeId()));
        System.out.println("§§§Task after: " + t.getAssignee().getId() +" / " + t.getTitle() + " / " + t.getDescription());
        taskRepository.save(t);
        User newExec = userRepository.findById(t.getAssignee().getId()).get();
        newExec.addTask(t);
        return taskMapper.map(t);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        taskRepository.deleteById(id);
    }
    // END
}
