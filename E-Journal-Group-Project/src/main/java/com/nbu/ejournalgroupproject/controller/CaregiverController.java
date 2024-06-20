package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.service.CaregiverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/caregivers")
public class CaregiverController {
    private CaregiverService caregiverService;

    @GetMapping
    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverService.getAllCaregivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaregiverDTO> getCaregiverById(@PathVariable Long id) {
        CaregiverDTO caregiverDTO = caregiverService.getCaregiverById(id);
        return caregiverDTO != null ? ResponseEntity.ok(caregiverDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public CaregiverDTO createCaregiver(@Valid @RequestBody CaregiverDTO caregiverDTO) {
        return caregiverService.createCaregiver(caregiverDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaregiver(@PathVariable Long id) {
        caregiverService.deleteCaregiver(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaregiverDTO> updateCaregiver(@PathVariable Long id, @Valid @RequestBody CaregiverDTO caregiverDTO) {
        CaregiverDTO updatedCaregiver = caregiverService.updateCaregiver(id, caregiverDTO);
        return ResponseEntity.ok(updatedCaregiver);
    }

    @PostMapping("/{caregiverId}/students/{studentId}")
    public ResponseEntity<CaregiverDTO> addStudentToCaregiver(@PathVariable Long caregiverId, @PathVariable Long studentId) {
        CaregiverDTO updatedCaregiver = caregiverService.addStudentToCaregiver(caregiverId, studentId);
        return ResponseEntity.ok(updatedCaregiver);
    }

    @DeleteMapping("/{caregiverId}/students/{studentId}")
    public ResponseEntity<CaregiverDTO> removeStudentFromCaregiver(@PathVariable Long caregiverId, @PathVariable Long studentId) {
        CaregiverDTO updatedCaregiver = caregiverService.removeStudentFromCaregiver(caregiverId, studentId);
        return ResponseEntity.ok(updatedCaregiver);
    }
}