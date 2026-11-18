package com.portfolio.students;

import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class StudentDao {
    private final EntityManager entityManager;

    public StudentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Student> findAll() {
        return entityManager.createQuery("select s from Student s order by s.id", Student.class)
                .getResultList();
    }

    public Student findById(long id) {
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return student;
    }

    @Transactional
    public Student create(StudentInput input) {
        Student student = new Student(input);
        entityManager.persist(student);
        return student;
    }

    @Transactional
    public Student update(long id, StudentInput input) {
        Student student = findById(id);
        student.update(input);
        return student;
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(findById(id));
    }
}
