package com.example.demo.course;

import java.util.List;

public class CourseDto {
    private String name;
    private String capacity;
    private List<Long> studentIds;

    public CourseDto(String name, String capacity, List<Long> studentIds) {
        this.name = name;
        this.capacity = capacity;
        this.studentIds = studentIds;
    }

    public CourseDto(String name, String capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public CourseDto() {
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

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
