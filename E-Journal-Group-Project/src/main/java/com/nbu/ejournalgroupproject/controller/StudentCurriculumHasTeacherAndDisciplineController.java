package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumHasTeacherAndDisciplineDto;
import com.nbu.ejournalgroupproject.model.StudentCurriculumHasTeacherAndDiscipline;
import com.nbu.ejournalgroupproject.service.StudentCurriculumHasTeacherAndDisciplineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/curriculums-teachers-disciplines")
@RestController
public class StudentCurriculumHasTeacherAndDisciplineController {

    private final StudentCurriculumHasTeacherAndDisciplineService service;

    @GetMapping("/")
    public ResponseEntity<List<StudentCurriculumHasTeacherAndDisciplineDto>> getAllCurriculumsTeachersDisciplines() {

        return ResponseEntity.ok(service.getAllCurriculumHasTeacherAndDiscipline());

    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCurriculumHasTeacherAndDisciplineDto> getCurriculumTeacherDisciplineById(@PathVariable Long id) {

        return ResponseEntity.ok(service.getCurriculumHasTeacherAndDisciplineById(id));

    }

    @PostMapping("/")
    public ResponseEntity<StudentCurriculumHasTeacherAndDisciplineDto> createCurriculumTeacherDiscipline(@RequestBody StudentCurriculumHasTeacherAndDisciplineDto dto) {

        StudentCurriculumHasTeacherAndDisciplineDto createdDto = service.createCurriculumHasTeacherAndDiscipline(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCurriculumHasTeacherAndDisciplineDto> updateCurriculumTeacherDiscipline(@RequestBody StudentCurriculumHasTeacherAndDisciplineDto dto, @PathVariable Long id) {

        StudentCurriculumHasTeacherAndDisciplineDto updatedDto = service.updateCurriculumHasTeacherAndDiscipline(dto, id);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCurriculumTeacherDisciplineById(@PathVariable Long id) {

        service.deleteCurriculumHasTeacherAndDiscipline(id);

        return ResponseEntity.ok(HttpStatus.OK);

    }
}
