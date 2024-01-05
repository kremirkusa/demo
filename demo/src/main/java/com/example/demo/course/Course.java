package com.example.demo.course;

import com.example.demo.student.Student;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;
    private String name;
    private String capacity;
    @ManyToMany
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public Course(Long id, String name, String capacity, List<Student> students) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.students = students;
    }

    public Course() {

    }

    public Course(String name, String capacity, List<Student> students) {
        this.name = name;
        this.capacity = capacity;
        this.students = students;
    }

    public Course(Long id, String name, String capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Course(String name, String capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
