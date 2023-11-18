package com.example.sprintfirst.repositories;

import com.example.sprintfirst.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findByName(String name);
}
