package com.example.sprintfirst.services;

import com.example.sprintfirst.entities.Student;

public interface StudentService {
    Student register(String name);

    Student searchByName(String name);
}