package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;
import com.nbu.ejournalgroupproject.service.DisciplineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/disciplines")
@RestController
public class DisciplineController {

    private DisciplineService disciplineService;

    @GetMapping("/")
    public ResponseEntity<List<DisciplineDto>> getAllDisciplines() {

        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDto> getDisciplineById(@PathVariable Long id) {

        return ResponseEntity.ok(disciplineService.getDisciplineById(id));
    }

    @PostMapping("/")
    public ResponseEntity<DisciplineDto> createDiscipline(@RequestBody DisciplineDto disciplineDto) {

        DisciplineDto createdDisciplineDto = disciplineService.createDiscipline(disciplineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisciplineDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineDto> updateDiscipline(@RequestBody DisciplineDto disciplineDto, @PathVariable Long id) {

        DisciplineDto updatedDisciplineDto = disciplineService.updateDiscipline(disciplineDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDisciplineDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDiscipline(@PathVariable Long id) {

        disciplineService.deleteDiscipline(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
