package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;
import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.service.DisciplineService;
import jakarta.validation.Valid;
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
    public ResponseEntity<DisciplineDto> createDiscipline(@Valid @RequestBody DisciplineDto disciplineDto) {

        DisciplineDto createdDisciplineDto = disciplineService.createDiscipline(disciplineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisciplineDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineDto> updateDiscipline(@Valid @RequestBody DisciplineDto disciplineDto, @PathVariable Long id) {

        DisciplineDto updatedDisciplineDto = disciplineService.updateDiscipline(disciplineDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDisciplineDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDiscipline(@PathVariable Long id) {

        disciplineService.deleteDiscipline(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/qualifications")
    public ResponseEntity<List<TeacherQualificationDto>> getQualificationsByDisciplineId(@PathVariable Long id) {
        return ResponseEntity.ok(disciplineService.getQualificationsByDisciplineId(id));
    }

    // Add and Remove methods for the m:n table

    @PostMapping("/{disciplineId}/qualifications/{qualificationId}")
    public ResponseEntity<DisciplineDto > addQualificationToDiscipline(@PathVariable Long disciplineId, @PathVariable Long qualificationId) {
        DisciplineDto createdDisciplineDto = disciplineService.addQualificationToDiscipline(disciplineId, qualificationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisciplineDto);
    }

    @DeleteMapping("/{disciplineId}/qualifications/{qualificationId}")
    public ResponseEntity<DisciplineDto > removeQualificationFromDiscipline(@PathVariable Long disciplineId, @PathVariable Long qualificationId) {
        DisciplineDto updatedDisciplineDto = disciplineService.removeQualificationFromDiscipline(disciplineId, qualificationId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDisciplineDto);
    }

}
