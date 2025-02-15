package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolClassMapper;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.service.SchoolClassService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;
    private final SchoolRepository schoolRepository;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @Override
    public List<SchoolClassDTO> getSchoolClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        return schoolClasses
                .stream()
                .map(schoolClassMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','HEADMASTER', 'TEACHER', 'STUDENT', 'CAREGIVER')")
    @Override
    public SchoolClassDTO getSchoolClas(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found"));
        return schoolClassMapper.mapEntityToDto(schoolClass);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public List<SchoolClassDTO> getSchoolClasBySchoolId(Long id) {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAllBySchoolId(id);
        return schoolClasses
                .stream()
                .map(schoolClassMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public SchoolClassDTO createSchoolClass(@Valid SchoolClassDTO schoolClassDTO) {
        validateSchoolClassDTO(schoolClassDTO);


        SchoolClass schoolClass = schoolClassMapper.mapDtoToEntity(schoolClassDTO);
        return schoolClassMapper.mapEntityToDto(schoolClassRepository.save(schoolClass));
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public SchoolClassDTO updateSchoolClass(@NotNull Long id, @Valid SchoolClassDTO newSchoolClass) {
        validateSchoolClassDTO(newSchoolClass);

        SchoolClass existingSchoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found."));

        existingSchoolClass.setName(newSchoolClass.getName());
        existingSchoolClass.setYear(newSchoolClass.getYear());

        existingSchoolClass.setSchool(schoolRepository.findById(newSchoolClass.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("School with id " + newSchoolClass.getSchoolId() + " not found.")));
        SchoolClass updatedSchoolClass = schoolClassRepository.save(existingSchoolClass);

        return schoolClassMapper.mapEntityToDto(updatedSchoolClass);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public void deleteSchoolClass(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found."));
        schoolClassRepository.delete(schoolClass);

    }

    @Override
    public void validateSchoolClassDTO(SchoolClassDTO schoolClassDTO) {
        if (schoolClassDTO.getSchoolId() == 0) {
            throw new IllegalArgumentException("The School ID cannot be zero.");
        }

        if (schoolClassDTO.getName() == null) {
            throw new IllegalArgumentException("The School Class name cannot be null.");
        }

        if (schoolClassDTO.getYear() < 2000) {
            throw new IllegalArgumentException("The School Year name cannot be < 2000.");
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','TEACHER','HEADMASTER')")
    @Override
    public List<SchoolClassDTO> getClassesFromTeacherId(Long teacherId){
        List<SchoolClass> classes = schoolClassRepository.findDistinctClassesByTeacherId(teacherId);
        return classes
                .stream()
                .map(schoolClassMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
