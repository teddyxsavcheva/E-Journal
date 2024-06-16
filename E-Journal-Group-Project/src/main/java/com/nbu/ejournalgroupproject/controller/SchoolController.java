package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/school")
@RestController
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<List<SchoolDTO>> getSchools(){
        return ResponseEntity.ok(schoolService.getSchools());
    }

    @RequestMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable long id){
        return ResponseEntity.ok(schoolService.getSchool(id));
    }

    @PostMapping(value={"/"})
    @ResponseBody
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody SchoolDTO newSchool){
        SchoolDTO schoolDTO = schoolService.createSchool(newSchool);
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolDTO);
    }

    @DeleteMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteSchool(@PathVariable Long id){
        schoolService.deleteSchool(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable Long id, @RequestBody SchoolDTO schoolDTO){
        SchoolDTO newSchool = schoolService.updateSchool(id, schoolDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newSchool);
    }
}

