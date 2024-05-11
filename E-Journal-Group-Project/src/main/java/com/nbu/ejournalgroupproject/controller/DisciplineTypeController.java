package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDTO;
import com.nbu.ejournalgroupproject.service.DisciplineTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/disciplineTypes")
@RestController
public class DisciplineTypeController {

    private final DisciplineTypeService disciplineTypeService;

    @GetMapping("/")
    public ResponseEntity<List<DisciplineTypeDTO>> getAllDisciplineType() {

        return ResponseEntity.ok(disciplineTypeService.getAllDisciplineTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineTypeDTO> getDisciplineTypeById(@PathVariable Long id) {

        return ResponseEntity.ok(disciplineTypeService.getDisciplineTypeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateDisciplineType(@RequestBody DisciplineTypeDTO disciplineTypeDTO, @PathVariable Long id) {

        disciplineTypeService.updateDisciplineType(disciplineTypeDTO, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createDisciplineType(@RequestBody DisciplineTypeDTO disciplineTypeDTO) {

        disciplineTypeService.createDisciplineType(disciplineTypeDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDisciplineType(@PathVariable Long id) {

        disciplineTypeService.deleteDisciplineType(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
