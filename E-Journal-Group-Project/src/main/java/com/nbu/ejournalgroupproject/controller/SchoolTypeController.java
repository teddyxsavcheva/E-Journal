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
        try{
            List<SchoolTypeDTO> schoolTypes = schoolTypeService.getAllSchoolTypes();
            return new ResponseEntity<>(schoolTypes, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolTypeDTO> getSchoolTypeById(@PathVariable Long id){
        try{
            SchoolTypeDTO schoolType = schoolTypeService.getSchoolTypeById(id);
            return new ResponseEntity<>(schoolType, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<SchoolTypeDTO> createSchoolType(@RequestBody SchoolTypeDTO schoolTypeDTO){
        try {
            SchoolTypeDTO newSchoolTypeDTO1 = schoolTypeService.createSchoolType(schoolTypeDTO);
            return new ResponseEntity<>(newSchoolTypeDTO1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value={"/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolTypeDTO> updateSchoolType(@PathVariable Long id, @RequestBody SchoolTypeDTO schoolTypeDTO){
        try{
            SchoolTypeDTO newSchoolTypeDTO= schoolTypeService.updateSchoolType(id, schoolTypeDTO);
            return new ResponseEntity<>(newSchoolTypeDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<String> deleteSchoolType(@PathVariable Long id){
        try{
            schoolTypeService.deleteSchoolType(id);
            return new ResponseEntity<>("SchoolType deleted successfully", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
