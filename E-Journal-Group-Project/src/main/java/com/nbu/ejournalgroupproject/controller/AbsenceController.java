package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import com.nbu.ejournalgroupproject.service.AbsenceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/absences")
public class AbsenceController {
    private final AbsenceService absenceService;

    @GetMapping("/")
    public List<AbsenceDTO> getAllAbsences() {
        return absenceService.getAllAbsences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> getAbsenceById(@PathVariable Long id) {
        AbsenceDTO absenceDTO = absenceService.getAbsenceById(id);
        return absenceDTO != null ? ResponseEntity.ok(absenceDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public AbsenceDTO createAbsence(@Valid @RequestBody AbsenceDTO absenceDTO) {
        return absenceService.createAbsence(absenceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsenceById(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceDTO> updateAbsence(@PathVariable Long id, @Valid @RequestBody AbsenceDTO absenceDTO) {
        AbsenceDTO updatedAbsence = absenceService.updateAbsence(id, absenceDTO);
        return ResponseEntity.ok(updatedAbsence);
    }
}