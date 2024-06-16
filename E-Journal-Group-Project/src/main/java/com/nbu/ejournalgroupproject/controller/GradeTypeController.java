package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.service.GradeTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/gradeTypes")
public class GradeTypeController {
    private final GradeTypeService gradeTypeService;

    @GetMapping
    public List<GradeTypeDTO> getAllGradeTypes() {
        return gradeTypeService.getAllGradeTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeTypeDTO> getGradeTypeById(@PathVariable Long id) {
        GradeTypeDTO gradeTypeDTO = gradeTypeService.getGradeTypeById(id);
        return gradeTypeDTO != null ? ResponseEntity.ok(gradeTypeDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public GradeTypeDTO createGradeType(@RequestBody GradeTypeDTO gradeTypeDTO) {
        return gradeTypeService.createGradeType(gradeTypeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGrade(@PathVariable Long id) {
        gradeTypeService.deleteGradeType(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeTypeDTO> updateGradeType(@PathVariable Long id, @RequestBody GradeTypeDTO gradeTypeDTO) {
        GradeTypeDTO updateGradeType = gradeTypeService.updateGradeType(id, gradeTypeDTO);
        return ResponseEntity.ok(updateGradeType);
    }
}
