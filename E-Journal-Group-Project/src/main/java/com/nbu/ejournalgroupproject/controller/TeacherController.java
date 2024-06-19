package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.service.TeacherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/teacher")
@RestController
public class TeacherController {

    private final TeacherService teacherService;

    @RequestMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<List<TeacherDTO>> getTeachers(){
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @RequestMapping(value = {"/school-id/{id}"})
    @ResponseBody
    public ResponseEntity<List<TeacherDTO>> getTeachersFromSchoolId(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeachersFromSchool(id));
    }

    @RequestMapping(value={"/{id}"})
    @ResponseBody
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody @Valid TeacherDTO newTeacher){
        TeacherDTO teacherDTO = teacherService.createTeacher(newTeacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherDTO);
    }

    @PutMapping(value = {"{id}"})
    @ResponseBody
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @RequestBody @Valid TeacherDTO teacherDTO) {
        TeacherDTO updatedTeacherDTO = teacherService.updateTeacher(id, teacherDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTeacherDTO);
    }

    @DeleteMapping(value = {"{id}"})
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/{teacherId}/qualifications/{qualificationId}")
    public ResponseEntity<TeacherDTO> addQualificationToTeacher(@PathVariable Long teacherId, @PathVariable Long qualificationId) {
        TeacherDTO updatedDto = teacherService.addQualificationToTeacher(teacherId, qualificationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedDto);
    }

    @DeleteMapping("/{teacherId}/qualifications/{qualificationId}")
    public ResponseEntity<TeacherDTO> removeQualificationFromTeacher(@PathVariable Long teacherId, @PathVariable Long qualificationId) {
        TeacherDTO updatedDto = teacherService.deleteQualificationFromTeacher(teacherId, qualificationId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
}
