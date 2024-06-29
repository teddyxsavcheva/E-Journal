package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import com.nbu.ejournalgroupproject.mappers.AbsenceTypeMapper;
import com.nbu.ejournalgroupproject.model.AbsenceType;
import com.nbu.ejournalgroupproject.repository.AbsenceTypeRepository;
import com.nbu.ejournalgroupproject.service.AbsenceTypeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceTypeServiceImpl implements AbsenceTypeService {

    private final AbsenceTypeRepository absenceTypeRepository;
    private final AbsenceTypeMapper absenceTypeMapper;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','HEADMASTER', 'TEACHER', 'STUDENT', 'CAREGIVER')")
    @Override
    public AbsenceTypeDTO getAbsenceTypeById(Long id) {
        AbsenceType absenceType = absenceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AbsenceType not found with id " + id));
        return absenceTypeMapper.toDTO(absenceType);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @Override
    public List<AbsenceTypeDTO> getAllAbsenceTypes() {
        List<AbsenceType> absenceTypes = absenceTypeRepository.findAll();
        return absenceTypes.stream()
                .map(absenceTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public AbsenceTypeDTO createAbsenceType(@Valid AbsenceTypeDTO absenceTypeDTO) {
        AbsenceType absenceType = absenceTypeMapper.toEntity(absenceTypeDTO);
        AbsenceType createdAbsenceType = absenceTypeRepository.save(absenceType);
        return absenceTypeMapper.toDTO(createdAbsenceType);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public void deleteAbsenceType(Long id) {
        absenceTypeRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public AbsenceTypeDTO updateAbsenceType(Long id, @Valid AbsenceTypeDTO absenceTypeDTO) {
        AbsenceType existingAbsenceType = absenceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AbsenceType not found with id " + id));

        AbsenceType updatedAbsenceType = absenceTypeMapper.toEntity(absenceTypeDTO);
        updatedAbsenceType.setId(existingAbsenceType.getId());

        AbsenceType savedAbsenceType = absenceTypeRepository.save(updatedAbsenceType);
        return absenceTypeMapper.toDTO(savedAbsenceType);
    }
}