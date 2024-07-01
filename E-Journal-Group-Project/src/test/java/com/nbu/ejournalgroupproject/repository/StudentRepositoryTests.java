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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass;
    private Student student1;
    private Student student2;

    private Caregiver caregiver1;
    private Caregiver caregiver2;
    @Autowired
    private CaregiverRepository caregiverRepository;

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

        schoolClass = new SchoolClass();
        schoolClass.setName("Test School Class");
        schoolClass.setYear(2023);
        schoolClass.setSchool(school);
        schoolClassRepository.save(schoolClass);

        caregiver1 = new Caregiver();
        caregiver1.setName("Caregiver1");
        caregiver1.setEmail("caregiver1@example.com");
        caregiverRepository.save(caregiver1);

        caregiver2 = new Caregiver();
        caregiver2.setName("Caregiver2");
        caregiver2.setEmail("caregiver2@example.com");
        caregiverRepository.save(caregiver2);

        student1 = new Student();
        student1.setName("Student1");
        student1.setNumberInClass(1);
        student1.setSchoolClass(schoolClass);
        student1.setCaregivers(new HashSet<>(List.of(caregiver1)));

        student2 = new Student();
        student2.setName("Student2");
        student2.setNumberInClass(2);
        student2.setSchoolClass(schoolClass);
        student2.setCaregivers(new HashSet<>(List.of(caregiver1, caregiver2)));

        studentRepository.save(student1);
        studentRepository.save(student2);
    }

    @Test
    public void studentRepo_findAll_returnsManyStudents() {
        List<Student> students = studentRepository.findAll();

        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
        assertThat(students).extracting(Student::getName)
                .containsExactlyInAnyOrder("Student1", "Student2");
    }

    @Test
    public void studentRepo_findById_returnsStudent() {
        Optional<Student> foundStudent = studentRepository.findById(student1.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getName()).isEqualTo("Student1");
        assertThat(foundStudent.get().getNumberInClass()).isEqualTo(1);
        assertThat(foundStudent.get().getSchoolClass()).isEqualTo(schoolClass);
        assertThat(foundStudent.get().getCaregivers()).containsExactly(caregiver1);
    }

    @Test
    public void studentRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentStudentId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            studentRepository.findById(nonExistentStudentId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void studentRepo_save_returnsSavedStudent() {
        Student student3 = new Student();
        student3.setName("Student3");
        student3.setNumberInClass(3);
        student3.setSchoolClass(schoolClass);

        Student savedStudent = studentRepository.save(student3);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getName()).isEqualTo("Student3");
        assertThat(savedStudent.getNumberInClass()).isEqualTo(3);
        assertThat(savedStudent.getSchoolClass()).isEqualTo(schoolClass);
    }

    @Test
    public void studentRepo_update_returnsUpdatedStudent() {
        student1.setName("Advanced Student1");
        student1.setNumberInClass(10);
        Student updatedStudent = studentRepository.save(student1);

        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getId()).isNotNull();
        assertThat(updatedStudent.getName()).isEqualTo("Advanced Student1");
        assertThat(updatedStudent.getNumberInClass()).isEqualTo(10);
    }

    @Test
    public void studentRepo_delete_returnsDeletedStudent() {
        studentRepository.delete(student1);
        Optional<Student> deletedStudent = studentRepository.findById(student1.getId());

        assertThat(deletedStudent).isNotPresent();
    }

    @Test
    public void studentRepo_hasLessThanTwoCaregivers_returnsCorrectResult() {
        boolean student1HasLessThanTwoCaregivers = studentRepository.hasLessThenTwoCaregivers(student1.getId());
        boolean student2HasLessThanTwoCaregivers = studentRepository.hasLessThenTwoCaregivers(student2.getId());

        assertThat(student1HasLessThanTwoCaregivers).isTrue();
        assertThat(student2HasLessThanTwoCaregivers).isFalse();
    }
}