package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;
import com.nbu.ejournalgroupproject.mappers.DisciplineMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.service.DisciplineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final TeacherQualificationRepository teacherQualificationRepository;
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
    public DisciplineDto getDisciplineById(@NotNull Long id) {

        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + id));

        return disciplineMapper.convertToDto(discipline);

    }

    @Override
    public DisciplineDto createDiscipline(@Valid DisciplineDto disciplineDto) {

        validateDisciplineDTO(disciplineDto);

        // In the convertToEntity method we check the DisciplineType as well
        Discipline discipline = disciplineMapper.convertToEntity(disciplineDto);

        return disciplineMapper.convertToDto(disciplineRepository.save(discipline));

    }

    @Override
    public DisciplineDto updateDiscipline(@Valid DisciplineDto disciplineDto, @NotNull Long id) {

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
    public void deleteDiscipline(@NotNull Long id) {

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

    @Override
    public DisciplineDto  addQualificationToDiscipline(@NotNull Long disciplineId,@NotNull Long qualificationId) {

        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + disciplineId));

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id " + qualificationId));

        discipline.getTeacherQualifications().add(qualification);

        return disciplineMapper.convertToDto(disciplineRepository.save(discipline));

    }

    @Override
    public DisciplineDto  removeQualificationFromDiscipline(@NotNull Long disciplineId,@NotNull Long qualificationId) {

        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + disciplineId));

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id " + qualificationId));

        discipline.getTeacherQualifications().remove(qualification);

        return disciplineMapper.convertToDto(disciplineRepository.save(discipline));

    }

    @Override
    public List<DisciplineDto> getDisciplinesByStudentId(Long studentId) {
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByStudentId(studentId);
        return disciplines.stream()
                .map(disciplineMapper::convertToDto)
                .collect(Collectors.toList());
    }
}