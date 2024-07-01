package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import com.nbu.ejournalgroupproject.model.GradeType;
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
public class GradeTypeRepositoryTests {

    @Autowired
    private GradeTypeRepository gradeTypeRepository;

    private GradeType gradeType1;
    private GradeType gradeType2;

    @BeforeEach
    public void setUp() {
        gradeType1 = new GradeType();
        gradeType1.setGradeTypeEnum(GradeTypeEnum.A);
        gradeTypeRepository.save(gradeType1);

        gradeType2 = new GradeType();
        gradeType2.setGradeTypeEnum(GradeTypeEnum.B);
        gradeTypeRepository.save(gradeType2);
    }

    @Test
    public void gradeTypeRepo_findAll_returnsManyGradeTypes() {
        List<GradeType> gradeTypes = gradeTypeRepository.findAll();

        assertThat(gradeTypes).isNotNull();
        assertThat(gradeTypes.size()).isEqualTo(2);
        assertThat(gradeTypes).extracting(GradeType::getGradeTypeEnum)
                .containsExactlyInAnyOrder(GradeTypeEnum.A, GradeTypeEnum.B);
    }

    @Test
    public void gradeTypeRepo_findById_returnsGradeType() {
        Optional<GradeType> foundGradeType = gradeTypeRepository.findById(gradeType1.getId());

        assertThat(foundGradeType).isPresent();
        assertThat(foundGradeType.get().getGradeTypeEnum()).isEqualTo(GradeTypeEnum.A);
    }

    @Test
    public void gradeTypeRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentGradeTypeId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            gradeTypeRepository.findById(nonExistentGradeTypeId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void gradeTypeRepo_save_returnsSavedGradeType() {
        GradeType gradeType3 = new GradeType();
        gradeType3.setGradeTypeEnum(GradeTypeEnum.C);

        GradeType savedGradeType = gradeTypeRepository.save(gradeType3);

        assertThat(savedGradeType).isNotNull();
        assertThat(savedGradeType.getId()).isNotNull();
        assertThat(savedGradeType.getGradeTypeEnum()).isEqualTo(GradeTypeEnum.C);
    }

    @Test
    public void gradeTypeRepo_update_returnsUpdatedGradeType() {
        gradeType1.setGradeTypeEnum(GradeTypeEnum.D);
        GradeType updatedGradeType = gradeTypeRepository.save(gradeType1);

        assertThat(updatedGradeType).isNotNull();
        assertThat(updatedGradeType.getId()).isNotNull();
        assertThat(updatedGradeType.getGradeTypeEnum()).isEqualTo(GradeTypeEnum.D);
    }

    @Test
    public void gradeTypeRepo_delete_returnsDeletedGradeType() {
        gradeTypeRepository.delete(gradeType1);
        Optional<GradeType> deletedGradeType = gradeTypeRepository.findById(gradeType1.getId());

        assertThat(deletedGradeType).isNotPresent();
    }
}