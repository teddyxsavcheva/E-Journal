package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.user.User;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HeadmasterMapper {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    public HeadmasterDTO mapEntityToDto(Headmaster headmaster){
        HeadmasterDTO headmasterDTO = new HeadmasterDTO();
        headmasterDTO.setId(headmaster.getId());
        headmasterDTO.setName(headmaster.getName());
        headmasterDTO.setEmail(headmaster.getEmail());

        headmasterDTO.setSchoolId(getSchoolId(headmaster));

        // Checking for user
        headmasterDTO.setUserId(getUserId(headmaster));

        return headmasterDTO;
    }


    public Headmaster mapDtoToEntity(HeadmasterDTO headmasterDTO){
        Headmaster headmaster = new Headmaster();
        headmaster.setId(headmasterDTO.getId());
        headmaster.setName(headmasterDTO.getName());
        headmaster.setEmail(headmasterDTO.getEmail());

        headmaster.setSchool(getSchool(headmasterDTO));

        // Checking for user
        headmaster.setUser(getUser(headmasterDTO));

        return headmaster;
    }

    public Long getUserId(Headmaster headmaster) {
        return headmaster.getUser() != null ? headmaster.getUser().getId() : null;
    }

    public User getUser(HeadmasterDTO headmasterDTO) {
        if (headmasterDTO.getUserId() != null) {
            return userRepository.findById(headmasterDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("No User found with id " + headmasterDTO.getUserId()));
        } else {
            return null;
        }
    }

    private School getSchool(HeadmasterDTO headmasterDTO) {
        if (headmasterDTO.getSchoolId() != null) {
            return schoolRepository.findById(headmasterDTO.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("School with id " + headmasterDTO.getSchoolId() + " not found"));
        }
        else throw new IllegalArgumentException("School cannot be null");
    }

    private Long getSchoolId(Headmaster headmaster) {
        if(headmaster.getSchool() != null){
            return headmaster.getSchool().getId();
        }
        else throw new IllegalArgumentException("School cannot be null");
    }

}
