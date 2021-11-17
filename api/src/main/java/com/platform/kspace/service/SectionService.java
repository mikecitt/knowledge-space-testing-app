package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.SectionDTO;
import com.platform.kspace.model.Section;

public interface SectionService {
    List<Section> findAll();
    List<SectionDTO> getSections();
    List<SectionDTO> getTestSections(Integer testId);
    Section findOne(Integer id);
    Section save(Section section);
    void deleteSection(Integer id);
    SectionDTO addSection(Section section, Integer testId);
}
