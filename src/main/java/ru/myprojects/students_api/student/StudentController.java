package ru.myprojects.students_api.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        service.addNewStudent(student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id) {
        service.deleteStudent(id);
    }

    @PutMapping("{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        service.updateStudent(studentId, name, email);
    }
}
