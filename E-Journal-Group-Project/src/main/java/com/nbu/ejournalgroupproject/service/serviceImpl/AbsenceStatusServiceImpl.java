package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import com.nbu.ejournalgroupproject.model.AbsenceStatus;
import com.nbu.ejournalgroupproject.repository.AbsenceStatusRepository;
import com.nbu.ejournalgroupproject.service.AbsenceStatusService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceStatusServiceImpl implements AbsenceStatusService {
    private AbsenceStatusRepository absenceStatusRepository;
    private ModelMapper modelMapper;

    @Override
    public AbsenceStatusDTO getAbsenceStatusById(Long id){
        AbsenceStatus absenceStatus = absenceStatusRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AbsenceStatus not found with id " + id));
        return modelMapper.map(absenceStatus, AbsenceStatusDTO.class);
    }

    @Override
    public List<AbsenceStatusDTO> getAllAbsenceStatuses(){
        List<AbsenceStatus> absenceStatuses = absenceStatusRepository.findAll();
        return absenceStatuses.stream()
                .map(absenceStatus -> modelMapper.map(absenceStatus, AbsenceStatusDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceStatusDTO createAbsenceStatus(AbsenceStatusDTO absenceStatusDTO){
        AbsenceStatus absenceStatus = modelMapper.map(absenceStatusDTO, AbsenceStatus.class);
        AbsenceStatus createdAbsenceStatus = absenceStatusRepository.save(absenceStatus);
        return modelMapper.map(createdAbsenceStatus, AbsenceStatusDTO.class);
    }

    @Override
    public void deleteAbsenceStatus(Long id){
        absenceStatusRepository.deleteById(id);
    }

    @Override
    public AbsenceStatusDTO updateAbsenceStatus(Long id, AbsenceStatusDTO absenceStatusDTO){
        AbsenceStatus absenceStatus = absenceStatusRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AbsenceStatus not found with id " + id));
        modelMapper.map(absenceStatusDTO, absenceStatus);
        AbsenceStatus updateAbsenceStatus = absenceStatusRepository.save(absenceStatus);
        return modelMapper.map(updateAbsenceStatus, AbsenceStatusDTO.class);
    }
}
