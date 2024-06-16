package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import com.nbu.ejournalgroupproject.service.AbsenceTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/absenceTypes")
public class AbsenceTypeController {
    private final AbsenceTypeService absenceTypeService;

    @GetMapping("/")
    public List<AbsenceTypeDTO> getAllAbsenceTypes() {
        return absenceTypeService.getAllAbsenceTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceTypeDTO> getAbsenceTypeById(@PathVariable Long id) {
        AbsenceTypeDTO absenceTypeDTO = absenceTypeService.getAbsenceTypeById(id);
        return absenceTypeDTO != null ? ResponseEntity.ok(absenceTypeDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public AbsenceTypeDTO createAbsenceType(@RequestBody AbsenceTypeDTO absenceTypeDTO) {
        return absenceTypeService.createAbsenceType(absenceTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsenceTypeById(@PathVariable Long id) {
        absenceTypeService.deleteAbsenceType(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceTypeDTO> updateAbsenceType(@PathVariable Long id, @RequestBody AbsenceTypeDTO absenceTypeDTO) {
        AbsenceTypeDTO updatedAbsenceType = absenceTypeService.updateAbsenceType(id, absenceTypeDTO);
        return ResponseEntity.ok(updatedAbsenceType);
    }
}
