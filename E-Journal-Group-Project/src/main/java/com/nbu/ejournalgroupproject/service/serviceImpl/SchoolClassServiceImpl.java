package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolClassMapper;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.service.SchoolClassService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;
    private final SchoolRepository schoolRepository;

    @Override
    public List<SchoolClassDTO> getSchoolClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        return schoolClasses
                .stream()
                .map(schoolClassMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolClassDTO getSchoolClas(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found"));
        return schoolClassMapper.mapEntityToDto(schoolClass);
    }

    @Override
    public SchoolClassDTO createSchoolClass(@Valid SchoolClassDTO schoolClassDTO) {
        validateSchoolClassDTO(schoolClassDTO);


        SchoolClass schoolClass = schoolClassMapper.mapDtoToEntity(schoolClassDTO);
        return schoolClassMapper.mapEntityToDto(schoolClassRepository.save(schoolClass));
    }

    @Override
    public SchoolClassDTO updateSchoolClass(Long id, @Valid SchoolClassDTO newSchoolClass) {
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
}
