package com.platform.kspace.mapper;

import com.platform.kspace.dto.ProfessorDTO;
import com.platform.kspace.model.Professor;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;

public class ProfessorMapper implements MapperInterface<Professor, ProfessorDTO> {
    @Override
    public Professor toEntity(ProfessorDTO dto) {
        return new Professor(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword()
        );
    }

    @Override
    public List<Professor> toEntityList(List<ProfessorDTO> dtoList) {
        throw new NotYetImplementedException();
    }

    @Override
    public ProfessorDTO toDto(Professor entity) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<ProfessorDTO> toDtoList(List<Professor> entityList) {
        throw new NotYetImplementedException();
    }
}
