package com.example.demo.student;

import com.example.demo.course.Course;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> getStudents(){

        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student student : students) {
            List<Long> coursesIds = new ArrayList<>();
            for (Course course: student.getCourses()) {
                coursesIds.add(course.getId());
            }
            StudentDto studentDto = new StudentDto(
                    student.getName(),
                    student.getEmail(),
                    student.getDob(),
                    coursesIds
            );
            studentDtos.add(studentDto);
        }
        return studentDtos;

    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);

    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id"
                    + studentId + "does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new IllegalStateException(
                        "student with id " +studentId + "does not exist"
                ));
        if (name != null && name.length() > 0 &&
            !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if (email != null && email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }
    }

    public List<Student> getStudentById(Long id) {
        return Collections.singletonList(studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student with id " + id + " does not exist")));
    }

    public List<Student> getStudentByName(String name) {
        return studentRepository.findByNameStartingWith(name);
    }

    public List<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmailStartingWith(email);
    }

    public List<Student> getStudentsBornBetween(String dobAfter, String dobBefore) {
        LocalDate after = LocalDate.parse(dobAfter);
        LocalDate before = LocalDate.parse(dobBefore);
        return studentRepository.findByDobBetween(after, before);
    }

    public List<Student> getStudentsBornAfter(String dobAfter) {
        LocalDate after = LocalDate.parse(dobAfter);
        return studentRepository.findByDobAfter(after);
    }

    public List<Student> getStudentsBornBefore(String dobBefore) {
        LocalDate before = LocalDate.parse(dobBefore);
        return studentRepository.findByDobBefore(before);
    }
}
