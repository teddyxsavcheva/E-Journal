package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import com.nbu.ejournalgroupproject.model.AbsenceType;
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
public class AbsenceTypeRepositoryTests {

    @Autowired
    private AbsenceTypeRepository absenceTypeRepository;

    private AbsenceType absenceType1;
    private AbsenceType absenceType2;

    @BeforeEach
    public void setUp() {
        absenceType1 = new AbsenceType();
        absenceType1.setAbsenceTypeEnum(AbsenceTypeEnum.SICK_LEAVE);
        absenceTypeRepository.save(absenceType1);

        absenceType2 = new AbsenceType();
        absenceType2.setAbsenceTypeEnum(AbsenceTypeEnum.EVENT_PARTICIPATION);
        absenceTypeRepository.save(absenceType2);
    }

    @Test
    public void absenceTypeRepo_findAll_returnsManyAbsenceTypes() {
        List<AbsenceType> absenceTypes = absenceTypeRepository.findAll();

        assertThat(absenceTypes).isNotNull();
        assertThat(absenceTypes.size()).isEqualTo(2);
        assertThat(absenceTypes).extracting(AbsenceType::getAbsenceTypeEnum)
                .containsExactlyInAnyOrder(AbsenceTypeEnum.SICK_LEAVE, AbsenceTypeEnum.EVENT_PARTICIPATION);
    }

    @Test
    public void absenceTypeRepo_findById_returnsAbsenceType() {
        Optional<AbsenceType> foundAbsenceType = absenceTypeRepository.findById(absenceType1.getId());

        assertThat(foundAbsenceType).isPresent();
        assertThat(foundAbsenceType.get().getAbsenceTypeEnum()).isEqualTo(AbsenceTypeEnum.SICK_LEAVE);
    }

    @Test
    public void absenceTypeRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentAbsenceTypeId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            absenceTypeRepository.findById(nonExistentAbsenceTypeId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void absenceTypeRepo_save_returnsSavedAbsenceType() {
        AbsenceType absenceType3 = new AbsenceType();
        absenceType3.setAbsenceTypeEnum(AbsenceTypeEnum.PARENT_EXCUSE);

        AbsenceType savedAbsenceType = absenceTypeRepository.save(absenceType3);

        assertThat(savedAbsenceType).isNotNull();
        assertThat(savedAbsenceType.getId()).isNotNull();
        assertThat(savedAbsenceType.getAbsenceTypeEnum()).isEqualTo(AbsenceTypeEnum.PARENT_EXCUSE);
    }

    @Test
    public void absenceTypeRepo_update_returnsUpdatedAbsenceType() {
        absenceType1.setAbsenceTypeEnum(AbsenceTypeEnum.PARENT_EXCUSE);
        AbsenceType updatedAbsenceType = absenceTypeRepository.save(absenceType1);

        assertThat(updatedAbsenceType).isNotNull();
        assertThat(updatedAbsenceType.getId()).isNotNull();
        assertThat(updatedAbsenceType.getAbsenceTypeEnum()).isEqualTo(AbsenceTypeEnum.PARENT_EXCUSE);
    }

    @Test
    public void absenceTypeRepo_delete_returnsDeletedAbsenceType() {
        absenceTypeRepository.delete(absenceType1);
        Optional<AbsenceType> deletedAbsenceType = absenceTypeRepository.findById(absenceType1.getId());

        assertThat(deletedAbsenceType).isNotPresent();
    }
}