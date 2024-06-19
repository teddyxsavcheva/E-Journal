package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDto;
import com.nbu.ejournalgroupproject.mappers.DisciplineTypeMapper;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.service.DisciplineTypeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public List<DisciplineTypeDto> getAllDisciplineTypes() {

        List<DisciplineType> disciplineTypes = disciplineTypeRepository.findAll();

        return disciplineTypes.stream()
                .map(disciplineTypeMapper::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public DisciplineTypeDto getDisciplineTypeById(@NotNull Long id) {

        DisciplineType disciplineType = disciplineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + id));

        return disciplineTypeMapper.convertToDto(disciplineType);

    }

    @Override
    public DisciplineTypeDto createDisciplineType(@Valid DisciplineTypeDto disciplineTypeDto) {

        validateDisciplineTypeDTO(disciplineTypeDto);

        DisciplineType disciplineType = disciplineTypeMapper.convertToEntity(disciplineTypeDto);

        return disciplineTypeMapper.convertToDto(disciplineTypeRepository.save(disciplineType));

    }

    @Override
    public DisciplineTypeDto updateDisciplineType(@Valid DisciplineTypeDto disciplineTypeDto,@NotNull Long id) {

        validateDisciplineTypeDTO(disciplineTypeDto);

        DisciplineType existingDisciplineType = disciplineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + id));

        existingDisciplineType.setDisciplineTypeEnum(disciplineTypeDto.getDisciplineType());

        return disciplineTypeMapper.convertToDto(disciplineTypeRepository.save(existingDisciplineType));

    }

    @Override
    public void deleteDisciplineType(@NotNull Long id) {

        DisciplineType disciplineType = disciplineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + id));

        disciplineTypeRepository.delete(disciplineType);

    }

    @Override
    public void validateDisciplineTypeDTO(DisciplineTypeDto disciplineTypeDto) {

        if (disciplineTypeDto.getDisciplineType() == null) {
            throw new IllegalArgumentException("The Discipline Type Enum cannot be null");
        }

    }
}
