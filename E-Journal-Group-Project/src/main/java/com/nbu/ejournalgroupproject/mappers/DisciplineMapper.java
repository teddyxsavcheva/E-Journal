package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.DisciplineDTO;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DisciplineMapper {

    private final DisciplineTypeRepository disciplineTypeRepository;

    public DisciplineDTO convertToDto(Discipline discipline) {

        DisciplineDTO disciplineDTO = new DisciplineDTO();

        disciplineDTO.setId(discipline.getId());
        disciplineDTO.setName(discipline.getName());
        // New method, because I want to give the DTO only the id
        setDisciplineTypeIdInDisciplineDTO(discipline, disciplineDTO);

        return disciplineDTO;
    }

    public Discipline convertToEntity(DisciplineDTO disciplineDTO) {

        Discipline discipline = new Discipline();

        discipline.setName(disciplineDTO.getName());

        DisciplineType disciplineType = disciplineTypeRepository.findById(disciplineDTO.getDisciplineTypeId())
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + disciplineDTO.getDisciplineTypeId()));

        discipline.setDisciplineType(disciplineType);

        return discipline;
    }

    public void setDisciplineTypeIdInDisciplineDTO(Discipline discipline, DisciplineDTO disciplineDTO) {
        if (discipline.getDisciplineType() != null) {
            disciplineDTO.setDisciplineTypeId(discipline.getDisciplineType().getId());
        }
    }


}
