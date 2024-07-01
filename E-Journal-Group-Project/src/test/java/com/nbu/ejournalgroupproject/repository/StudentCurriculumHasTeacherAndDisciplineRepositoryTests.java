package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@EntityScan(basePackages = "com.nbu.ejournalgroupproject.model")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudentCurriculumHasTeacherAndDisciplineRepositoryTests {

    @Autowired
    private StudentCurriculumHasTeacherAndDisciplineRepository repository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private StudentCurriculumRepository studentCurriculumRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    private StudentCurriculumHasTeacherAndDiscipline program1;
    private StudentCurriculumHasTeacherAndDiscipline program2;
    private StudentCurriculumHasTeacherAndDiscipline program3;
    private Teacher teacher;
    private Discipline discipline;
    private DisciplineType disciplineType;
    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass;
    private StudentCurriculum curriculum;

    @BeforeEach
    public void setUp() {
        teacher = new Teacher();
        teacher.setName("Teddy");
        teacher.setEmail("teddyeqka@example.com");
        teacherRepository.save(teacher);

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Biology");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

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

        program2 = new StudentCurriculumHasTeacherAndDiscipline();
        program2.setTeacher(teacher);
        program2.setDiscipline(discipline);
        program2.setStudentCurriculum(curriculum);
        repository.save(program2);

        program3 = new StudentCurriculumHasTeacherAndDiscipline();
        program3.setTeacher(teacher);
        program3.setDiscipline(discipline);
        program3.setStudentCurriculum(curriculum);
        repository.save(program3);
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
    public void repository_findAll_returnsManyEntities() {
        List<StudentCurriculumHasTeacherAndDiscipline> entities = repository.findAll();

        assertThat(entities).isNotNull();
        assertThat(entities.size()).isEqualTo(3);
    }

    @Test
    public void repository_findById_returnsEntity() {
        Optional<StudentCurriculumHasTeacherAndDiscipline> foundEntity = repository.findById(program1.getId());

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getId()).isEqualTo(program1.getId());
    }

    @Test
    public void repository_findById_notFoundThrowsException() {
        Long nonExistentId = 999L;

        assertThrows(EntityNotFoundException.class, () -> {
            Optional<StudentCurriculumHasTeacherAndDiscipline> foundEntity = repository.findById(nonExistentId);
            if (foundEntity.isEmpty()) {
                throw new EntityNotFoundException("No entity found with id " + nonExistentId);
            }
        });
    }

    @Test
    public void repository_save_returnsSavedEntity() {
        Teacher teacher = new Teacher();
        teacher.setName("Teddy");
        teacher.setEmail("teddyeqka@example.com");
        teacherRepository.save(teacher);

        DisciplineType disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);
        disciplineTypeRepository.save(disciplineType);

        Discipline discipline = new Discipline();
        discipline.setName("Biology");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

        SchoolType schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.HIGH_SCHOOL);
        schoolTypeRepository.save(schoolType);

        School school = new School();
        school.setName("PMG");
        school.setAddress("Gabrovo");
        school.setSchoolType(schoolType);
        schoolRepository.save(school);

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setYear(2021);
        schoolClass.setSchool(school);
        schoolClass.setName("Gclass");
        schoolClassRepository.save(schoolClass);

        StudentCurriculum curriculum = new StudentCurriculum();
        curriculum.setYear(12);
        curriculum.setSemester(2);
        curriculum.setSchoolClass(schoolClass);
        studentCurriculumRepository.save(curriculum);

        StudentCurriculumHasTeacherAndDiscipline newEntity = new StudentCurriculumHasTeacherAndDiscipline();
        newEntity.setTeacher(teacher);
        newEntity.setDiscipline(discipline);
        newEntity.setStudentCurriculum(curriculum);
        repository.save(newEntity);

        StudentCurriculumHasTeacherAndDiscipline savedEntity = repository.save(newEntity);

        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
    }

    @Test
    public void repository_update_returnsUpdatedEntity() {
        Teacher newTeacher = new Teacher();
        newTeacher.setName("Goshko");
        newTeacher.setEmail("goshoeqk@example.com");
        teacherRepository.save(newTeacher);

        program1.setTeacher(newTeacher);
        StudentCurriculumHasTeacherAndDiscipline updatedEntity = repository.save(program1);

        assertThat(updatedEntity).isNotNull();
        assertThat(updatedEntity.getTeacher().getId()).isEqualTo(newTeacher.getId());
        assertThat(updatedEntity.getTeacher().getName()).isEqualTo(newTeacher.getName());
    }

    @Test
    public void repository_delete_returnsDeletedEntity() {
        repository.delete(program1);
        Optional<StudentCurriculumHasTeacherAndDiscipline> deletedEntity = repository.findById(program1.getId());

        assertThat(deletedEntity).isNotPresent();
    }

    @Test
    public void repository_findAllByStudentCurriculumId_returnsAllEntities() {
        List<StudentCurriculumHasTeacherAndDiscipline> foundEntities = repository.findAllByStudentCurriculumId(curriculum.getId());

        assertThat(foundEntities).isNotNull();
        assertThat(foundEntities.size()).isEqualTo(3);
    }

}
