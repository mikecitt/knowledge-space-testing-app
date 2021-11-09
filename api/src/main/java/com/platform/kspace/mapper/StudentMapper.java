package com.platform.kspace.mapper;

import com.platform.kspace.dto.StudentDTO;
import com.platform.kspace.model.Student;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;

public class StudentMapper implements MapperInterface<Student, StudentDTO> {

    @Override
    public Student toEntity(StudentDTO dto) {
        return new Student(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getDateOfBirth()
        );
    }

    @Override
    public List<Student> toEntityList(List<StudentDTO> dtoList) {
        throw new NotYetImplementedException();
    }

    @Override
    public StudentDTO toDto(Student entity) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<StudentDTO> toDtoList(List<Student> entityList) {
        throw new NotYetImplementedException();
    }
}
