package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.*;
import com.nbu.ejournalgroupproject.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final DisciplineService disciplineService;
    private final GradeService gradeService;
    private final AbsenceService absenceService;

    @GetMapping("/")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return studentDTO != null ? ResponseEntity.ok(studentDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public StudentDTO createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @PostMapping("/{studentId}/caregivers/{caregiverId}")
    public ResponseEntity<StudentDTO> addCaregiverToStudent(@PathVariable Long studentId, @PathVariable Long caregiverId) {
        StudentDTO updatedStudent = studentService.addCaregiverToStudent(studentId, caregiverId);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}/caregivers/{caregiverId}")
    public ResponseEntity<StudentDTO> removeCaregiverFromStudent(@PathVariable Long studentId, @PathVariable Long caregiverId) {
        StudentDTO updatedStudent = studentService.removeCaregiverFromStudent(studentId, caregiverId);
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping("/{studentId}/disciplines")
    public List<DisciplineDto> getStudentDisciplines(@PathVariable Long studentId) {
        return disciplineService.getDisciplinesByStudentId(studentId);
    }

    @GetMapping("/{studentId}/disciplines/{disciplineId}/grades")
    public List<String> getStudentGrades(@PathVariable Long studentId, @PathVariable Long disciplineId) {
        return gradeService.getGradesByStudentAndDiscipline(studentId, disciplineId);
    }

    @GetMapping("/{studentId}/disciplines/{disciplineId}/absences")
    public List<Long> getStudentAbsences(@PathVariable Long studentId, @PathVariable Long disciplineId) {
        return absenceService.getAbsencesByStudentAndDiscipline(studentId, disciplineId);
    }

    @GetMapping("/school-class/{id}")
    public ResponseEntity<List<StudentDTO>> getStudentByClassId(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentsFromClass(id));
    }

    @GetMapping("/{studentId}/discipline/{disciplineId}/grades")
    public ResponseEntity<List<GradeDTO>> getStudentGradesByDisciplineId(@PathVariable Long studentId, @PathVariable Long disciplineId) {
        return ResponseEntity.ok(gradeService.getGradeObjectsByStudentAndDiscipline(studentId, disciplineId));
    }

    @GetMapping("/{studentId}/discipline/{disciplineId}/absences")
    public ResponseEntity<List<AbsenceDTO>> getStudentAbsencesByDisciplineId(@PathVariable Long studentId, @PathVariable Long disciplineId) {
        return ResponseEntity.ok(absenceService.getAbsenceObjectsByStudentAndDiscipline(studentId, disciplineId));
    }


}