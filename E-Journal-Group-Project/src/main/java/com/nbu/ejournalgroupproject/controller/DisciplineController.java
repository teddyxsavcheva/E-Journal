package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.DisciplineDTO;
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
    public ResponseEntity<List<DisciplineDTO>> getAllDisciplines() {

        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDisciplineById(@PathVariable Long id) {

        return ResponseEntity.ok(disciplineService.getDisciplineById(id));
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createDiscipline(@RequestBody DisciplineDTO disciplineDTO) {

        disciplineService.createDiscipline(disciplineDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateDiscipline(@RequestBody DisciplineDTO disciplineDTO, @PathVariable Long id) {

        disciplineService.updateDiscipline(disciplineDTO, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDiscipline(@PathVariable Long id) {

        disciplineService.deleteDiscipline(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
