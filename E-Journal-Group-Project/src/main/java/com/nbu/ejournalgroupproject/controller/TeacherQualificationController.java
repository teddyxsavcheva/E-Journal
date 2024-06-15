package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.service.TeacherQualificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/teacher-qualifications")
@RestController
public class TeacherQualificationController {

    private final TeacherQualificationService teacherQualificationService;

    @GetMapping("/")
    public ResponseEntity<List<TeacherQualificationDto>> getAllTeacherQualifications() {

        return ResponseEntity.ok(teacherQualificationService.getAllTeacherQualifications());

    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherQualificationDto> getTeacherQualificationById(@PathVariable Long id) {

        return ResponseEntity.ok(teacherQualificationService.getTeacherQualificationById(id));

    }

    @PostMapping("/")
    public ResponseEntity<TeacherQualificationDto> createTeacherQualification(@RequestBody TeacherQualificationDto teacherQualificationDto) {

        TeacherQualificationDto createdDto = teacherQualificationService.createTeacherQualification(teacherQualificationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherQualificationDto> updateTeacherQualification(@RequestBody TeacherQualificationDto teacherQualificationDto, @PathVariable Long id) {

        TeacherQualificationDto updatedDto = teacherQualificationService.updateTeacherQualification(teacherQualificationDto, id);

        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeacherQualification(@PathVariable Long id) {

        teacherQualificationService.deleteTeacherQualification(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Add and Remove teachers and disciplines in m:n tables

    @PostMapping("/{qualificationId}/teachers/{teacherId}")
    public ResponseEntity<TeacherQualificationDto> addTeacherToQualification(@PathVariable Long qualificationId, @PathVariable Long teacherId) {
        TeacherQualificationDto updatedDto = teacherQualificationService.addTeacherToQualification(qualificationId, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedDto);
    }

    @DeleteMapping("/{qualificationId}/teachers/{teacherId}")
    public ResponseEntity<TeacherQualificationDto> removeTeacherFromQualification(@PathVariable Long qualificationId, @PathVariable Long teacherId) {
        TeacherQualificationDto updatedDto = teacherQualificationService.removeTeacherFromQualification(qualificationId, teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @PostMapping("/{qualificationId}/disciplines/{disciplineId}")
    public ResponseEntity<TeacherQualificationDto> addDisciplineToQualification(@PathVariable Long qualificationId, @PathVariable Long disciplineId) {
        TeacherQualificationDto updatedDto = teacherQualificationService.addDisciplineToQualification(qualificationId, disciplineId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedDto);
    }

    @DeleteMapping("/{qualificationId}/disciplines/{disciplineId}")
    public ResponseEntity<TeacherQualificationDto> removeDisciplineFromQualification(@PathVariable Long qualificationId, @PathVariable Long disciplineId) {
        TeacherQualificationDto updatedDto = teacherQualificationService.removeDisciplineFromQualification(qualificationId, disciplineId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

}
