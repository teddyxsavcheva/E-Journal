package com.nbu.ejournalgroupproject.controller;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.service.GradeService;
import com.nbu.ejournalgroupproject.service.HeadmasterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/headmaster")
@RestController
public class HeadmasterController {
    private final HeadmasterService headmasterService;
    private final GradeService gradeService;

    @GetMapping(value = {"/"})
    @ResponseBody
    public ResponseEntity<List<HeadmasterDTO>> getHeadmasters(){
        return ResponseEntity.ok(headmasterService.getHeadmasters());
    }

    @GetMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<HeadmasterDTO> getHeadmasterById(@PathVariable Long id){
        return ResponseEntity.ok(headmasterService.getHeadmaster(id));
    }

    @GetMapping(value = {"/school-id/{id}"})
    @ResponseBody
    public ResponseEntity<HeadmasterDTO> getHeadmasterBySchoolId(@PathVariable long id){
        return ResponseEntity.ok(headmasterService.getHeadmasterBySchoolID(id));
    }

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<HeadmasterDTO> createHeadmaster(@Valid @RequestBody HeadmasterDTO headmasterDTO){
        HeadmasterDTO newHeadmaster = headmasterService.createHeadmaster(headmasterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHeadmaster);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteHeadmaster(@PathVariable Long id) {
        headmasterService.deleteHeadmaster(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping(value = {"/{id}"})
    @ResponseBody
    public ResponseEntity<HeadmasterDTO> updateHeadmaster(@PathVariable Long id,@Valid @RequestBody HeadmasterDTO headmasterDTO){
        HeadmasterDTO newHeadmaster = headmasterService.updateHeadmaster(id, headmasterDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newHeadmaster);
    }

    @GetMapping("/{id}/avg-grade-for-school")
    public List<String> avgGradeForSchool(@PathVariable Long id){
        return gradeService.findAvgGradeForSchool(id);
    }

    @GetMapping("/{id}/avg-grade-for-discipline")
    public List<String> avgGradeForDiscipline(@PathVariable Long id){
        return gradeService.findAvgGradeForDiscipline(id);
    }

    @GetMapping("/{id}/avg-grade-for-teacher-by-discipline")
    public List<String> avgGradeForTeacherByDiscipline(@PathVariable Long id){
        return gradeService.findAvgGradeForTeacherByDiscipline(id);
    }
}
