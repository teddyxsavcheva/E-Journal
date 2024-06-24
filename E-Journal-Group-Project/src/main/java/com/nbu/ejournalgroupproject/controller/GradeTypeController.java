package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.service.GradeTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/gradeTypes")
public class GradeTypeController {
    private final GradeTypeService gradeTypeService;

    @GetMapping("/")
    public List<GradeTypeDTO> getAllGradeTypes() {
        return gradeTypeService.getAllGradeTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeTypeDTO> getGradeTypeById(@PathVariable Long id) {
        GradeTypeDTO gradeTypeDTO = gradeTypeService.getGradeTypeById(id);
        return gradeTypeDTO != null ? ResponseEntity.ok(gradeTypeDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public GradeTypeDTO createGradeType(@Valid @RequestBody GradeTypeDTO gradeTypeDTO) {
        return gradeTypeService.createGradeType(gradeTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGradeType(@PathVariable Long id) {
        gradeTypeService.deleteGradeType(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeTypeDTO> updateGradeType(@PathVariable Long id, @Valid @RequestBody GradeTypeDTO gradeTypeDTO) {
        GradeTypeDTO updatedGradeType = gradeTypeService.updateGradeType(id, gradeTypeDTO);
        return ResponseEntity.ok(updatedGradeType);
    }
}