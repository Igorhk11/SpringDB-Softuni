package com.example.sprintfirst.services;

import com.example.sprintfirst.entities.Student;
import com.example.sprintfirst.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student register(String name) {
        Student student = new Student(name);

        return studentRepository.save(student);
    }

    @Override
    public Student searchByName(String name) {
        return studentRepository.findByName(name);
    }
}

