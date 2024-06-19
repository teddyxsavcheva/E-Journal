package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumDTO;
import com.nbu.ejournalgroupproject.service.StudentCurriculumService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/student-curriculum")
@RestController
public class StudentCurriculumController {

    private final StudentCurriculumService studentCurriculumService;

    @GetMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<List<StudentCurriculumDTO>> getStudentCurriculums(){
        return ResponseEntity.ok(studentCurriculumService.getStudentCurriculums());
    }

    @RequestMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<StudentCurriculumDTO> getStudentCurriculumById(@PathVariable long id){
        return ResponseEntity.ok(studentCurriculumService.getStudentCurriculum(id));
    }

    @PostMapping(value={"/"})
    @ResponseBody
    public ResponseEntity<StudentCurriculumDTO> createStudentCurriculum(@Valid @RequestBody StudentCurriculumDTO newStudentCurriculum){
        StudentCurriculumDTO studentCurriculumDTO = studentCurriculumService.createStudentCurriculum(newStudentCurriculum);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentCurriculumDTO);
    }

    @DeleteMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteStudentCurriculum(@PathVariable Long id){
        studentCurriculumService.deleteStudentCurriculum(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<StudentCurriculumDTO> updateStudentCurriculum(@PathVariable Long id, @Valid @RequestBody StudentCurriculumDTO studentCurriculumDTO){
        StudentCurriculumDTO newStudentCurriculum = studentCurriculumService.updateStudentCurriculum(id, studentCurriculumDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newStudentCurriculum);
    }
}

