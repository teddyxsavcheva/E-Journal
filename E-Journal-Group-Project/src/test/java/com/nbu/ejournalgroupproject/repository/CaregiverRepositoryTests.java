package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.model.SchoolType;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
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
public class CaregiverRepositoryTests {

    @Autowired
    private CaregiverRepository caregiverRepository;

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

        student1 = new Student();
        student1.setName("Student1");
        student1.setNumberInClass(1);
        student1.setSchoolClass(schoolClass);
        studentRepository.save(student1);

        student2 = new Student();
        student2.setName("Student2");
        student2.setNumberInClass(2);
        student2.setSchoolClass(schoolClass);
        studentRepository.save(student2);

        caregiver1 = new Caregiver();
        caregiver1.setName("Caregiver1");
        caregiver1.setEmail("caregiver1@example.com");
        caregiver1.setStudents(new HashSet<>(List.of(student1, student2)));
        caregiverRepository.save(caregiver1);

        caregiver2 = new Caregiver();
        caregiver2.setName("Caregiver2");
        caregiver2.setEmail("caregiver2@example.com");
        caregiverRepository.save(caregiver2);
    }

    @Test
    public void caregiverRepo_findAll_returnsManyCaregivers() {
        List<Caregiver> caregivers = caregiverRepository.findAll();

        assertThat(caregivers).isNotNull();
        assertThat(caregivers.size()).isEqualTo(2);
        assertThat(caregivers).extracting(Caregiver::getName)
                .containsExactlyInAnyOrder("Caregiver1", "Caregiver2");
    }

    @Test
    public void caregiverRepo_findById_returnsCaregiver() {
        Optional<Caregiver> foundCaregiver = caregiverRepository.findById(caregiver1.getId());

        assertThat(foundCaregiver).isPresent();
        assertThat(foundCaregiver.get().getName()).isEqualTo("Caregiver1");
        assertThat(foundCaregiver.get().getEmail()).isEqualTo("caregiver1@example.com");
        assertThat(foundCaregiver.get().getStudents()).containsExactlyInAnyOrder(student1, student2);
    }

    @Test
    public void caregiverRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentCaregiverId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            caregiverRepository.findById(nonExistentCaregiverId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void caregiverRepo_save_returnsSavedCaregiver() {
        Caregiver caregiver3 = new Caregiver();
        caregiver3.setName("Caregiver3");
        caregiver3.setEmail("caregiver3@example.com");

        Caregiver savedCaregiver = caregiverRepository.save(caregiver3);

        assertThat(savedCaregiver).isNotNull();
        assertThat(savedCaregiver.getId()).isNotNull();
        assertThat(savedCaregiver.getName()).isEqualTo("Caregiver3");
        assertThat(savedCaregiver.getEmail()).isEqualTo("caregiver3@example.com");
    }

    @Test
    public void caregiverRepo_update_returnsUpdatedCaregiver() {
        caregiver1.setName("Advanced Caregiver1");
        caregiver1.setEmail("advanced_caregiver1@example.com");
        Caregiver updatedCaregiver = caregiverRepository.save(caregiver1);

        assertThat(updatedCaregiver).isNotNull();
        assertThat(updatedCaregiver.getId()).isNotNull();
        assertThat(updatedCaregiver.getName()).isEqualTo("Advanced Caregiver1");
        assertThat(updatedCaregiver.getEmail()).isEqualTo("advanced_caregiver1@example.com");
    }

    @Test
    public void caregiverRepo_delete_returnsDeletedCaregiver() {
        caregiverRepository.delete(caregiver1);
        Optional<Caregiver> deletedCaregiver = caregiverRepository.findById(caregiver1.getId());

        assertThat(deletedCaregiver).isNotPresent();
    }
}