package com.example.demo.course;


import com.example.demo.student.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {

        this.courseRepository = courseRepository;
    }

    public List<CourseDto> getCourses(){

        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses){
            List<Long> studentIds = new ArrayList<>();
            for(Student student : course.getStudents()){
                studentIds.add(student.getId());
            }
            CourseDto courseDto = new CourseDto(
                    course.getName(),
                    course.getCapacity(),
                    studentIds
            );
            courseDtos.add(courseDto);
        }
        return courseDtos;
    }

    public void addNewCourse(Course course) {
        Optional<Course> courseOptional = courseRepository.findByName(course.getName());

        if(courseOptional.isPresent()){
            throw new IllegalStateException("course name taken");
        }
        courseRepository.save(course);

    }

    public void deleteCourse(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);
        if(!exists){
            throw new IllegalStateException("course with id"
                    + courseId + "does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, String name, String capacity) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new IllegalStateException(
                        "Course with id " + courseId + "does not exist"
                ));
        if (name != null && !name.isEmpty() && !Objects.equals(name,course.getName())){
            Optional<Course> courseOptional = courseRepository.findByName(course.getName());

            if(courseOptional.isPresent()){
                throw new IllegalStateException("course name taken");
            }
            course.setName(name);
        }

        if (capacity != null && !capacity.isEmpty() && !Objects.equals(capacity,course.getCapacity())){
            course.setCapacity(capacity);
        }
    }


    public List<Course> getCourseById(Long id) {
        return Collections.singletonList(courseRepository.findById(id).orElseThrow(() -> new IllegalStateException("Course with id " + id + " does not exist")));
    }

    public List<Course> getCourseByName(String name) {
        return courseRepository.findByNameStartingWith(name);
    }

    public List<Course> getCoursesByCapacity(String capacity) {
        return courseRepository.findByCapacityStartingWith(capacity);
    }

    public Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(()-> new IllegalStateException(
                        "Course with id " + courseId + "does not exist"
                ));
    }
}
