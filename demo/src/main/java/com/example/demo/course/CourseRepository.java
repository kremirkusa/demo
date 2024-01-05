package com.example.demo.course;

import com.example.demo.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameStartingWith(String name);

    List<Course> findByCapacityStartingWith(String email);

    Optional<Course> findByName(String name);


}
