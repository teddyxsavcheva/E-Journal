package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import com.nbu.ejournalgroupproject.mappers.AbsenceMapper;
import com.nbu.ejournalgroupproject.model.Absence;
import com.nbu.ejournalgroupproject.repository.AbsenceRepository;
import com.nbu.ejournalgroupproject.service.AbsenceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final AbsenceMapper absenceMapper;

    @Override
    public AbsenceDTO getAbsenceById(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id " + id));
        return absenceMapper.toDTO(absence);
    }

    @Override
    public List<AbsenceDTO> getAllAbsences() {
        List<Absence> absences = absenceRepository.findAll();
        return absences.stream()
                .map(absenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceDTO createAbsence(@Valid AbsenceDTO absenceDTO) {
        Absence absence = absenceMapper.toEntity(absenceDTO);
        Absence createdAbsence = absenceRepository.save(absence);
        return absenceMapper.toDTO(createdAbsence);
    }

    @Override
    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }

    @Override
    public AbsenceDTO updateAbsence(Long id, @Valid AbsenceDTO absenceDTO) {
        Absence existingAbsence = absenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Absence not found with id " + id));

        Absence updatedAbsence = absenceMapper.toEntity(absenceDTO);
        updatedAbsence.setId(existingAbsence.getId());

        Absence savedAbsence = absenceRepository.save(updatedAbsence);
        return absenceMapper.toDTO(savedAbsence);
    }

    @Override
    public List<Long> getAbsencesByStudentAndDiscipline(Long studentId, Long disciplineId) {
        List<Long> counts = new ArrayList<>();
        Long excusedCount = absenceRepository.excusedAbsencesCountByStudentAndDiscipline(studentId, disciplineId);
        Long notExcusedCount = absenceRepository.notExcusedAbsencesCountByStudentAndDiscipline(studentId, disciplineId);

        counts.add(excusedCount);
        counts.add(notExcusedCount);

        return counts;
    }

    @Override
    public List<AbsenceDTO> getAbsenceObjectsByStudentAndDiscipline(Long studentId, Long disciplineId) {
        return absenceRepository.getAllByStudentIdAndDisciplineId(studentId, disciplineId)
                .stream().map(absenceMapper::toDTO).collect(Collectors.toList());
    }
}