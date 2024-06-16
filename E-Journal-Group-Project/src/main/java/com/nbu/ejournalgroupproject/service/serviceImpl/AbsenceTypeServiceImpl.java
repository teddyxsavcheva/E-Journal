package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import com.nbu.ejournalgroupproject.model.AbsenceType;
import com.nbu.ejournalgroupproject.repository.AbsenceTypeRepository;
import com.nbu.ejournalgroupproject.service.AbsenceTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceTypeServiceImpl implements AbsenceTypeService {
    private AbsenceTypeRepository absenceTypeRepository;
    private ModelMapper modelMapper;

    @Override
    public AbsenceTypeDTO getAbsenceTypeById(Long id){
        AbsenceType absenceType = absenceTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AbsenceType not found with id " + id));
        return modelMapper.map(absenceType, AbsenceTypeDTO.class);
    }

    @Override
    public List<AbsenceTypeDTO> getAllAbsenceTypes(){
        List<AbsenceType> absenceTypes = absenceTypeRepository.findAll();
        return absenceTypes.stream()
                .map(absenceType -> modelMapper.map(absenceType, AbsenceTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceTypeDTO createAbsenceType(AbsenceTypeDTO absenceTypeDTO){
        AbsenceType absenceType = modelMapper.map(absenceTypeDTO, AbsenceType.class);
        AbsenceType createdAbsenceType = absenceTypeRepository.save(absenceType);
        return modelMapper.map(createdAbsenceType, AbsenceTypeDTO.class);
    }

    @Override
    public void deleteAbsenceType(Long id){
        absenceTypeRepository.deleteById(id);
    }

    @Override
    public AbsenceTypeDTO updateAbsenceType(Long id, AbsenceTypeDTO absenceTypeDTO){
        AbsenceType absenceType = absenceTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AbsenceType not found with id " + id));
        modelMapper.map(absenceTypeDTO, absenceType);
        AbsenceType updateAbsenceType = absenceTypeRepository.save(absenceType);
        return modelMapper.map(updateAbsenceType, AbsenceTypeDTO.class);
    }
}
