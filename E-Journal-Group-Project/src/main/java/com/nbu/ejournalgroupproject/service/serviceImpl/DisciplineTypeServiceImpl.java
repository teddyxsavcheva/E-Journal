package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDTO;
import com.nbu.ejournalgroupproject.mappers.DisciplineTypeMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.service.DisciplineTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DisciplineTypeServiceImpl implements DisciplineTypeService {

    private final DisciplineTypeRepository disciplineTypeRepository;
    private final DisciplineTypeMapper disciplineTypeMapper;


    @Override
    public List<DisciplineTypeDTO> getAllDisciplineTypes() {
        List<DisciplineType> disciplineTypes = disciplineTypeRepository.findAll();
        return disciplineTypes.stream()
                .map(disciplineTypeMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DisciplineTypeDTO getDisciplineTypeById(Long id) {

        DisciplineType disciplineType = disciplineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + id));

        return disciplineTypeMapper.convertToDto(disciplineType);
    }

    @Override
    public void createDisciplineType(DisciplineTypeDTO disciplineTypeDTO) {
        // TODO: Decide if I should validate the DTO here as well
        disciplineTypeRepository.save(disciplineTypeMapper.convertToEntity(disciplineTypeDTO));
    }

    @Override
    public void updateDisciplineType(DisciplineTypeDTO disciplineTypeDTO, Long id) {
        // TODO: Decide if I should validate the DTO here as well
        DisciplineType existingDisciplineType = disciplineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + id));

        existingDisciplineType.setDisciplineTypeEnum(disciplineTypeDTO.getDisciplineType());

        disciplineTypeRepository.save(existingDisciplineType);
    }

    @Override
    public void deleteDisciplineType(Long id) {

        DisciplineType disciplineType = disciplineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + id));

        disciplineTypeRepository.delete(disciplineType);
    }
}
