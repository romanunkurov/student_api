package ru.myprojects.students_api.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents() {
        return repository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = repository.findStudentByEmail(student.getEmail());
        if (studentOptional.isEmpty()) {
            throw new IllegalStateException("email taken");
        }
        repository.save(student);
        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = repository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "student with id " + studentId + " does not exist"
            );
        }
        repository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = repository.findById(studentId).orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does npt exist"));

        if (name != null
                && name.length() > 0
                && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null
        && email.length() > 0
        && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = repository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException();
            }
            student.setEmail(email);
        }
    }
}
