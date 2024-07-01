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
        teacherQualificationRepository.deleteAll();
        disciplineRepository.deleteAll();
        disciplineTypeRepository.deleteAll();
        studentCurriculumRepository.deleteAll();
    }

    @Test
    void service_getAllCurriculumHasTeacherAndDiscipline_returnsList() {
        List<StudentCurriculumHasTeacherAndDiscipline> programs = Arrays.asList(program1);
        when(repository.findAll()).thenReturn(programs);
        when(mapper.convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(new StudentCurriculumHasTeacherAndDisciplineDto());

        List<StudentCurriculumHasTeacherAndDisciplineDto> result = service.getAllCurriculumHasTeacherAndDiscipline();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class));
    }

    @Test
    void service_getCurriculumHasTeacherAndDisciplineById_returnsDto() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(program1));
        when(mapper.convertToDto(program1)).thenReturn(dto);

        StudentCurriculumHasTeacherAndDisciplineDto result = service.getCurriculumHasTeacherAndDisciplineById(id);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).convertToDto(program1);
    }

    @Test
    void service_getCurriculumHasTeacherAndDisciplineById_throwsException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getCurriculumHasTeacherAndDisciplineById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void service_createCurriculumHasTeacherAndDiscipline_returnsDto() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.isTeacherQualifiedForDiscipline(1L, 1L)).thenReturn(true);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        when(repository.save(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(program1);
        when(mapper.convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(dto);

        StudentCurriculumHasTeacherAndDisciplineDto result = service.createCurriculumHasTeacherAndDiscipline(dto);

        assertNotNull(result);
        verify(mapper, times(1)).convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class));
    }

    @Test
    void service_createCurriculumHasTeacherAndDiscipline_teacherNotQualified() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.isTeacherQualifiedForDiscipline(1L, 1L)).thenReturn(false);
        lenient().when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));

        assertThrows(IllegalArgumentException.class, () -> service.createCurriculumHasTeacherAndDiscipline(dto));

    }

    @Test
    void service_updateCurriculumHasTeacherAndDiscipline_returnsDto() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(program1));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.isTeacherQualifiedForDiscipline(1L, 1L)).thenReturn(true);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        when(repository.save(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(program1);
        when(mapper.convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class))).thenReturn(dto);

        StudentCurriculumHasTeacherAndDisciplineDto result = service.updateCurriculumHasTeacherAndDiscipline(dto, id);

        assertNotNull(result);
        verify(mapper, times(1)).convertToDto(any(StudentCurriculumHasTeacherAndDiscipline.class));
    }

    @Test
    void service_updateCurriculumHasTeacherAndDiscipline_throwsException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateCurriculumHasTeacherAndDiscipline(dto, id));
    }

    @Test
    void service_deleteCurriculumHasTeacherAndDiscipline_returnsVoid() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(program1));

        service.deleteCurriculumHasTeacherAndDiscipline(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void service_getAllCurriculumTeacherDisciplineByStudCurr_returnsDtoList() {
        Long curriculumId = 1L;

        StudentCurriculumHasTeacherAndDiscipline sctd1 = new StudentCurriculumHasTeacherAndDiscipline();
        StudentCurriculumHasTeacherAndDiscipline sctd2 = new StudentCurriculumHasTeacherAndDiscipline();
        when(repository.findAllByStudentCurriculumId(curriculumId)).thenReturn(Arrays.asList(sctd1, sctd2));

        StudentCurriculumHasTeacherAndDisciplineDto dto1 = new StudentCurriculumHasTeacherAndDisciplineDto();
        StudentCurriculumHasTeacherAndDisciplineDto dto2 = new StudentCurriculumHasTeacherAndDisciplineDto();
        when(mapper.convertToDto(sctd1)).thenReturn(dto1);
        when(mapper.convertToDto(sctd2)).thenReturn(dto2);

        List<StudentCurriculumHasTeacherAndDisciplineDto> result = service.getAllCurriculumTeacherDisciplineByStudCurr(curriculumId);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        verify(repository, times(1)).findAllByStudentCurriculumId(curriculumId);
        verify(mapper, times(1)).convertToDto(sctd1);
        verify(mapper, times(1)).convertToDto(sctd2);
    }

    @Test
    void service_getAllCurriculumTeacherDisciplineByStudCurr_returnsEmptyList() {
        Long curriculumId = 1L;

        when(repository.findAllByStudentCurriculumId(curriculumId)).thenReturn(Collections.emptyList());

        List<StudentCurriculumHasTeacherAndDisciplineDto> result = service.getAllCurriculumTeacherDisciplineByStudCurr(curriculumId);

        assertEquals(0, result.size());
        verify(repository, times(1)).findAllByStudentCurriculumId(curriculumId);
        verifyNoInteractions(mapper);
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsValidDto() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(1L);
        dto.setTeacherId(1L);
        dto.setCurriculumId(1L);

        assertDoesNotThrow(() -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsNullDisciplineId() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(null);
        dto.setTeacherId(1L);
        dto.setCurriculumId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
        String expectedMessage = "The discipline id cannot be null or zero.";
        String actualMessage = exception.getMessage();
        assert actualMessage.contains(expectedMessage);
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsZeroDisciplineId() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(0L);
        dto.setTeacherId(1L);
        dto.setCurriculumId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
        String expectedMessage = "The discipline id cannot be null or zero.";
        String actualMessage = exception.getMessage();
        assert actualMessage.contains(expectedMessage);
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsNullTeacherId() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(1L);
        dto.setTeacherId(null);
        dto.setCurriculumId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
        String expectedMessage = "The teacher id cannot be null or zero.";
        String actualMessage = exception.getMessage();
        assert actualMessage.contains(expectedMessage);
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsZeroTeacherId() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(1L);
        dto.setTeacherId(0L);
        dto.setCurriculumId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
        String expectedMessage = "The teacher id cannot be null or zero.";
        String actualMessage = exception.getMessage();
        assert actualMessage.contains(expectedMessage);
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsNullCurriculumId() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(1L);
        dto.setTeacherId(1L);
        dto.setCurriculumId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
        String expectedMessage = "The curriculum id cannot be null or zero.";
        String actualMessage = exception.getMessage();
        assert actualMessage.contains(expectedMessage);
    }

    @Test
    void service_validateCurriculumHasTeacherAndDisciplineDto_returnsZeroCurriculumId() {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();
        dto.setDisciplineId(1L);
        dto.setTeacherId(1L);
        dto.setCurriculumId(0L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.validateCurriculumHasTeacherAndDisciplineDto(dto));
        String expectedMessage = "The curriculum id cannot be null or zero.";
        String actualMessage = exception.getMessage();
        assert actualMessage.contains(expectedMessage);
    }
}
