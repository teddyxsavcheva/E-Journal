package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;
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

    public DisciplineDto convertToDto(Discipline discipline) {

        DisciplineDto disciplineDto = new DisciplineDto();

        disciplineDto.setId(discipline.getId());
        disciplineDto.setName(discipline.getName());
        // New method, because I also want to check if the id is present in the DB
        setDisciplineTypeIdInDisciplineDto(discipline, disciplineDto);

        return disciplineDto;
    }

    public Discipline convertToEntity(DisciplineDto disciplineDto) {

        Discipline discipline = new Discipline();

        discipline.setName(disciplineDto.getName());

        DisciplineType disciplineType = disciplineTypeRepository.findById(disciplineDto.getDisciplineTypeId())
                .orElseThrow(() -> new EntityNotFoundException("No DisciplineType found with id " + disciplineDto.getDisciplineTypeId()));

        discipline.setDisciplineType(disciplineType);

        return discipline;
    }

    public void setDisciplineTypeIdInDisciplineDto(Discipline discipline, DisciplineDto disciplineDto) {

        DisciplineType disciplineType = discipline.getDisciplineType();

        if (disciplineType != null && disciplineTypeRepository.existsById(disciplineType.getId())) {
            disciplineDto.setDisciplineTypeId(disciplineType.getId());
        }

        else {
            throw new EntityNotFoundException("No DisciplineType found with id " + disciplineType.getId());
        }
    }


}
