package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.mappers.GradeTypeMapper;
import com.nbu.ejournalgroupproject.model.GradeType;
import com.nbu.ejournalgroupproject.repository.GradeTypeRepository;
import com.nbu.ejournalgroupproject.service.GradeTypeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeTypeServiceImpl implements GradeTypeService {

    private final GradeTypeRepository gradeTypeRepository;
    private final GradeTypeMapper gradeTypeMapper;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','HEADMASTER', 'TEACHER', 'STUDENT', 'CAREGIVER')")
    @Override
    public GradeTypeDTO getGradeTypeById(Long id) {
        GradeType gradeType = gradeTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GradeType not found with id " + id));
        return gradeTypeMapper.toDTO(gradeType);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @Override
    public List<GradeTypeDTO> getAllGradeTypes() {
        List<GradeType> gradeTypes = gradeTypeRepository.findAll();
        return gradeTypes.stream()
                .map(gradeTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public GradeTypeDTO createGradeType(@Valid GradeTypeDTO gradeTypeDTO) {
        GradeType gradeType = gradeTypeMapper.toEntity(gradeTypeDTO);
        GradeType createdGradeType = gradeTypeRepository.save(gradeType);
        return gradeTypeMapper.toDTO(createdGradeType);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public void deleteGradeType(Long id) {
        gradeTypeRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @Override
    public GradeTypeDTO updateGradeType(Long id, @Valid GradeTypeDTO gradeTypeDTO) {
        GradeType existingGradeType = gradeTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GradeType not found with id " + id));

        GradeType updatedGradeType = gradeTypeMapper.toEntity(gradeTypeDTO);
        updatedGradeType.setId(existingGradeType.getId());

        GradeType savedGradeType = gradeTypeRepository.save(updatedGradeType);
        return gradeTypeMapper.toDTO(savedGradeType);
    }
}