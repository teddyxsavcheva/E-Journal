package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import com.nbu.ejournalgroupproject.service.GradeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/grades")
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/")
    public List<GradeDTO> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable Long id) {
        GradeDTO gradeDTO = gradeService.getGradeById(id);
        return gradeDTO != null ? ResponseEntity.ok(gradeDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public GradeDTO createGrade(@Valid @RequestBody GradeDTO gradeDTO) {
        return gradeService.createGrade(gradeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeDTO gradeDTO) {
        GradeDTO updatedGrade = gradeService.updateGrade(id, gradeDTO);
        return ResponseEntity.ok(updatedGrade);
    }
}