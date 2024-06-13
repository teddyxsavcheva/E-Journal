package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.SchoolTypeDTO;
import com.nbu.ejournalgroupproject.service.SchoolTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/school-type")
@RestController
public class SchoolTypeController {

    private final SchoolTypeService schoolTypeService;

    @RequestMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<List<SchoolTypeDTO>> getSchoolTypes(){
        return ResponseEntity.ok(schoolTypeService.getAllSchoolTypes());
    }

    @RequestMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolTypeDTO> getSchoolTypeById(@PathVariable Long id){
        return ResponseEntity.ok(schoolTypeService.getSchoolTypeById(id));
    }

    @PostMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<SchoolTypeDTO> createSchoolType(@RequestBody SchoolTypeDTO schoolTypeDTO){
        SchoolTypeDTO newSchoolType = schoolTypeService.createSchoolType(schoolTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSchoolType);
    }

    @PutMapping(value={"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolTypeDTO> updateSchoolType(@PathVariable Long id, @RequestBody SchoolTypeDTO schoolTypeDTO){
        SchoolTypeDTO newSchoolType = schoolTypeService.updateSchoolType(id, schoolTypeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newSchoolType);
    }

    @DeleteMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteSchoolType(@PathVariable Long id){
        schoolTypeService.deleteSchoolType(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }





}
