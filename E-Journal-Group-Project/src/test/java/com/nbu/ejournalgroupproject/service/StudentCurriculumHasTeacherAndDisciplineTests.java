package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumHasTeacherAndDisciplineDto;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.mappers.CurriculumHasTeacherAndDisciplineMapper;
import com.nbu.ejournalgroupproject.model.*;
import com.nbu.ejournalgroupproject.repository.*;
import com.nbu.ejournalgroupproject.service.serviceImpl.StudentCurriculumHasTeacherAndDisciplineServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentCurriculumHasTeacherAndDisciplineTests {

    @Mock
    StudentCurriculumRepository studentCurriculumRepository;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    TeacherQualificationRepository teacherQualificationRepository;

    @Mock
    DisciplineRepository disciplineRepository;

    @Mock
    DisciplineTypeRepository disciplineTypeRepository;

    @Mock
    SchoolTypeRepository schoolTypeRepository;

    @Mock
    SchoolRepository schoolRepository;

    @Mock
    SchoolClassRepository schoolClassRepository;

    @Mock
    StudentCurriculumHasTeacherAndDisciplineRepository repository;

    @Mock
    CurriculumHasTeacherAndDisciplineMapper mapper;

    @InjectMocks
    StudentCurriculumHasTeacherAndDisciplineServiceImpl service;

    private StudentCurriculumHasTeacherAndDiscipline program1;
    private StudentCurriculumHasTeacherAndDiscipline program2;
    private StudentCurriculumHasTeacherAndDiscipline program3;
    private Teacher teacher;
    private TeacherQualification teacherQualification;
    private Discipline discipline;
    private DisciplineType disciplineType;
    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass;
    private StudentCurriculum curriculum;
    private StudentCurriculumHasTeacherAndDisciplineDto dto;

    @BeforeEach
    void setUp() {

        schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.HIGH_SCHOOL);
        schoolTypeRepository.save(schoolType);

        school = new School();
        school.setName("PMG");
        school.setAddress("Gabrovo");
        school.setSchoolType(schoolType);
        schoolRepository.save(school);

        schoolClass = new SchoolClass();
        schoolClass.setYear(2021);
        schoolClass.setSchool(school);
        schoolClass.setName("Gclass");
        schoolClassRepository.save(schoolClass);

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Biology");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

        teacherQualification = new TeacherQualification();
        teacherQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);
        Set<Discipline> disciplines = new HashSet<>();
        disciplines.add(discipline);
        teacherQualification.setDisciplines(disciplines);
        teacherQualificationRepository.save(teacherQualification);

        teacher = new Teacher();
        teacher.setName("Teddy");
        teacher.setEmail("teddyeqka@example.com");
        teacher.setSchool(school);
        Set<TeacherQualification> teacherQualifications = new HashSet<>();
        teacherQualifications.add(teacherQualification);
        teacher.setTeacherQualifications(teacherQualifications);
        teacherRepository.save(teacher);

        curriculum = new StudentCurriculum();
        curriculum.setYear(12);
        curriculum.setSemester(2);
        curriculum.setSchoolClass(schoolClass);
        studentCurriculumRepository.save(curriculum);

        program1 = new StudentCurriculumHasTeacherAndDiscipline();
        program1.setTeacher(teacher);
        program1.setDiscipline(discipline);
        program1.setStudentCurriculum(curriculum);
        repository.save(program1);

        dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setTeacherId(1L);
        dto.setDisciplineId(1L);
        dto.setCurriculumId(1L);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        schoolClassRepository.deleteAll();
        schoolTypeRepository.deleteAll();
        schoolRepository.deleteAll();
        teacherRepository.deleteAll();
        disciplineRepository.deleteAll();
        disciplineTypeRepository.deleteAll();
        studentCurriculumRepository.deleteAll();
    }

    @Test
    void service_getAllCurriculumHasTeacherAndDiscipline_returnsList() {
        // Given
        List<StudentCurriculumHasTeacherAndDiscipline> programs = Arrays.asList(program1);
        when(repository.findAll()).thenReturn(programs);
        when(mapper.convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(new StudentCurriculumHasTeacherAndDisciplineDto());

        // When
        List<StudentCurriculumHasTeacherAndDisciplineDto> result = service.getAllCurriculumHasTeacherAndDiscipline();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class));
    }

    @Test
    void service_getCurriculumHasTeacherAndDisciplineById_returnsDto() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(program1));
        when(mapper.convertToDto(program1)).thenReturn(dto);

        // When
        StudentCurriculumHasTeacherAndDisciplineDto result = service.getCurriculumHasTeacherAndDisciplineById(id);

        // Then
        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).convertToDto(program1);
    }

    @Test
    void service_getCurriculumHasTeacherAndDisciplineById_throwsException() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> service.getCurriculumHasTeacherAndDisciplineById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void service_createCurriculumHasTeacherAndDiscipline_returnsDto() {
        // Given
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        when(repository.save(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(program1);
        when(mapper.convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(dto);

        // When
        StudentCurriculumHasTeacherAndDisciplineDto result = service.createCurriculumHasTeacherAndDiscipline(dto);

        // Then
        assertNotNull(result);
        verify(repository, times(1)).save(any(StudentCurriculumHasTeacherAndDiscipline.class));
        verify(mapper, times(1)).convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class));
    }

    @Test
    void service_createCurriculumHasTeacherAndDiscipline_teacherNotQualified() {
        // Given
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        when(teacherRepository.isTeacherQualifiedForDiscipline(1L, 1L)).thenReturn(false);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> service.createCurriculumHasTeacherAndDiscipline(dto));
    }

    @Test
    void service_updateCurriculumHasTeacherAndDiscipline_returnsDto() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(program1));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        when(repository.save(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(program1);
        when(mapper.convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(dto);

        // When
        StudentCurriculumHasTeacherAndDisciplineDto result = service.updateCurriculumHasTeacherAndDiscipline(dto, id);

        // Then
        assertNotNull(result);
        verify(repository, times(1)).save(any(StudentCurriculumHasTeacherAndDiscipline.class));
        verify(mapper, times(1)).convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class));
    }

    @Test
    void service_updateCurriculumHasTeacherAndDiscipline_throwsException() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> service.updateCurriculumHasTeacherAndDiscipline(dto, id));
    }

    @Test
    void service_deleteCurriculumHasTeacherAndDiscipline() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(program1));

        // When
        service.deleteCurriculumHasTeacherAndDiscipline(id);


        // Then
        verify(repository, times(1)).deleteById(id);
    }
}
