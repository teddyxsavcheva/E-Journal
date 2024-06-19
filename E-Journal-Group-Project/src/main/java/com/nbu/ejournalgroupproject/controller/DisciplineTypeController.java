package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDto;
import com.nbu.ejournalgroupproject.service.DisciplineTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/discipline-types")
@RestController
public class DisciplineTypeController {

    private final DisciplineTypeService disciplineTypeService;

    @GetMapping("/")
    public ResponseEntity<List<DisciplineTypeDto>> getAllDisciplineType() {

        return ResponseEntity.ok(disciplineTypeService.getAllDisciplineTypes());

    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineTypeDto> getDisciplineTypeById(@PathVariable Long id) {

        return ResponseEntity.ok(disciplineTypeService.getDisciplineTypeById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineTypeDto> updateDisciplineType(@Valid @RequestBody DisciplineTypeDto disciplineTypeDto, @PathVariable Long id) {

        DisciplineTypeDto updatedDto = disciplineTypeService.updateDisciplineType(disciplineTypeDto, id);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);

    }

    @PostMapping("/")
    public ResponseEntity<DisciplineTypeDto> createDisciplineType(@Valid @RequestBody DisciplineTypeDto disciplineTypeDto) {

        DisciplineTypeDto createdDto = disciplineTypeService.createDisciplineType(disciplineTypeDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDisciplineType(@PathVariable Long id) {

        disciplineTypeService.deleteDisciplineType(id);

        return ResponseEntity.ok(HttpStatus.OK);

    }

}
