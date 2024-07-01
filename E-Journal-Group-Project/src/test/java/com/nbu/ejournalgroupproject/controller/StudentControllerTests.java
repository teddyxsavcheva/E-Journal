package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.*;
import com.nbu.ejournalgroupproject.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private DisciplineService disciplineService;

    @MockBean
    private GradeService gradeService;

    @MockBean
    private AbsenceService absenceService;

    private StudentDTO studentDTO;
    private DisciplineDto disciplineDto;
    private GradeDTO gradeDTO;
    private AbsenceDTO absenceDTO;

    @BeforeEach
    void setUp() {
        studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setName("John Doe");
        studentDTO.setNumberInClass(1);
        studentDTO.setSchoolClassId(1L);

        disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Math");

        gradeDTO = new GradeDTO();
        gradeDTO.setId(1L);
        gradeDTO.setStudentId(1L);
        gradeDTO.setDisciplineId(1L);
        gradeDTO.setGradeTypeId(1L);

        absenceDTO = new AbsenceDTO();
        absenceDTO.setId(1L);
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(studentDTO));

        mockMvc.perform(get("/students/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(studentDTO);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testCreateStudent() throws Exception {
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(studentDTO);

        mockMvc.perform(post("/students/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(studentService, times(1)).createStudent(any(StudentDTO.class));
    }

    @Test
    void testCreateStudentValidationFailure() throws Exception {
        StudentDTO invalidStudentDTO = new StudentDTO();
        invalidStudentDTO.setId(1L);
        invalidStudentDTO.setName("");
        invalidStudentDTO.setNumberInClass(0);
        invalidStudentDTO.setSchoolClassId(null);

        mockMvc.perform(post("/students/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStudentDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name cannot be empty"))
                .andExpect(jsonPath("$.numberInClass").value("Number in class must be at least 1"))
                .andExpect(jsonPath("$.schoolClassId").value("School class cannot be null"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    void testUpdateStudent() throws Exception {
        when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(studentDTO);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(studentService, times(1)).updateStudent(eq(1L), any(StudentDTO.class));
    }

    @Test
    void testAddCaregiverToStudent() throws Exception {
        when(studentService.addCaregiverToStudent(1L, 1L)).thenReturn(studentDTO);

        mockMvc.perform(post("/students/1/caregivers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(studentService, times(1)).addCaregiverToStudent(1L, 1L);
    }

    @Test
    void testRemoveCaregiverFromStudent() throws Exception {
        when(studentService.removeCaregiverFromStudent(1L, 1L)).thenReturn(studentDTO);

        mockMvc.perform(delete("/students/1/caregivers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(studentService, times(1)).removeCaregiverFromStudent(1L, 1L);
    }

    @Test
    void testGetStudentDisciplines() throws Exception {
        when(disciplineService.getDisciplinesByStudentId(1L)).thenReturn(Collections.singletonList(disciplineDto));

        mockMvc.perform(get("/students/1/disciplines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Math"));

        verify(disciplineService, times(1)).getDisciplinesByStudentId(1L);
    }

    @Test
    void testGetStudentGrades() throws Exception {
        when(gradeService.getGradesByStudentAndDiscipline(1L, 1L)).thenReturn(Collections.singletonList("A"));

        mockMvc.perform(get("/students/1/disciplines/1/grades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("A"));

        verify(gradeService, times(1)).getGradesByStudentAndDiscipline(1L, 1L);
    }

    @Test
    void testGetStudentAbsences() throws Exception {
        when(absenceService.getAbsencesByStudentAndDiscipline(1L, 1L)).thenReturn(Collections.singletonList(1L));

        mockMvc.perform(get("/students/1/disciplines/1/absences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(1L));

        verify(absenceService, times(1)).getAbsencesByStudentAndDiscipline(1L, 1L);
    }

    @Test
    void testGetStudentByClassId() throws Exception {
        when(studentService.getStudentsFromClass(1L)).thenReturn(Collections.singletonList(studentDTO));

        mockMvc.perform(get("/students/school-class/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(studentService, times(1)).getStudentsFromClass(1L);
    }

    @Test
    void testGetStudentGradesByDisciplineId() throws Exception {
        when(gradeService.getGradeObjectsByStudentAndDiscipline(1L, 1L)).thenReturn(Collections.singletonList(gradeDTO));

        mockMvc.perform(get("/students/1/discipline/1/grades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].studentId").value(1L))
                .andExpect(jsonPath("$[0].disciplineId").value(1L))
                .andExpect(jsonPath("$[0].gradeTypeId").value(1L));

        verify(gradeService, times(1)).getGradeObjectsByStudentAndDiscipline(1L, 1L);
    }

    @Test
    void testGetStudentAbsencesByDisciplineId() throws Exception {
        when(absenceService.getAbsenceObjectsByStudentAndDiscipline(1L, 1L)).thenReturn(Collections.singletonList(absenceDTO));

        mockMvc.perform(get("/students/1/discipline/1/absences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(absenceService, times(1)).getAbsenceObjectsByStudentAndDiscipline(1L, 1L);
    }
}