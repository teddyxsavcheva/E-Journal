package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
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
public class SchoolTypeRepositoryTests {

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    private SchoolType schoolType1;
    private SchoolType schoolType2;

    @BeforeEach
    public void setUp() {
        schoolType1 = new SchoolType();
        schoolType1.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);
        schoolTypeRepository.save(schoolType1);

        schoolType2 = new SchoolType();
        schoolType2.setSchoolTypeEnum(SchoolTypeEnum.HIGH_SCHOOL);
        schoolTypeRepository.save(schoolType2);
    }

    @Test
    public void schoolTypeRepo_findAll_returnsManySchoolTypes() {
        List<SchoolType> schoolTypes = schoolTypeRepository.findAll();

        assertThat(schoolTypes).isNotNull();
        assertThat(schoolTypes.size()).isEqualTo(2);
        assertThat(schoolTypes).extracting(SchoolType::getSchoolTypeEnum)
                .containsExactlyInAnyOrder(SchoolTypeEnum.PRIMARY_SCHOOL, SchoolTypeEnum.HIGH_SCHOOL);
    }

    @Test
    public void schoolTypeRepo_findById_returnsSchoolType() {
        Optional<SchoolType> foundSchoolType = schoolTypeRepository.findById(schoolType1.getId());

        assertThat(foundSchoolType).isPresent();
        assertThat(foundSchoolType.get().getSchoolTypeEnum()).isEqualTo(SchoolTypeEnum.PRIMARY_SCHOOL);
    }

    @Test
    public void schoolTypeRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentSchoolTypeId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            schoolTypeRepository.findById(nonExistentSchoolTypeId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void schoolTypeRepo_save_returnsSavedSchoolType() {
        SchoolType schoolType3 = new SchoolType();
        schoolType3.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);

        SchoolType savedSchoolType = schoolTypeRepository.save(schoolType3);

        assertThat(savedSchoolType).isNotNull();
        assertThat(savedSchoolType.getId()).isNotNull();
        assertThat(savedSchoolType.getSchoolTypeEnum()).isEqualTo(SchoolTypeEnum.PRIMARY_SCHOOL);
    }

    @Test
    public void schoolTypeRepo_update_returnsUpdatedSchoolType() {
        schoolType1.setSchoolTypeEnum(SchoolTypeEnum.HIGH_SCHOOL);
        SchoolType updatedSchoolType = schoolTypeRepository.save(schoolType1);

        assertThat(updatedSchoolType).isNotNull();
        assertThat(updatedSchoolType.getId()).isNotNull();
        assertThat(updatedSchoolType.getSchoolTypeEnum()).isEqualTo(SchoolTypeEnum.HIGH_SCHOOL);
    }

    @Test
    public void schoolTypeRepo_delete_returnsDeletedSchoolType() {
        schoolTypeRepository.delete(schoolType1);
        Optional<SchoolType> deletedSchoolType = schoolTypeRepository.findById(schoolType1.getId());

        assertThat(deletedSchoolType).isNotPresent();
    }
}