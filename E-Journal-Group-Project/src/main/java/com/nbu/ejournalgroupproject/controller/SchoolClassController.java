package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.service.SchoolClassService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/school-class")
@RestController
public class SchoolClassController {
    private final SchoolClassService schoolClassService;

    @GetMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<List<SchoolClassDTO>> getSchoolClasses(){
        return ResponseEntity.ok(schoolClassService.getSchoolClasses());
    }

    @GetMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolClassDTO> getSchoolClassById(@PathVariable Long id){
        return ResponseEntity.ok(schoolClassService.getSchoolClas(id));
    }

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<SchoolClassDTO> createSchoolClass(@RequestBody SchoolClassDTO schoolClassDTO){
        SchoolClassDTO newSchoolClass = schoolClassService.createSchoolClass(schoolClassDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSchoolClass);
    }

    @PutMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolClassDTO> updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClassDTO schoolClassDTO){
//        SchoolClassDTO newSchoolClass = schoolClassService.updateSchoolClass(id, schoolClassDTO);
//        return ResponseEntity.status(HttpStatus.OK).body(newSchoolClass);
        return null;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteSchoolClass(@PathVariable Long id) {
        schoolClassService.deleteSchoolClass(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
