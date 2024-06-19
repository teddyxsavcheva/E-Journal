package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolMapper;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.repository.SchoolTypeRepository;
import com.nbu.ejournalgroupproject.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolTypeRepository schoolTypeRepository;
    private final SchoolMapper schoolMapper;

    @Override
    public List<SchoolDTO> getSchools() {
        List<School> schools = schoolRepository.findAll();
        return schools
                .stream()
                .map(schoolMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolDTO getSchool(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + id));
        return schoolMapper.mapEntityToDto(school);
    }

    @Override
    public SchoolDTO createSchool(@Valid SchoolDTO schoolDTO) {
        validateSchoolDTO(schoolDTO);
        School school = schoolMapper.mapDtoToEntity(schoolDTO);
        return schoolMapper.mapEntityToDto(schoolRepository.save(school));
    }

    @Override
    public SchoolDTO updateSchool(@NotNull Long id, @Valid SchoolDTO newSchool) {
        validateSchoolDTO(newSchool);

        School existingSchool = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + id));

        existingSchool.setName(newSchool.getName());
        existingSchool.setAddress(newSchool.getAddress());
        existingSchool.setSchoolType(schoolTypeRepository.findById(newSchool.getSchoolTypeId())
                .orElseThrow(() -> new EntityNotFoundException("School Type with id " + newSchool.getSchoolTypeId() + "not ofund")));

        School updatedSchool = schoolRepository.save(existingSchool);
        return schoolMapper.mapEntityToDto(updatedSchool);
    }

    @Override
    public void deleteSchool(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School with id: " + schoolId + "not found"));
        schoolRepository.delete(school);
    }

    @Override
    public void validateSchoolDTO(SchoolDTO schoolDTO) {
        if (schoolDTO.getName() == null) {
            throw new IllegalArgumentException("School name cannot be null.");
        }
        if (schoolDTO.getAddress() == null) {
            throw new IllegalArgumentException("School address cannot be null.");
        }
        if (schoolDTO.getSchoolTypeId() == 0) {
            throw new IllegalArgumentException("School Type ID cannot be zero.");
        }

    }
}
