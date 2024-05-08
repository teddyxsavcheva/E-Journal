package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
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
                .map(headmasterMapper::HeadmasterEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HeadmasterDTO getHeadmaster(Long id) {
        Headmaster headmaster = headmasterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return headmasterMapper.HeadmasterEntityToDto(headmaster);
    }

    @Override
    public HeadmasterDTO getHeadmasterBySchoolID(Long id) {
       Headmaster headmaster = headmasterRepository.findBySchoolId(id);
       return headmasterMapper.HeadmasterEntityToDto(headmaster);
    }

    @Override
    public void createHeadmaster(HeadmasterDTO headmasterDTO) {
        Headmaster headmaster = headmasterMapper.HeadmasterDtoToEntity(headmasterDTO);
        headmasterRepository.save(headmaster);
    }

    @Override
    public boolean deleteHeadmaster(Long id) {
        try{
            getHeadmaster(id);
        } catch (Exception e){
            return false;
        }
        headmasterRepository.deleteById(id);
        return true;
    }

    @Override
    public void updateHeadmaster(Long id, HeadmasterDTO newHeadmaster){
        Headmaster existingHeadmaster = headmasterRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        existingHeadmaster.setName(newHeadmaster.getName());
        existingHeadmaster.setEmail(newHeadmaster.getEmail());
        if (newHeadmaster.getSchoolId() != null) {
            School school = schoolRepository.findById(newHeadmaster.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("School with id " + newHeadmaster.getSchoolId() + " not found"));
            existingHeadmaster.setSchool(school);
        }

        headmasterRepository.save(existingHeadmaster);
    }
}
