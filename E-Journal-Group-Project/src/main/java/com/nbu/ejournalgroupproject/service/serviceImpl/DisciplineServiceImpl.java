package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;
import com.nbu.ejournalgroupproject.mappers.DisciplineMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.service.DisciplineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final DisciplineTypeRepository disciplineTypeRepository;
    private final DisciplineMapper disciplineMapper;

    @Override
    public List<DisciplineDto> getAllDisciplines() {

        List<Discipline> disciplines = disciplineRepository.findAll();

        return disciplines.stream()
                .map(disciplineMapper::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public DisciplineDto getDisciplineById(Long id) {

        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        return disciplineMapper.convertToDto(discipline);

    }

    @Override
    public DisciplineDto createDiscipline(DisciplineDto disciplineDto) {

        validateDisciplineDTO(disciplineDto);

        Discipline discipline = disciplineMapper.convertToEntity(disciplineDto);

        return disciplineMapper.convertToDto(disciplineRepository.save(discipline));

    }

    @Override
    public DisciplineDto updateDiscipline(DisciplineDto disciplineDto, Long id) {

        validateDisciplineDTO(disciplineDto);

        Discipline existingDiscipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        existingDiscipline.setName(disciplineDto.getName());

        DisciplineType newDisciplineType = disciplineTypeRepository.findById(disciplineDto.getDisciplineTypeId())
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + disciplineDto.getDisciplineTypeId()));

        existingDiscipline.setDisciplineType(newDisciplineType);

        return disciplineMapper.convertToDto(disciplineRepository.save(existingDiscipline));

    }

    @Override
    public void deleteDiscipline(Long id) {

        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        disciplineRepository.delete(discipline);

    }

    @Override
    public void validateDisciplineDTO(DisciplineDto disciplineDto) {

        if (disciplineDto.getName() == null || disciplineDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The discipline name cannot be null or empty.");
        }

        if (disciplineDto.getDisciplineTypeId() == null || disciplineDto.getDisciplineTypeId() == 0) {
            throw new IllegalArgumentException("The discipline type id cannot be null or zero.");
        }

    }
}
