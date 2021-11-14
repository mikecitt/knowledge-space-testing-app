package com.platform.kspace.repository;

import com.platform.kspace.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student getById(UUID id);
}
