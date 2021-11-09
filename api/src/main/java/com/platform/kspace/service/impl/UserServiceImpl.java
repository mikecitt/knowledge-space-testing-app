package com.platform.kspace.service.impl;

import com.platform.kspace.dto.ProfessorDTO;
import com.platform.kspace.dto.StudentDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.mapper.ProfessorMapper;
import com.platform.kspace.mapper.StudentMapper;
import com.platform.kspace.model.Authority;
import com.platform.kspace.model.Professor;
import com.platform.kspace.model.Student;
import com.platform.kspace.repository.AuthorityRepository;
import com.platform.kspace.repository.UserRepository;
import com.platform.kspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    private StudentMapper studentMapper;

    private ProfessorMapper professorMapper;

    private boolean checkEmailAddress(String emailAddress) {
        return userRepository.findByEmail(emailAddress) != null;
    }

    public UserServiceImpl() {
        this.studentMapper = new StudentMapper();
        this.professorMapper = new ProfessorMapper();
    }

    @Override
    public void registerStudent(StudentDTO registrationForm) throws KSpaceException {
        Student student = studentMapper.toEntity(registrationForm);
        if(checkEmailAddress(student.getEmail())) {
            throw new KSpaceException(
                    HttpStatus.CONFLICT,
                    "There is already registered student with given email address"
            );
        }

        // before password is placed it goes through hashing phase
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        Authority auth = authorityRepository.findByName("ROLE_STUDENT");
        student.setAuthorities(new ArrayList<>() {{ add(auth); }});
        userRepository.save(student);
    }

    @Override
    public void registerProfessor(ProfessorDTO registrationForm) throws KSpaceException {
        Professor professor = professorMapper.toEntity(registrationForm);
        if(checkEmailAddress(professor.getEmail())) {
            throw new KSpaceException(
                    HttpStatus.CONFLICT,
                    "There is already registered professor with given email address"
            );
        }

        // before password is placed it goes through hashing phase
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));

        Authority auth = authorityRepository.findByName("ROLE_PROFESSOR");
        professor.setAuthorities(new ArrayList<>() {{ add(auth); }});
        userRepository.save(professor);
    }
}
