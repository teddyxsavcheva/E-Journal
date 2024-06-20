package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import com.nbu.ejournalgroupproject.service.StudentService;
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
}