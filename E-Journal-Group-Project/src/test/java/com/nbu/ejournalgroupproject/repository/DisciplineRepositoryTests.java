package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DisciplineRepositoryTests {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCurriculumRepository studentCurriculumRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Discipline biology;
    private Discipline chemistry;
    private Discipline math;

    private DisciplineType biologyType;
    private DisciplineType chemistryType;
    private DisciplineType mathType;
    @Autowired
    private TeacherQualificationRepository teacherQualificationRepository;
    @Autowired
    private StudentCurriculumHasTeacherAndDisciplineRepository studentCurriculumHasTeacherAndDisciplineRepository;

    @BeforeEach
    public void setUp() {
        biologyType = new DisciplineType();
        biologyType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);

        chemistryType = new DisciplineType();
        chemistryType.setDisciplineTypeEnum(DisciplineTypeEnum.CHEMISTRY);

        mathType = new DisciplineType();
        mathType.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);

        biology = new Discipline();
        biology.setName("Biology");
        biology.setDisciplineType(biologyType);

        chemistry = new Discipline();
        chemistry.setName("Chemistry");
        chemistry.setDisciplineType(chemistryType);

        math = new Discipline();
        math.setName("Math");
        math.setDisciplineType(mathType);

        disciplineTypeRepository.save(biologyType);
        disciplineTypeRepository.save(chemistryType);
        disciplineTypeRepository.save(mathType);

        disciplineRepository.save(biology);
        disciplineRepository.save(chemistry);
        disciplineRepository.save(math);
    }

    @AfterEach
    public void tearDown() {
        disciplineRepository.deleteAll();
        disciplineTypeRepository.deleteAll();
        schoolClassRepository.deleteAll();
        studentRepository.deleteAll();
        studentCurriculumRepository.deleteAll();
        teacherQualificationRepository.deleteAll();
        schoolRepository.deleteAll();
        schoolTypeRepository.deleteAll();
    }

    @Test
    public void disciplineRepo_findAll_returnsManyDisciplines() {
        List<Discipline> disciplines = disciplineRepository.findAll();

        assertThat(disciplines).isNotNull();
        assertThat(disciplines.size()).isEqualTo(3);
        assertThat(disciplines).extracting(Discipline::getName)
                .containsExactlyInAnyOrder("Biology", "Chemistry", "Math");
    }

    @Test
    public void disciplineRepo_findById_returnsDiscipline() {
        Optional<Discipline> foundDiscipline = disciplineRepository.findById(biology.getId());

        assertThat(foundDiscipline).isPresent();
        assertThat(foundDiscipline.get().getName()).isEqualTo("Biology");
        assertThat(foundDiscipline.get().getDisciplineType()).isEqualTo(biologyType);
    }

    @Test
    public void disciplineRepo_findById_throwsEntityNotFoundException() {
        Long notFoundId = 100L;
        assertThrows(EntityNotFoundException.class, () ->
                disciplineRepository.findById(notFoundId)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

    @Test
    public void disciplineRepo_save_returnsSavedDiscipline() {
        Discipline history = new Discipline();
        DisciplineType historyType = new DisciplineType();
        historyType.setDisciplineTypeEnum(DisciplineTypeEnum.HISTORY);
        disciplineTypeRepository.save(historyType);

        history.setName("History");
        history.setDisciplineType(historyType);

        Discipline savedDiscipline = disciplineRepository.save(history);

        assertThat(savedDiscipline).isNotNull();
        assertThat(savedDiscipline.getId()).isNotNull();
        assertThat(savedDiscipline.getName()).isEqualTo("History");
        assertThat(savedDiscipline.getDisciplineType()).isEqualTo(historyType);
    }

    @Test
    public void disciplineRepo_update_returnsUpdatedDiscipline() {
        biology.setName("Advanced Biology");
        Discipline updatedDiscipline = disciplineRepository.save(biology);

        assertThat(updatedDiscipline).isNotNull();
        assertThat(updatedDiscipline.getId()).isNotNull();
        assertThat(updatedDiscipline.getName()).isEqualTo("Advanced Biology");
        assertThat(updatedDiscipline.getDisciplineType()).isEqualTo(biologyType);
    }

    @Test
    public void disciplineRepo_delete_returnsDeletedDiscipline() {
        disciplineRepository.delete(biology);
        Optional<Discipline> deletedDiscipline = disciplineRepository.findById(biology.getId());

        assertThat(deletedDiscipline).isNotPresent();
    }

    @Test
    public void disciplineRepo_findDisciplinesByStudentId_returnsDisciplines() {

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

        Student student = new Student();
        student.setName("Teddy");
        student.setNumberInClass(21);
        student.setSchoolClass(schoolClass);
        studentRepository.save(student);

        StudentCurriculum curriculum = new StudentCurriculum();
        curriculum.setYear(12);
        curriculum.setSemester(2);
        curriculum.setSchoolClass(schoolClass);
        studentCurriculumRepository.save(curriculum);

        TeacherQualification teacherQualification = new TeacherQualification();
        teacherQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);
        // Setting the m:n relationship
        Set<Discipline> disciplinesSet = new HashSet<>();
        disciplinesSet.add(biology);
        teacherQualification.setDisciplines(disciplinesSet);
        teacherQualificationRepository.save(teacherQualification);

        Teacher teacher = new Teacher();
        teacher.setName("Teddy");
        teacher.setEmail("teddyeqka@example.com");
        teacher.setTeacherQualifications(Set.of(teacherQualification));
        teacherRepository.save(teacher);

        StudentCurriculumHasTeacherAndDiscipline program1 = new StudentCurriculumHasTeacherAndDiscipline();
        program1.setTeacher(teacher);
        program1.setDiscipline(biology);
        program1.setStudentCurriculum(curriculum);
        studentCurriculumHasTeacherAndDisciplineRepository.save(program1);

        // Call the repository method
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByStudentId(student.getId());

        assertThat(disciplines).isNotNull();
        assertThat(disciplines.size()).isGreaterThan(0);
        assertThat(disciplines).extracting(Discipline::getName)
                .contains("Biology");
    }
}
