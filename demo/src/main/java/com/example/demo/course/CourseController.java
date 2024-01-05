package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {

    private final CourseService courseService;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseController(CourseService courseService, StudentRepository studentRepository) {
        this.courseService = courseService;
        this.studentRepository = studentRepository;
    }


    @GetMapping
    public List<CourseDto> getcourse(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String capacity
    ){

        if(id != null){
            return courseService.getCourseById(id).stream()
                    .map(course -> new CourseDto(
                            course.getName(),
                            course.getCapacity(),
                            course.getStudents().stream()
                                    .map(Student::getId)
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        }
        else if(name != null){
            return courseService.getCourseByName(name).stream()
                    .map(course -> new CourseDto(
                            course.getName(),
                            course.getCapacity(),
                            course.getStudents().stream()
                                    .map(Student::getId)
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        }
        else if (capacity != null) {
            return courseService.getCoursesByCapacity(capacity).stream()
                    .map(course -> new CourseDto(
                            course.getName(),
                            course.getCapacity(),
                            course.getStudents().stream()
                                    .map(Student::getId)
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        } else {
            return courseService.getCourses();
        }
    }
    @PostMapping
    public void registerNewCourse(
            @RequestBody Course course){
        courseService.addNewCourse(course);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId){

        courseService.deleteCourse(courseId);
    }

    @PutMapping(path = "{courseId}")
    public void updateCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        courseService.updateCourse(courseId, name, email);
    }

    @PostMapping(path = "{courseId}")
    public void addStudents(
            @RequestBody List<Long> studentIds,
            @PathVariable("courseId") Long courseId
    ) {
        Course course = courseService.findCourseById(courseId);
        int capacity = Integer.parseInt(course.getCapacity());

        List<Student> students = studentRepository.findAllById(studentIds);
        for(Student student : students){
            int curr = course.getStudents().size();
            if(curr >= capacity){
                throw new IllegalStateException("Course capacity filled!");
            }

            course.getStudents().add(student);
        }
    }
}
