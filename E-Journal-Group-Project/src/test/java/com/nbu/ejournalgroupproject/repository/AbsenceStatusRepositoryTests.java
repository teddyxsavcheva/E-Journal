package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import com.nbu.ejournalgroupproject.model.AbsenceStatus;
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
public class AbsenceStatusRepositoryTests {

    @Autowired
    private AbsenceStatusRepository absenceStatusRepository;

    private AbsenceStatus absenceStatus1;
    private AbsenceStatus absenceStatus2;

    @BeforeEach
    public void setUp() {
        absenceStatus1 = new AbsenceStatus();
        absenceStatus1.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);
        absenceStatusRepository.save(absenceStatus1);

        absenceStatus2 = new AbsenceStatus();
        absenceStatus2.setAbsenceStatusEnum(AbsenceStatusEnum.NOT_EXCUSED);
        absenceStatusRepository.save(absenceStatus2);
    }

    @Test
    public void absenceStatusRepo_findAll_returnsManyAbsenceStatuses() {
        List<AbsenceStatus> absenceStatuses = absenceStatusRepository.findAll();

        assertThat(absenceStatuses).isNotNull();
        assertThat(absenceStatuses.size()).isEqualTo(2);
        assertThat(absenceStatuses).extracting(AbsenceStatus::getAbsenceStatusEnum)
                .containsExactlyInAnyOrder(AbsenceStatusEnum.EXCUSED, AbsenceStatusEnum.NOT_EXCUSED);
    }

    @Test
    public void absenceStatusRepo_findById_returnsAbsenceStatus() {
        Optional<AbsenceStatus> foundAbsenceStatus = absenceStatusRepository.findById(absenceStatus1.getId());

        assertThat(foundAbsenceStatus).isPresent();
        assertThat(foundAbsenceStatus.get().getAbsenceStatusEnum()).isEqualTo(AbsenceStatusEnum.EXCUSED);
    }

    @Test
    public void absenceStatusRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentAbsenceStatusId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            absenceStatusRepository.findById(nonExistentAbsenceStatusId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void absenceStatusRepo_save_returnsSavedAbsenceStatus() {
        AbsenceStatus absenceStatus3 = new AbsenceStatus();
        absenceStatus3.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);

        AbsenceStatus savedAbsenceStatus = absenceStatusRepository.save(absenceStatus3);

        assertThat(savedAbsenceStatus).isNotNull();
        assertThat(savedAbsenceStatus.getId()).isNotNull();
        assertThat(savedAbsenceStatus.getAbsenceStatusEnum()).isEqualTo(AbsenceStatusEnum.EXCUSED);
    }

    @Test
    public void absenceStatusRepo_update_returnsUpdatedAbsenceStatus() {
        absenceStatus1.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);
        AbsenceStatus updatedAbsenceStatus = absenceStatusRepository.save(absenceStatus1);

        assertThat(updatedAbsenceStatus).isNotNull();
        assertThat(updatedAbsenceStatus.getId()).isNotNull();
        assertThat(updatedAbsenceStatus.getAbsenceStatusEnum()).isEqualTo(AbsenceStatusEnum.EXCUSED);
    }

    @Test
    public void absenceStatusRepo_delete_returnsDeletedAbsenceStatus() {
        absenceStatusRepository.delete(absenceStatus1);
        Optional<AbsenceStatus> deletedAbsenceStatus = absenceStatusRepository.findById(absenceStatus1.getId());

        assertThat(deletedAbsenceStatus).isNotPresent();
    }
}