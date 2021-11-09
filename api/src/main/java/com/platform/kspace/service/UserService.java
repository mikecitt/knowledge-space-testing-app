package com.platform.kspace.service;

import com.platform.kspace.dto.ProfessorDTO;
import com.platform.kspace.dto.StudentDTO;
import com.platform.kspace.exceptions.KSpaceException;

public interface UserService {
    void registerStudent(StudentDTO registrationForm) throws KSpaceException;
    void registerProfessor(ProfessorDTO registrationForm) throws KSpaceException;
}
