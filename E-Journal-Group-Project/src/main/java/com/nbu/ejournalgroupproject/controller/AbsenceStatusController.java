package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import com.nbu.ejournalgroupproject.service.AbsenceStatusService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/absenceStatuses")
public class AbsenceStatusController {
    private final AbsenceStatusService absenceStatusService;

    @GetMapping("/")
    public List<AbsenceStatusDTO> getAllAbsenceStatuses() {
        return absenceStatusService.getAllAbsenceStatuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceStatusDTO> getAbsenceStatusById(@PathVariable Long id) {
        AbsenceStatusDTO absenceStatusDTO = absenceStatusService.getAbsenceStatusById(id);
        return absenceStatusDTO != null ? ResponseEntity.ok(absenceStatusDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public AbsenceStatusDTO createAbsenceStatus(@Valid @RequestBody AbsenceStatusDTO absenceStatusDTO) {
        return absenceStatusService.createAbsenceStatus(absenceStatusDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsenceStatusById(@PathVariable Long id) {
        absenceStatusService.deleteAbsenceStatus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceStatusDTO> updateAbsenceStatus(@PathVariable Long id, @Valid @RequestBody AbsenceStatusDTO absenceStatusDTO) {
        AbsenceStatusDTO updatedAbsenceStatus = absenceStatusService.updateAbsenceStatus(id, absenceStatusDTO);
        return ResponseEntity.ok(updatedAbsenceStatus);
    }
}