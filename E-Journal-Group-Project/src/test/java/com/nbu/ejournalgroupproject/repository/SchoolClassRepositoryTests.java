package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SchoolClassRepositoryTests {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentCurriculumRepository studentCurriculumRepository;

    @Autowired
    private StudentCurriculumHasTeacherAndDisciplineRepository scurrHasTeacherDisciplineRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass1;
    private SchoolClass schoolClass2;
    private Discipline discipline;
    private DisciplineType disciplineType;
    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);
        schoolTypeRepository.save(schoolType);

        school = new School();
        school.setName("Test School");
        school.setAddress("123 Test Street");
        school.setSchoolType(schoolType);
        schoolRepository.save(school);

        schoolClass1 = new SchoolClass();
        schoolClass1.setName("Class 1");
        schoolClass1.setYear(2023);
        schoolClass1.setSchool(school);
        schoolClassRepository.save(schoolClass1);

        schoolClass2 = new SchoolClass();
        schoolClass2.setName("Class 2");
        schoolClass2.setYear(2023);
        schoolClass2.setSchool(school);
        schoolClassRepository.save(schoolClass2);

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Test Discipline");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

        teacher = new Teacher();
        teacher.setName("Teacher 1");
        teacher.setEmail("teacher1@example.com");
        teacher.setSchool(school);
        teacherRepository.save(teacher);

        StudentCurriculum studentCurriculum = new StudentCurriculum();
        studentCurriculum.setSemester(1);
        studentCurriculum.setYear(10);
        studentCurriculum.setSchoolClass(schoolClass1);
        studentCurriculumRepository.save(studentCurriculum);

        StudentCurriculumHasTeacherAndDiscipline scurrHasTeacherDiscipline = new StudentCurriculumHasTeacherAndDiscipline();
        scurrHasTeacherDiscipline.setDiscipline(discipline);
        scurrHasTeacherDiscipline.setStudentCurriculum(studentCurriculum);
        scurrHasTeacherDiscipline.setTeacher(teacher);
        scurrHasTeacherDisciplineRepository.save(scurrHasTeacherDiscipline);
    }

    @Test
    public void schoolClassRepo_findAll_returnsManySchoolClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();

        assertThat(schoolClasses).isNotNull();
        assertThat(schoolClasses.size()).isEqualTo(2);
        assertThat(schoolClasses).extracting(SchoolClass::getName)
                .containsExactlyInAnyOrder("Class 1", "Class 2");
    }

    @Test
    public void schoolClassRepo_findById_returnsSchoolClass() {
        Optional<SchoolClass> foundSchoolClass = schoolClassRepository.findById(schoolClass1.getId());

        assertThat(foundSchoolClass).isPresent();
        assertThat(foundSchoolClass.get().getName()).isEqualTo("Class 1");
        assertThat(foundSchoolClass.get().getYear()).isEqualTo(2023);
        assertThat(foundSchoolClass.get().getSchool()).isEqualTo(school);
    }

    @Test
    public void schoolClassRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentSchoolClassId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            schoolClassRepository.findById(nonExistentSchoolClassId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void schoolClassRepo_save_returnsSavedSchoolClass() {
        SchoolClass schoolClass3 = new SchoolClass();
        schoolClass3.setName("Class 3");
        schoolClass3.setYear(2023);
        schoolClass3.setSchool(school);

        SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass3);

        assertThat(savedSchoolClass).isNotNull();
        assertThat(savedSchoolClass.getId()).isNotNull();
        assertThat(savedSchoolClass.getName()).isEqualTo("Class 3");
        assertThat(savedSchoolClass.getYear()).isEqualTo(2023);
        assertThat(savedSchoolClass.getSchool()).isEqualTo(school);
    }

    @Test
    public void schoolClassRepo_update_returnsUpdatedSchoolClass() {
        schoolClass1.setName("Updated Class 1");
        schoolClass1.setYear(2024);
        SchoolClass updatedSchoolClass = schoolClassRepository.save(schoolClass1);

        assertThat(updatedSchoolClass).isNotNull();
        assertThat(updatedSchoolClass.getId()).isNotNull();
        assertThat(updatedSchoolClass.getName()).isEqualTo("Updated Class 1");
        assertThat(updatedSchoolClass.getYear()).isEqualTo(2024);
    }

    @Test
    public void schoolClassRepo_delete_returnsDeletedSchoolClass() {
        schoolClassRepository.delete(schoolClass1);
        Optional<SchoolClass> deletedSchoolClass = schoolClassRepository.findById(schoolClass1.getId());

        assertThat(deletedSchoolClass).isNotPresent();
    }

    @Test
    public void schoolClassRepo_findAllBySchoolId_returnsSchoolClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAllBySchoolId(school.getId());

        assertThat(schoolClasses).isNotNull();
        assertThat(schoolClasses.size()).isEqualTo(2);
        assertThat(schoolClasses).extracting(SchoolClass::getName)
                .containsExactlyInAnyOrder("Class 1", "Class 2");
    }

    @Test
    public void schoolClassRepo_findDistinctClassesByTeacherId_returnsClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findDistinctClassesByTeacherId(teacher.getId());

        assertThat(schoolClasses).isNotNull();
        assertThat(schoolClasses.size()).isEqualTo(1);
        assertThat(schoolClasses).extracting(SchoolClass::getName)
                .containsExactlyInAnyOrder("Class 1");
    }
}