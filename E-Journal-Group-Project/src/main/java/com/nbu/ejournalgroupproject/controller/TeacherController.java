package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class TeacherController {

    private final TeacherService teacherService;

    @RequestMapping(value = {"/teacher"})
    @ResponseBody
    public ResponseEntity<List<TeacherDTO>> getTeachers(){
        try{
            List<TeacherDTO> teachers = teacherService.getTeachers();
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = {"/teacher"})
    @ResponseBody
    public ResponseEntity<String> createTeacher(@RequestBody TeacherDTO newTeacher){
        try{
            teacherService.createTeacher(newTeacher);
            return new ResponseEntity<>("Teacher created successfully", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/teacher/from_school/{id}"})
    @ResponseBody
    public ResponseEntity<List<TeacherDTO>> getTeachersFromSchool(@PathVariable Long id){
        try{
            List<TeacherDTO> teachers = teacherService.getTeachersFromSchool(id);
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value={"teacher/{id}"})
    @ResponseBody
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id){
        try {
            TeacherDTO teacher = teacherService.getTeacher(id);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/teacher/{id}"})
    @ResponseBody
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id){
        try{
            if (teacherService.deleteTeacher(id)){
                return new ResponseEntity<>("Teacher deleted seccessfully", HttpStatus.OK);
            }
            else{
                throw new EntityNotFoundException("Teacher not found.");
            }
        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
