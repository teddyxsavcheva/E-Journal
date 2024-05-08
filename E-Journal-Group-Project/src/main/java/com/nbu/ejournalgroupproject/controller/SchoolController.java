package com.nbu.ejournalgroupproject.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class SchoolController {

    private final SchoolService schoolService;

    @RequestMapping(value = {"/school"})
    @ResponseBody
    public ResponseEntity<List<SchoolDTO>> getSchools(){
        try{
            List<SchoolDTO> schools = schoolService.getSchools();
            return new ResponseEntity<>(schools, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"school/{id}"})
    @ResponseBody
    public ResponseEntity<SchoolDTO> getSchool(@PathVariable long id){
        try{
            SchoolDTO school = schoolService.getSchool(id);
            return new ResponseEntity<>(school, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value={"/school"})
    @ResponseBody
    public ResponseEntity<String> createSchool(@RequestBody SchoolDTO newSchool){
        try{
            schoolService.createSchool(newSchool);
            return new ResponseEntity<>("School created successfully", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/school/{id}"})
    @ResponseBody
    public ResponseEntity<String> deleteSchool(@PathVariable Long id){
        try{
            if(schoolService.deleteSchool(id)){
                return new ResponseEntity<>("School deleted successfully.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("School not found.");
            }
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

