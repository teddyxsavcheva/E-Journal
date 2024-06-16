package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.model.GradeType;
import com.nbu.ejournalgroupproject.repository.GradeTypeRepository;
import com.nbu.ejournalgroupproject.service.GradeTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeTypeServiceImpl implements GradeTypeService {
    private GradeTypeRepository gradeTypeRepository;
    private ModelMapper modelMapper;

    @Override
    public GradeTypeDTO getGradeTypeById(Long id){
        GradeType gradeType = gradeTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GradeType not found with id " + id));
        return modelMapper.map(gradeType, GradeTypeDTO.class);
    }

    @Override
    public List<GradeTypeDTO> getAllGradeTypes(){
        List<GradeType> gradeTypes = gradeTypeRepository.findAll();
        return gradeTypes.stream()
                .map(gradeType -> modelMapper.map(gradeType, GradeTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GradeTypeDTO createGradeType(GradeTypeDTO gradeTypeDTO) {
        GradeType gradeType = modelMapper.map(gradeTypeDTO, GradeType.class);
        GradeType createdGradeType = gradeTypeRepository.save(gradeType);
        return modelMapper.map(createdGradeType, GradeTypeDTO.class);
    }

    @Override
    public void deleteGradeType(Long id){
        gradeTypeRepository.deleteById(id);
    }

    @Override
    public GradeTypeDTO updateGradeType(Long id, GradeTypeDTO gradeTypeDTO) {
        GradeType gradeType = gradeTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GradeType not found with id " + id));
        modelMapper.map(gradeTypeDTO, gradeType);
        GradeType updateGradeType = gradeTypeRepository.save(gradeType);
        return modelMapper.map(updateGradeType, GradeTypeDTO.class);
    }
}
