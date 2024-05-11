package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.DisciplineDTO;
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
    public List<DisciplineDTO> getAllDisciplines() {

        List<Discipline> disciplines = disciplineRepository.findAll();

        if (disciplines.isEmpty()) {
            throw new EntityNotFoundException("No Disciplines found");
        }

        return disciplines.stream()
                .map(disciplineMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DisciplineDTO getDisciplineById(Long id) {

        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        return disciplineMapper.convertToDto(discipline);
    }

    @Override
    public void createDiscipline(DisciplineDTO disciplineDTO) {

        validateDisciplineDTO(disciplineDTO);

        disciplineRepository.save(disciplineMapper.convertToEntity(disciplineDTO));
    }

    @Override
    public void updateDiscipline(DisciplineDTO disciplineDTO, Long id) {

        validateDisciplineDTO(disciplineDTO);

        Discipline existingDiscipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        existingDiscipline.setName(disciplineDTO.getName());

        DisciplineType newDisciplineType = disciplineTypeRepository.findById(disciplineDTO.getDisciplineTypeId())
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + disciplineDTO.getDisciplineTypeId()));

        existingDiscipline.setDisciplineType(newDisciplineType);

        disciplineRepository.save(existingDiscipline);
    }

    @Override
    public void deleteDiscipline(Long id) {

        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        disciplineRepository.delete(discipline);
    }

    @Override
    public void validateDisciplineDTO(DisciplineDTO disciplineDTO) {

        if (disciplineDTO.getName() == null || disciplineDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("The discipline name cannot be null or empty.");
        }

        if (disciplineDTO.getDisciplineTypeId() == null || disciplineDTO.getDisciplineTypeId() == 0) {
            throw new IllegalArgumentException("The discipline type ID cannot be null or zero.");
        }
    }


}
