package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import com.nbu.ejournalgroupproject.mappers.GradeMapper;
import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.repository.GradeRepository;
import com.nbu.ejournalgroupproject.service.GradeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    @Override
    public GradeDTO getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id " + id));
        return gradeMapper.toDTO(grade);
    }

    @Override
    public List<GradeDTO> getAllGrades() {
        List<Grade> grades = gradeRepository.findAll();
        return grades.stream()
                .map(gradeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradeDTO createGrade(@Valid GradeDTO gradeDTO) {
        Grade grade = gradeMapper.toEntity(gradeDTO);
        Grade createdGrade = gradeRepository.save(grade);
        return gradeMapper.toDTO(createdGrade);
    }

    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public GradeDTO updateGrade(Long id, @Valid GradeDTO gradeDTO) {
        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id " + id));

        Grade updatedGrade = gradeMapper.toEntity(gradeDTO);
        updatedGrade.setId(existingGrade.getId());

        Grade savedGrade = gradeRepository.save(updatedGrade);
        return gradeMapper.toDTO(savedGrade);
    }

    @Override
    public List<String> getGradesByStudentAndDiscipline(Long studentId,Long disciplineId) {
        return gradeRepository.findGradeTypesByStudentAndDiscipline(studentId, disciplineId);
    }

    @Override
    public List<GradeDTO> getGradeObjectsByStudentAndDiscipline(Long studentId, Long disciplineId) {
        return gradeRepository.getAllByStudentIdAndDisciplineId(studentId, disciplineId)
                .stream()
                .map(gradeMapper::toDTO)
                .collect(Collectors.toList());
    }
}