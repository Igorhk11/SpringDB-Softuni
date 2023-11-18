package com.example.softunigamestore.util;

import jakarta.persistence.Basic;
import jakarta.validation.ConstraintViolation;
import org.springframework.context.annotation.Bean;

import java.util.Set;


public interface ValidationUtil {

    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> violation(E entity);
}
