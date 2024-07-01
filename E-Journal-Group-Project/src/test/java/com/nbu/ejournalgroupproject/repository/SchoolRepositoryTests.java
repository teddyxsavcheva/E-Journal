package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.SchoolType;
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
public class SchoolRepositoryTests {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    private SchoolType schoolType;
    private School school1;
    private School school2;

    @BeforeEach
    public void setUp() {
        schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);
        schoolTypeRepository.save(schoolType);

        school1 = new School();
        school1.setName("Test School 1");
        school1.setAddress("123 Test Street 1");
        school1.setSchoolType(schoolType);
        schoolRepository.save(school1);

        school2 = new School();
        school2.setName("Test School 2");
        school2.setAddress("123 Test Street 2");
        school2.setSchoolType(schoolType);
        schoolRepository.save(school2);
    }

    @Test
    public void schoolRepo_findAll_returnsManySchools() {
        List<School> schools = schoolRepository.findAll();

        assertThat(schools).isNotNull();
        assertThat(schools.size()).isEqualTo(2);
        assertThat(schools).extracting(School::getName)
                .containsExactlyInAnyOrder("Test School 1", "Test School 2");
    }

    @Test
    public void schoolRepo_findById_returnsSchool() {
        Optional<School> foundSchool = schoolRepository.findById(school1.getId());

        assertThat(foundSchool).isPresent();
        assertThat(foundSchool.get().getName()).isEqualTo("Test School 1");
        assertThat(foundSchool.get().getAddress()).isEqualTo("123 Test Street 1");
        assertThat(foundSchool.get().getSchoolType()).isEqualTo(schoolType);
    }

    @Test
    public void schoolRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentSchoolId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            schoolRepository.findById(nonExistentSchoolId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void schoolRepo_save_returnsSavedSchool() {
        School school3 = new School();
        school3.setName("Test School 3");
        school3.setAddress("123 Test Street 3");
        school3.setSchoolType(schoolType);

        School savedSchool = schoolRepository.save(school3);

        assertThat(savedSchool).isNotNull();
        assertThat(savedSchool.getId()).isNotNull();
        assertThat(savedSchool.getName()).isEqualTo("Test School 3");
        assertThat(savedSchool.getAddress()).isEqualTo("123 Test Street 3");
        assertThat(savedSchool.getSchoolType()).isEqualTo(schoolType);
    }

    @Test
    public void schoolRepo_update_returnsUpdatedSchool() {
        school1.setName("Updated Test School 1");
        school1.setAddress("456 Updated Test Street 1");
        School updatedSchool = schoolRepository.save(school1);

        assertThat(updatedSchool).isNotNull();
        assertThat(updatedSchool.getId()).isNotNull();
        assertThat(updatedSchool.getName()).isEqualTo("Updated Test School 1");
        assertThat(updatedSchool.getAddress()).isEqualTo("456 Updated Test Street 1");
    }

    @Test
    public void schoolRepo_delete_returnsDeletedSchool() {
        schoolRepository.delete(school1);
        Optional<School> deletedSchool = schoolRepository.findById(school1.getId());

        assertThat(deletedSchool).isNotPresent();
    }
}