package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
// In-memory db so we don't use the real one and damage it
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DisciplineTypeRepositoryTests {

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    private DisciplineType biology;
    private DisciplineType chemistry;
    private DisciplineType math;

    @BeforeEach
    public void setUp() {
        biology = new DisciplineType();
        biology.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);

        chemistry = new DisciplineType();
        chemistry.setDisciplineTypeEnum(DisciplineTypeEnum.CHEMISTRY);

        math = new DisciplineType();
        math.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);

        disciplineTypeRepository.save(biology);
        disciplineTypeRepository.save(chemistry);
        disciplineTypeRepository.save(math);
    }

    @Test
    public void disciplineTypeRepo_findAll_returnsManyDisciplineTypes() {
        List<DisciplineType> disciplineTypes = disciplineTypeRepository.findAll();

        assertThat(disciplineTypes).isNotNull();
        assertThat(disciplineTypes.size()).isEqualTo(3);
        assertThat(disciplineTypes).extracting(DisciplineType::getDisciplineTypeEnum)
                .containsExactlyInAnyOrder(DisciplineTypeEnum.BIOLOGY, DisciplineTypeEnum.CHEMISTRY, DisciplineTypeEnum.MATH);
    }

    @Test
    public void disciplineTypeRepo_findById_returnsDisciplineType() {
        Optional<DisciplineType> foundDisciplineType = disciplineTypeRepository.findById(biology.getId());

        assertThat(foundDisciplineType).isPresent();
        assertThat(foundDisciplineType.get().getDisciplineTypeEnum()).isEqualTo(DisciplineTypeEnum.BIOLOGY);
    }

    @Test
    public void disciplineTypeRepo_save_returnsSavedDisciplineType() {
        DisciplineType history = new DisciplineType();
        history.setDisciplineTypeEnum(DisciplineTypeEnum.HISTORY);

        DisciplineType savedDisciplineType = disciplineTypeRepository.save(history);

        assertThat(savedDisciplineType).isNotNull();
        assertThat(savedDisciplineType.getId()).isNotNull();
        assertThat(savedDisciplineType.getDisciplineTypeEnum()).isEqualTo(DisciplineTypeEnum.HISTORY);
    }

    @Test
    public void disciplineTypeRepo_update_returnsUpdatedDisciplineType() {
        biology.setDisciplineTypeEnum(DisciplineTypeEnum.PHYSICS);
        DisciplineType updatedDisciplineType = disciplineTypeRepository.save(biology);
        
        assertThat(updatedDisciplineType).isNotNull();
        assertThat(updatedDisciplineType.getId()).isNotNull();
        assertThat(updatedDisciplineType.getDisciplineTypeEnum()).isEqualTo(DisciplineTypeEnum.PHYSICS);
    }

    @Test
    public void disciplineTypeRepo_delete_returnsDeletedDisciplineType() {
        disciplineTypeRepository.delete(biology);
        Optional<DisciplineType> deletedDisciplineType = disciplineTypeRepository.findById(biology.getId());

        assertThat(deletedDisciplineType).isNotPresent();

    }

}
