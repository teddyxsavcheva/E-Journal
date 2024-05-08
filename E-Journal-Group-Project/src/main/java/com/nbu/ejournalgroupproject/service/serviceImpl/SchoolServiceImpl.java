package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolMapper;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private final SchoolRepository schoolRepository;

    @Autowired
    private final SchoolMapper schoolMapper;

    @Override
    public List<SchoolDTO> getSchools() {
        List<School> schoolEntities = schoolRepository.findAll();
        return schoolEntities
                .stream()
                .map(schoolMapper::SchoolEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolDTO getSchool(Long id) {
        School school = schoolRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return schoolMapper.SchoolEntityToDto(school);
    }

    @Override
    public void createSchool(SchoolDTO schoolDTO) {
        School school = schoolMapper.SchoolDtoToEntity(schoolDTO);
        schoolRepository.save(school);
    }

    @Override
    public boolean deleteSchool(Long schoolId) {
        try{
            getSchool(schoolId);
        } catch (Exception e){
            return false;
        }
        schoolRepository.deleteById(schoolId);
        return true;
    }


}
