package com.portfolio.students;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentDao studentDao;

    public StudentController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping
    public List<Student> listStudents() {
        return studentDao.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable long id) {
        return studentDao.findById(id);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentInput input) {
        Student student = studentDao.create(input);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();
        return ResponseEntity.created(location).body(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable long id, @Valid @RequestBody StudentInput input) {
        return studentDao.update(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable long id) {
        studentDao.delete(id);
    }
}
