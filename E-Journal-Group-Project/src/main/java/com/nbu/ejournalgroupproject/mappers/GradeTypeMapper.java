package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.model.GradeType;
import com.nbu.ejournalgroupproject.repository.GradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GradeTypeMapper {

    private final GradeRepository gradeRepository;

    public GradeTypeDTO toDTO(GradeType gradeType) {
        GradeTypeDTO gradeTypeDTO = new GradeTypeDTO();
        gradeTypeDTO.setId(gradeType.getId());
        gradeTypeDTO.setGradeTypeEnum(gradeType.getGradeTypeEnum());
        List<Long> gradeIds = gradeType.getGrades().stream()
                .map(Grade::getId)
                .collect(Collectors.toList());
        gradeTypeDTO.setGradesIds(gradeIds);
        return gradeTypeDTO;
    }

    public GradeType toEntity(GradeTypeDTO gradeTypeDTO) {
        GradeType gradeType = new GradeType();
        gradeType.setId(gradeTypeDTO.getId());
        gradeType.setGradeTypeEnum(gradeTypeDTO.getGradeTypeEnum());

        List<Grade> grades = gradeTypeDTO.getGradesIds().stream()
                .map(id -> gradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Grade not found with id " + id)))
                .collect(Collectors.toList());
        gradeType.setGrades(grades);

        return gradeType;
    }
}