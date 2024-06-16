package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import com.nbu.ejournalgroupproject.model.Absence;
import com.nbu.ejournalgroupproject.repository.AbsenceRepository;
import com.nbu.ejournalgroupproject.service.AbsenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {
    private AbsenceRepository absenceRepository;
    private ModelMapper modelMapper;

    @Override
    public AbsenceDTO getAbsenceById(Long id){
        Absence absence = absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Absence not found with id " + id));
        return modelMapper.map(absence, AbsenceDTO.class);
    }

    @Override
    public List<AbsenceDTO> getAllAbsences(){
        List<Absence> absences = absenceRepository.findAll();
        return absences.stream()
                .map(absence -> modelMapper.map(absence, AbsenceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceDTO createAbsence(AbsenceDTO absenceDTO) {
        Absence absence = modelMapper.map(absenceDTO, Absence.class);
        Absence createdAbsence = absenceRepository.save(absence);
        return modelMapper.map(createdAbsence, AbsenceDTO.class);
    }

    @Override
    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }

    @Override
    public AbsenceDTO updateAbsence(Long id, AbsenceDTO absenceDTO) {
        Absence absence = absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Absence not found with id " + id));
        modelMapper.map(absenceDTO, absence);
        Absence updatedAbsence = absenceRepository.save(absence);
        return modelMapper.map(updatedAbsence, AbsenceDTO.class);
    }
}
