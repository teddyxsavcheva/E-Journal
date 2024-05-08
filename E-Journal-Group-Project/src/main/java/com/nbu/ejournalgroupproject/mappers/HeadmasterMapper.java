package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class HeadmasterMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private final SchoolRepository schoolRepository;

    public HeadmasterMapper(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public HeadmasterDTO HeadmasterEntityToDto(Headmaster headmaster){
        HeadmasterDTO headmasterDTO = modelMapper.map(headmaster, HeadmasterDTO.class);
        mapSchoolEntityToId(headmaster, headmasterDTO);
        return headmasterDTO;
    }

    public Headmaster HeadmasterDtoToEntity(HeadmasterDTO headmasterDTO){
        Headmaster headmaster = modelMapper.map(headmasterDTO, Headmaster.class);
        mapSchoolIdToEntity(headmasterDTO, headmaster);
        return headmaster;
    }

    public void mapSchoolIdToEntity(HeadmasterDTO headmasterDTO, Headmaster headmaster){
        if(headmasterDTO.getSchoolId() != null){
            Optional<School> schoolOptional = schoolRepository.findById(headmasterDTO.getSchoolId());

            if(schoolOptional.isPresent()){
                headmaster.setSchool(schoolOptional.get());
            } else {
                throw new EntityNotFoundException("School with ID " + headmasterDTO.getSchoolId() + " is not found");
            }
        }
    }

    public void mapSchoolEntityToId(Headmaster headmaster, HeadmasterDTO headmasterDTO){
        if(headmaster.getSchool() != null){
            headmasterDTO.setSchoolId(headmaster.getSchool().getId());
        }
    }
}
