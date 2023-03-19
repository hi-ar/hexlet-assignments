package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping(path = "/{id}/previous", produces = "application/json")
    public Iterable<Course> getPrevCourses(@PathVariable long id) throws IllegalArgumentException {
        String path = courseRepository.findById(id).getPath();
        if (courseRepository.findById(id).getPath() == null || path.isEmpty()) {
            return new ArrayList<Course>();
        }
        String[] pathArr = path.split("\\.");
        List<Long> ids = Arrays.stream(pathArr)
                .map(parent -> (long) Integer.parseInt(parent))
                .toList();

        List<Course> result = (List<Course>) courseRepository.findAllById(ids);
        return result;
    }
    // END

}
