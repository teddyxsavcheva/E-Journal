package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.SchoolTypeDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolTypeMapper;
import com.nbu.ejournalgroupproject.model.SchoolType;
import com.nbu.ejournalgroupproject.repository.SchoolTypeRepository;
import com.nbu.ejournalgroupproject.service.SchoolTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolTypeServiceImpl implements SchoolTypeService {
    private SchoolTypeRepository schoolTypeRepository;
    private SchoolTypeMapper schoolTypeMapper;

    @Override
    public List<SchoolTypeDTO> getAllSchoolTypes() {
        List<SchoolType> schoolTypes = schoolTypeRepository.findAll();
        return schoolTypes
                .stream()
                .map(st -> schoolTypeMapper.mapEntityToDto(st))
                .collect(Collectors.toList());
    }

    @Override
    public SchoolTypeDTO getSchoolTypeById(Long id) {
        SchoolType schoolType = schoolTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No SchoolType found with id " + id));
        return schoolTypeMapper.mapEntityToDto(schoolType);
    }

    @Override
    public SchoolTypeDTO createSchoolType(SchoolTypeDTO schoolTypeDTO) {
        validateSchoolTypeDTO(schoolTypeDTO);

        SchoolType schoolType = schoolTypeMapper.mapDtoToEntity(schoolTypeDTO);
        return schoolTypeMapper.mapEntityToDto(schoolTypeRepository.save(schoolType));

    }

    @Override
    public SchoolTypeDTO updateSchoolType(Long id, SchoolTypeDTO schoolTypeDTO) {
        validateSchoolTypeDTO(schoolTypeDTO);

        SchoolType schoolType = schoolTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchoolType with id " + schoolTypeDTO.getId() + " not found"));

        schoolType.setSchoolTypeEnum(schoolTypeDTO.getSchoolType());

        return schoolTypeMapper.mapEntityToDto(schoolTypeRepository.save(schoolType));

    }

    @Override
    public void deleteSchoolType(Long id) {
        SchoolType schoolType = schoolTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchoolType with id " + id + " not found"));

        schoolTypeRepository.delete(schoolType);
    }

    @Override
    public void validateSchoolTypeDTO(SchoolTypeDTO schoolTypeDTO) {

        if (schoolTypeDTO.getSchoolType() == null) {
            throw new IllegalArgumentException("The School Type cannot be null");
        }

    }
}
