package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.model.Headmaster;
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
public class HeadmasterRepositoryTests {

    @Autowired
    private HeadmasterRepository headmasterRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    private SchoolType schoolType;
    private School school;
    private School school2;
    private Headmaster headmaster1;
    private Headmaster headmaster2;

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

        school2 = new School();
        school2.setName("Test School2");
        school2.setAddress("123 Test Street2");
        school2.setSchoolType(schoolType);
        schoolRepository.save(school2);

        headmaster1 = new Headmaster();
        headmaster1.setName("Headmaster 1");
        headmaster1.setEmail("headmaster1@example.com");
        headmaster1.setSchool(school);
        headmasterRepository.save(headmaster1);

        headmaster2 = new Headmaster();
        headmaster2.setName("Headmaster 2");
        headmaster2.setEmail("headmaster2@example.com");
        headmaster2.setSchool(school2);
        headmasterRepository.save(headmaster2);
    }

    @Test
    public void headmasterRepo_findAll_returnsManyHeadmasters() {
        List<Headmaster> headmasters = headmasterRepository.findAll();

        assertThat(headmasters).isNotNull();
        assertThat(headmasters.size()).isEqualTo(2);
        assertThat(headmasters).extracting(Headmaster::getName)
                .containsExactlyInAnyOrder("Headmaster 1", "Headmaster 2");
    }

    @Test
    public void headmasterRepo_findById_returnsHeadmaster() {
        Optional<Headmaster> foundHeadmaster = headmasterRepository.findById(headmaster1.getId());

        assertThat(foundHeadmaster).isPresent();
        assertThat(foundHeadmaster.get().getName()).isEqualTo("Headmaster 1");
        assertThat(foundHeadmaster.get().getEmail()).isEqualTo("headmaster1@example.com");
        assertThat(foundHeadmaster.get().getSchool()).isEqualTo(school);
    }

    @Test
    public void headmasterRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentHeadmasterId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            headmasterRepository.findById(nonExistentHeadmasterId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void headmasterRepo_save_returnsSavedHeadmaster() {
        School newSchool = new School();
        newSchool.setName("New School");
        newSchool.setAddress("456 New Street");
        newSchool.setSchoolType(schoolType);
        schoolRepository.save(newSchool);

        Headmaster headmaster3 = new Headmaster();
        headmaster3.setName("Headmaster 3");
        headmaster3.setEmail("headmaster3@example.com");
        headmaster3.setSchool(newSchool);

        Headmaster savedHeadmaster = headmasterRepository.save(headmaster3);

        assertThat(savedHeadmaster).isNotNull();
        assertThat(savedHeadmaster.getId()).isNotNull();
        assertThat(savedHeadmaster.getName()).isEqualTo("Headmaster 3");
        assertThat(savedHeadmaster.getEmail()).isEqualTo("headmaster3@example.com");
        assertThat(savedHeadmaster.getSchool()).isEqualTo(newSchool);
    }

    @Test
    public void headmasterRepo_update_returnsUpdatedHeadmaster() {
        headmaster1.setName("Updated Headmaster 1");
        headmaster1.setEmail("updated_headmaster1@example.com");
        Headmaster updatedHeadmaster = headmasterRepository.save(headmaster1);

        assertThat(updatedHeadmaster).isNotNull();
        assertThat(updatedHeadmaster.getId()).isNotNull();
        assertThat(updatedHeadmaster.getName()).isEqualTo("Updated Headmaster 1");
        assertThat(updatedHeadmaster.getEmail()).isEqualTo("updated_headmaster1@example.com");
    }

    @Test
    public void headmasterRepo_delete_returnsDeletedHeadmaster() {
        headmasterRepository.delete(headmaster1);
        Optional<Headmaster> deletedHeadmaster = headmasterRepository.findById(headmaster1.getId());

        assertThat(deletedHeadmaster).isNotPresent();
    }

    @Test
    public void headmasterRepo_findBySchoolId_returnsCorrectHeadmaster() {
        Optional<Headmaster> foundHeadmaster = Optional.ofNullable(headmasterRepository.findBySchoolId(school.getId()));

        assertThat(foundHeadmaster).isPresent();
        assertThat(foundHeadmaster.get().getName()).isEqualTo("Headmaster 1");
        assertThat(foundHeadmaster.get().getEmail()).isEqualTo("headmaster1@example.com");
        assertThat(foundHeadmaster.get().getSchool()).isEqualTo(school);
    }
}