package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GradeRepositoryTests {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradeTypeRepository gradeTypeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    @Autowired
    private HeadmasterRepository headmasterRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentCurriculumRepository studentCurriculumRepository;

    @Autowired
    private StudentCurriculumHasTeacherAndDisciplineRepository studentCurriculumHasTeacherAndDisciplineRepository;


    private GradeType gradeType;
    private GradeType gradeType2;
    private Discipline discipline;
    private DisciplineType disciplineType;
    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass;
    private Student student;
    private Grade grade;
    private Grade grade2;
    private Headmaster headmaster;
    private Teacher teacher;
    private StudentCurriculum studentCurriculum;
    private StudentCurriculumHasTeacherAndDiscipline studentCurriculumHasTeacherAndDiscipline;

    @BeforeEach
    public void setUp() {
        gradeType = new GradeType();
        gradeType.setGradeTypeEnum(GradeTypeEnum.A);
        gradeTypeRepository.save(gradeType);

        gradeType2 = new GradeType();
        gradeType2.setGradeTypeEnum(GradeTypeEnum.B);
        gradeTypeRepository.save(gradeType2);

        schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);
        schoolTypeRepository.save(schoolType);

        school = new School();
        school.setName("Test School");
        school.setAddress("123 Test Street");
        school.setSchoolType(schoolType);
        schoolRepository.save(school);

        schoolClass = new SchoolClass();
        schoolClass.setName("Test School Class");
        schoolClass.setYear(2023);
        schoolClass.setSchool(school);
        schoolClassRepository.save(schoolClass);

        student = new Student();
        student.setName("Student1");
        student.setNumberInClass(1);
        student.setSchoolClass(schoolClass);
        studentRepository.save(student);

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Mathematics");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

        grade = new Grade();
        grade.setDateOfIssue(LocalDate.now());
        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGradeType(gradeType);
        gradeRepository.save(grade);

        grade2 = new Grade();
        grade2.setDateOfIssue(LocalDate.now().minusDays(1));
        grade2.setStudent(student);
        grade2.setDiscipline(discipline);
        grade2.setGradeType(gradeType2);
        gradeRepository.save(grade2);

        headmaster = new Headmaster();
        headmaster.setName("Headmaster1");
        headmaster.setEmail("headmaster@example.com");
        headmaster.setSchool(school);
        headmasterRepository.save(headmaster);

        teacher = new Teacher();
        teacher.setName("Teacher1");
        teacher.setEmail("teacher1@example.com");
        teacher.setSchool(school);
        teacherRepository.save(teacher);

        studentCurriculum = new StudentCurriculum();
        studentCurriculum.setYear(10);
        studentCurriculum.setSchoolClass(schoolClass);
        studentCurriculum.setSemester(1);
        studentCurriculumRepository.save(studentCurriculum);

        studentCurriculumHasTeacherAndDiscipline = new StudentCurriculumHasTeacherAndDiscipline();
        studentCurriculumHasTeacherAndDiscipline.setDiscipline(discipline);
        studentCurriculumHasTeacherAndDiscipline.setTeacher(teacher);
        studentCurriculumHasTeacherAndDiscipline.setStudentCurriculum(studentCurriculum);
        studentCurriculumHasTeacherAndDisciplineRepository.save(studentCurriculumHasTeacherAndDiscipline);
    }

    @Test
    public void gradeRepo_findAll_returnsManyGrades() {
        List<Grade> grades = gradeRepository.findAll();

        assertThat(grades).isNotNull();
        assertThat(grades.size()).isEqualTo(2);
        assertThat(grades).extracting(Grade::getGradeType)
                .containsExactlyInAnyOrder(gradeType, gradeType2);
    }

    @Test
    public void gradeRepo_findById_returnsGrade() {
        Optional<Grade> foundGrade = gradeRepository.findById(grade.getId());

        assertThat(foundGrade).isPresent();
        assertThat(foundGrade.get().getDateOfIssue()).isEqualTo(grade.getDateOfIssue());
        assertThat(foundGrade.get().getStudent()).isEqualTo(student);
        assertThat(foundGrade.get().getDiscipline()).isEqualTo(discipline);
        assertThat(foundGrade.get().getGradeType()).isEqualTo(gradeType);
    }

    @Test
    public void gradeRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentGradeId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            gradeRepository.findById(nonExistentGradeId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void gradeRepo_save_returnsSavedGrade() {
        Grade grade3 = new Grade();
        grade3.setDateOfIssue(LocalDate.now());
        grade3.setStudent(student);
        grade3.setDiscipline(discipline);
        grade3.setGradeType(gradeType);

        Grade savedGrade = gradeRepository.save(grade3);

        assertThat(savedGrade).isNotNull();
        assertThat(savedGrade.getId()).isNotNull();
        assertThat(savedGrade.getDateOfIssue()).isEqualTo(LocalDate.now());
        assertThat(savedGrade.getStudent()).isEqualTo(student);
        assertThat(savedGrade.getDiscipline()).isEqualTo(discipline);
        assertThat(savedGrade.getGradeType()).isEqualTo(gradeType);
    }

    @Test
    public void gradeRepo_update_returnsUpdatedGrade() {
        grade.setDateOfIssue(LocalDate.now().minusDays(2));
        Grade updatedGrade = gradeRepository.save(grade);

        assertThat(updatedGrade).isNotNull();
        assertThat(updatedGrade.getId()).isNotNull();
        assertThat(updatedGrade.getDateOfIssue()).isEqualTo(LocalDate.now().minusDays(2));
    }

    @Test
    public void gradeRepo_delete_returnsDeletedGrade() {
        gradeRepository.delete(grade);
        Optional<Grade> deletedGrade = gradeRepository.findById(grade.getId());

        assertThat(deletedGrade).isNotPresent();
    }

    @Test
    public void gradeRepo_findGradeTypesByStudentAndDiscipline_returnsCorrectResult() {
        List<String> gradeTypes = gradeRepository.findGradeTypesByStudentAndDiscipline(student.getId(), discipline.getId());

        assertThat(gradeTypes).isNotNull();
        assertThat(gradeTypes.size()).isEqualTo(2);
        assertThat(gradeTypes).containsExactlyInAnyOrder(GradeTypeEnum.A.name(), GradeTypeEnum.B.name());
    }

    @Test
    public void gradeRepo_findAvgGradeForTeacherByDiscipline_returnsCorrectResult() {
        List<String> avgGrades = gradeRepository.findAvgGradeForTeacherByDiscipline(headmaster.getId());
        assertThat(avgGrades).isNotNull();
        assertThat(avgGrades.size()).isGreaterThan(0);
    }

    @Test
    public void gradeRepo_findAvgGradeForDiscipline_returnsCorrectResult() {
        List<String> avgGrades = gradeRepository.findAvgGradeForDiscipline(headmaster.getId());
        assertThat(avgGrades).isNotNull();
        assertThat(avgGrades.size()).isGreaterThan(0);
    }

    @Test
    public void gradeRepo_findAvgGradeForSchool_returnsCorrectResult() {
        List<String> avgGrades = gradeRepository.findAvgGradeForSchool(headmaster.getId());
        assertThat(avgGrades).isNotNull();
        assertThat(avgGrades.size()).isGreaterThan(0);
    }

}