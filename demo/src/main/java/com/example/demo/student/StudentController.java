package com.example.demo.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getStudent(
        @RequestParam(required = false) Long id,
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String email,
                @RequestParam(required = false) String dobAfter,
                @RequestParam(required = false) String dobBefore
    ){
        validateParameterCombination(id, name, email, dobAfter, dobBefore);

        if(id != null){
            return studentService.getStudentById(id);
        }
        else if(name != null){
            return studentService.getStudentByName(name);
        }
        else if(email != null){
            return studentService.getStudentByEmail(email);
        }
        else if (dobAfter != null && dobBefore != null){
            return studentService.getStudentsBornBetween(dobAfter, dobBefore);
        }
        else if (dobAfter != null) {
            return studentService.getStudentsBornAfter(dobAfter);
        } else if (dobBefore != null) {
            return studentService.getStudentsBornBefore(dobBefore);
        } else {
            return studentService.getStudents();
        }
    }

    private void validateParameterCombination(Long id, String name, String email, String dobAfter, String dobBefore) {
        if ((id != null && (name != null || email != null || dobAfter != null || dobBefore != null))
                || (name != null && (email != null || dobAfter != null || dobBefore != null))
                || (email != null && (dobAfter != null || dobBefore != null))) {
            throw new IllegalArgumentException("Nevaljana kombinacija!");
        }
    }


    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);
    }
}
