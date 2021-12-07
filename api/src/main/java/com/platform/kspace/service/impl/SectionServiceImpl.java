package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.dto.SectionDTO;
import com.platform.kspace.mapper.SectionMapper;
import com.platform.kspace.model.Section;
import com.platform.kspace.model.Test;
import com.platform.kspace.repository.SectionRepository;
import com.platform.kspace.repository.TestRepository;
import com.platform.kspace.service.SectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TestRepository testRepository;

    private SectionMapper sectionMapper;

    public SectionServiceImpl() {
        this.sectionMapper = new SectionMapper();
    }

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    @Override
    public Section findOne(Integer id) {
        return sectionRepository.findById(id).orElse(null);
    }

    @Override
    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public SectionDTO addSection(SectionDTO dto, Integer testId) {
        Test test = testRepository.findById(testId).orElse(null);
        Section section = sectionMapper.toEntity(dto);
        section.setTest(test);
        section = sectionRepository.save(section);
        return sectionMapper.toDto(section);
    }

    @Override
    public List<SectionDTO> getSections() {
        return sectionMapper.toDtoList(sectionRepository.findAll());
    }

    @Override
    public List<SectionDTO> getTestSections(Integer testId) {
        return sectionMapper.toDtoList(sectionRepository.findByTestId(testId));
    }

    @Override
    public void deleteSection(Integer id) {
        sectionRepository.deleteById(id);
        return;
    }

    @Override
    public SectionDTO updateSection(SectionDTO section, Integer sectionId) throws Exception {
        Section s = sectionRepository.getById(sectionId);
        if(s == null)
            throw new Exception("Section doesn't exist.");
        s.setName(section.getName());
        return sectionMapper.toDto(sectionRepository.save(s));
    }
    
}
