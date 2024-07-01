package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TeacherRepositoryTests {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    @Autowired
    private TeacherQualificationRepository teacherQualificationRepository;

    private SchoolType schoolType;
    private School school;
    private Teacher teacher1;
    private Teacher teacher2;
    private Discipline discipline;
    private DisciplineType disciplineType;
    private TeacherQualification qualification;

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

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Mathematics");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

        qualification = new TeacherQualification();
        qualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_ART_TEACHING);
        qualification.setDisciplines(Set.of(discipline));
        teacherQualificationRepository.save(qualification);

        teacher1 = new Teacher();
        teacher1.setName("Teacher 1");
        teacher1.setEmail("teacher1@example.com");
        teacher1.setSchool(school);
        teacher1.setTeacherQualifications(new HashSet<>(List.of(qualification)));
        teacherRepository.save(teacher1);

        teacher2 = new Teacher();
        teacher2.setName("Teacher 2");
        teacher2.setEmail("teacher2@example.com");
        teacher2.setSchool(school);
        teacherRepository.save(teacher2);
    }

    @Test
    public void teacherRepo_findAll_returnsManyTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        assertThat(teachers).isNotNull();
        assertThat(teachers.size()).isEqualTo(2);
        assertThat(teachers).extracting(Teacher::getName)
                .containsExactlyInAnyOrder("Teacher 1", "Teacher 2");
    }

    @Test
    public void teacherRepo_findById_returnsTeacher() {
        Optional<Teacher> foundTeacher = teacherRepository.findById(teacher1.getId());

        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getName()).isEqualTo("Teacher 1");
        assertThat(foundTeacher.get().getEmail()).isEqualTo("teacher1@example.com");
        assertThat(foundTeacher.get().getSchool()).isEqualTo(school);
    }

    @Test
    public void teacherRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentTeacherId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            teacherRepository.findById(nonExistentTeacherId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void teacherRepo_save_returnsSavedTeacher() {
        Teacher teacher3 = new Teacher();
        teacher3.setName("Teacher 3");
        teacher3.setEmail("teacher3@example.com");
        teacher3.setSchool(school);

        Teacher savedTeacher = teacherRepository.save(teacher3);

        assertThat(savedTeacher).isNotNull();
        assertThat(savedTeacher.getId()).isNotNull();
        assertThat(savedTeacher.getName()).isEqualTo("Teacher 3");
        assertThat(savedTeacher.getEmail()).isEqualTo("teacher3@example.com");
        assertThat(savedTeacher.getSchool()).isEqualTo(school);
    }

    @Test
    public void teacherRepo_update_returnsUpdatedTeacher() {
        teacher1.setName("Updated Teacher 1");
        teacher1.setEmail("updated_teacher1@example.com");
        Teacher updatedTeacher = teacherRepository.save(teacher1);

        assertThat(updatedTeacher).isNotNull();
        assertThat(updatedTeacher.getId()).isNotNull();
        assertThat(updatedTeacher.getName()).isEqualTo("Updated Teacher 1");
        assertThat(updatedTeacher.getEmail()).isEqualTo("updated_teacher1@example.com");
    }

    @Test
    public void teacherRepo_delete_returnsDeletedTeacher() {
        teacherRepository.delete(teacher1);
        Optional<Teacher> deletedTeacher = teacherRepository.findById(teacher1.getId());

        assertThat(deletedTeacher).isNotPresent();
    }

    @Test
    public void teacherRepo_isTeacherQualifiedForDiscipline_returnsTrue() {
        boolean isQualified = teacherRepository.isTeacherQualifiedForDiscipline(teacher1.getId(), discipline.getId());

        assertThat(isQualified).isTrue();
    }

    @Test
    public void teacherRepo_isTeacherQualifiedForDiscipline_returnsFalse() {
        boolean isQualified = teacherRepository.isTeacherQualifiedForDiscipline(teacher2.getId(), discipline.getId());

        assertThat(isQualified).isFalse();
    }

    @Test
    public void teacherRepo_findAllBySchoolId_returnsTeachers() {
        List<Teacher> teachers = teacherRepository.findAllBySchoolId(school.getId());

        assertThat(teachers).isNotNull();
        assertThat(teachers.size()).isEqualTo(2);
        assertThat(teachers).extracting(Teacher::getName)
                .containsExactlyInAnyOrder("Teacher 1", "Teacher 2");
    }
}