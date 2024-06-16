package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.mappers.HeadmasterMapper;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.repository.HeadmasterRepository;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.service.HeadmasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class HeadmasterServiceImpl implements HeadmasterService {

    @Autowired
    private final HeadmasterRepository headmasterRepository;

    @Autowired
    private final SchoolRepository schoolRepository;

    @Autowired
    private final HeadmasterMapper headmasterMapper;


    @Override
    public List<HeadmasterDTO> getHeadmasters() {
        List<Headmaster> headmasters = headmasterRepository.findAll();
        return headmasters
                .stream()
                .map(headmasterMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HeadmasterDTO getHeadmaster(Long id) {
        Headmaster headmaster = headmasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Headmaster with id " + id + " not found"));
        return headmasterMapper.mapEntityToDto(headmaster);
    }

    @Override
    public HeadmasterDTO getHeadmasterBySchoolID(Long id) {
       Headmaster headmaster = headmasterRepository.findBySchoolId(id);
       return headmasterMapper.mapEntityToDto(headmaster);
    }

    @Override
    public HeadmasterDTO createHeadmaster(HeadmasterDTO headmasterDTO) {
        validateHeadmasterDTO(headmasterDTO);
        Headmaster headmaster = headmasterMapper.mapDtoToEntity(headmasterDTO);
        return headmasterMapper.mapEntityToDto(headmasterRepository.save(headmaster));
    }

    @Override
    public HeadmasterDTO updateHeadmaster(Long id, HeadmasterDTO newHeadmaster) {
        validateHeadmasterDTO(newHeadmaster);

        Headmaster existingHeadmaster = headmasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Headmaster with id " + id + " not found"));

        existingHeadmaster.setName(newHeadmaster.getName());
        existingHeadmaster.setEmail(newHeadmaster.getEmail());

        updateHeadmasterSchool(existingHeadmaster, newHeadmaster);

        return headmasterMapper.mapEntityToDto(headmasterRepository.save(existingHeadmaster));
    }

    private void updateHeadmasterSchool(Headmaster existingHeadmaster, HeadmasterDTO newHeadmaster) {
        Long schoolId = newHeadmaster.getSchoolId();
        if (schoolId != null) {
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new EntityNotFoundException("School with id " + schoolId + " not found"));
            existingHeadmaster.setSchool(school);
        }
    }

    @Override
    public void deleteHeadmaster(Long id) {
        Headmaster headmaster = headmasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Headmaster with id " + id + " not found"));

        headmasterRepository.delete(headmaster);
    }

    @Override
    public void validateHeadmasterDTO(HeadmasterDTO headmasterDTO) {
        if (headmasterDTO.getSchoolId() == null) {
            throw new IllegalArgumentException("The School cannot be null.");
        }

        if (headmasterDTO.getName() == null) {
            throw new IllegalArgumentException("The Headmaster name cannot be null.");
        }

        if (headmasterDTO.getEmail() == null) {
            throw new IllegalArgumentException("The Headmaster email cannot be null.");
        }
    }

}

