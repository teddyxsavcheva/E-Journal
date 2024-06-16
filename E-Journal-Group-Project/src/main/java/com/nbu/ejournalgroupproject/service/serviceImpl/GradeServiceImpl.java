package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.repository.GradeRepository;
import com.nbu.ejournalgroupproject.service.GradeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final ModelMapper modelMapper;

    @Override
    public GradeDTO getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Grade not found with id " + id));
        return modelMapper.map(grade, GradeDTO.class);
    }

    @Override
    public List<GradeDTO> getAllGrades() {
        List<Grade> grades = gradeRepository.findAll();
        return grades.stream()
                .map(grade -> modelMapper.map(grade, GradeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GradeDTO createGrade(GradeDTO gradeDTO) {
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        Grade createdGrade = gradeRepository.save(grade);
        return modelMapper.map(createdGrade, GradeDTO.class);
    }

    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public GradeDTO updateGrade(Long id, GradeDTO gradeDTO) {
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Grade not found with id " + id));
        modelMapper.map(gradeDTO, grade);
        Grade updatedGrade = gradeRepository.save(grade);
        return modelMapper.map(updatedGrade, GradeDTO.class);
    }
}