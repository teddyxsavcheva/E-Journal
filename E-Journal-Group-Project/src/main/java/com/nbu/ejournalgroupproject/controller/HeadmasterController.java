package com.nbu.ejournalgroupproject.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.service.HeadmasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class HeadmasterController {
    private final HeadmasterService headmasterService;

    @GetMapping(value = {"/headmaster"})
    @ResponseBody
    public ResponseEntity<List<HeadmasterDTO>> getHeadmasters(){
        try{
            List<HeadmasterDTO> headmasters = headmasterService.getHeadmasters();
            return new ResponseEntity<>(headmasters, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/headmaster/{id}"})
    @ResponseBody
    public ResponseEntity<HeadmasterDTO> getHeadmasterById(@PathVariable Long id){
        try{
            HeadmasterDTO headmasterDTO = headmasterService.getHeadmaster(id);
            return new ResponseEntity<>(headmasterDTO, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/headmaster/school_id/{id}"})
    @ResponseBody
    public ResponseEntity<HeadmasterDTO> findHeadmasterBySchool(@PathVariable long id){
        try{
            HeadmasterDTO headmasterDTO = headmasterService.getHeadmasterBySchoolID(id);
            return new ResponseEntity<>(headmasterDTO, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/headmaster")
    @ResponseBody
    public ResponseEntity<String> createHeadmaster(@RequestBody HeadmasterDTO headmasterDTO){
        try {
            headmasterService.createHeadmaster(headmasterDTO);
            return new ResponseEntity<>("Headmaster created successfully", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/headmaster/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteHeadmaster(@PathVariable Long id){
        try{
            if(headmasterService.deleteHeadmaster(id)){
                return new ResponseEntity<>("Headmaster deleted successfully", HttpStatus.OK);
            }
            else{
                throw new EntityNotFoundException("Headmaster not found.");
            }
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/headmaster/{id}"})
    @ResponseBody
    public ResponseEntity<String> updateHeadmaster(@PathVariable Long id, @RequestBody HeadmasterDTO headmasterDTO){
        try{
            headmasterService.updateHeadmaster(id, headmasterDTO);
            return new ResponseEntity<>("Headmaster updated successfully.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
