package com.nbu.ejournalgroupproject.repository;

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
public class StudentCurriculumRepositoryTests {

    @Autowired
    private StudentCurriculumRepository studentCurriculumRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass1;
    private StudentCurriculum studentCurriculum;

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

        studentCurriculum = new StudentCurriculum();
        studentCurriculum.setSchoolClass(schoolClass1);
        studentCurriculum.setSemester(1);
        studentCurriculum.setYear(1);
        studentCurriculumRepository.save(studentCurriculum);
    }

    @Test
    public void studentCurriculumRepo_findAll_returnsManyStudentCurriculums() {
        List<StudentCurriculum> studentCurriculums = studentCurriculumRepository.findAll();

        assertThat(studentCurriculums).isNotNull();
        assertThat(studentCurriculums.size()).isEqualTo(1);
        assertThat(studentCurriculums).extracting(StudentCurriculum::getSemester)
                .containsExactlyInAnyOrder(1);
    }

    @Test
    public void studentCurriculumRepo_findById_returnsStudentCurriculum() {
        Optional<StudentCurriculum> foundStudentCurriculum = studentCurriculumRepository.findById(studentCurriculum.getId());

        assertThat(foundStudentCurriculum).isPresent();
        assertThat(foundStudentCurriculum.get().getSemester()).isEqualTo(1);
        assertThat(foundStudentCurriculum.get().getYear()).isEqualTo(1);
        assertThat(foundStudentCurriculum.get().getSchoolClass()).isEqualTo(schoolClass1);
    }

    @Test
    public void studentCurriculumRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentStudentCurriculumId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            studentCurriculumRepository.findById(nonExistentStudentCurriculumId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void studentCurriculumRepo_save_returnsSavedStudentCurriculum() {
        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setName("Class 2");
        schoolClass2.setYear(2023);
        schoolClass2.setSchool(school);
        schoolClassRepository.save(schoolClass2);

        StudentCurriculum studentCurriculum2 = new StudentCurriculum();
        studentCurriculum2.setSchoolClass(schoolClass2);
        studentCurriculum2.setSemester(2);
        studentCurriculum2.setYear(2);

        StudentCurriculum savedStudentCurriculum = studentCurriculumRepository.save(studentCurriculum2);

        assertThat(savedStudentCurriculum).isNotNull();
        assertThat(savedStudentCurriculum.getId()).isNotNull();
        assertThat(savedStudentCurriculum.getSemester()).isEqualTo(2);
        assertThat(savedStudentCurriculum.getYear()).isEqualTo(2);
        assertThat(savedStudentCurriculum.getSchoolClass()).isEqualTo(schoolClass2);
    }

    @Test
    public void studentCurriculumRepo_update_returnsUpdatedStudentCurriculum() {
        studentCurriculum.setSemester(2);
        studentCurriculum.setYear(2);
        StudentCurriculum updatedStudentCurriculum = studentCurriculumRepository.save(studentCurriculum);

        assertThat(updatedStudentCurriculum).isNotNull();
        assertThat(updatedStudentCurriculum.getId()).isNotNull();
        assertThat(updatedStudentCurriculum.getSemester()).isEqualTo(2);
        assertThat(updatedStudentCurriculum.getYear()).isEqualTo(2);
    }

    @Test
    public void studentCurriculumRepo_delete_returnsDeletedStudentCurriculum() {
        studentCurriculumRepository.delete(studentCurriculum);
        Optional<StudentCurriculum> deletedStudentCurriculum = studentCurriculumRepository.findById(studentCurriculum.getId());

        assertThat(deletedStudentCurriculum).isNotPresent();
    }

    @Test
    public void studentCurriculumRepo_getStudentCurriculumBySchoolClassId_returnsStudentCurriculum() {
        StudentCurriculum foundStudentCurriculum = studentCurriculumRepository.getStudentCurriculumBySchoolClassId(schoolClass1.getId());

        assertThat(foundStudentCurriculum).isNotNull();
        assertThat(foundStudentCurriculum.getSemester()).isEqualTo(1);
        assertThat(foundStudentCurriculum.getYear()).isEqualTo(1);
        assertThat(foundStudentCurriculum.getSchoolClass()).isEqualTo(schoolClass1);
    }
}