package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DisciplineRepositoryTests {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    private Discipline biology;
    private Discipline chemistry;
    private Discipline math;

    private DisciplineType biologyType;
    private DisciplineType chemistryType;
    private DisciplineType mathType;

    @BeforeEach
    public void setUp() {
        biologyType = new DisciplineType();
        biologyType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);

        chemistryType = new DisciplineType();
        chemistryType.setDisciplineTypeEnum(DisciplineTypeEnum.CHEMISTRY);

        mathType = new DisciplineType();
        mathType.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);

        biology = new Discipline();
        biology.setName("Biology");
        biology.setDisciplineType(biologyType);

        chemistry = new Discipline();
        chemistry.setName("Chemistry");
        chemistry.setDisciplineType(chemistryType);

        math = new Discipline();
        math.setName("Math");
        math.setDisciplineType(mathType);

        disciplineTypeRepository.save(biologyType);
        disciplineTypeRepository.save(chemistryType);
        disciplineTypeRepository.save(mathType);

        disciplineRepository.save(biology);
        disciplineRepository.save(chemistry);
        disciplineRepository.save(math);
    }

    @AfterEach
    public void tearDown() {
        disciplineRepository.deleteAll();
    }

    @Test
    public void disciplineRepo_findAll_returnsManyDisciplines() {
        List<Discipline> disciplines = disciplineRepository.findAll();

        assertThat(disciplines).isNotNull();
        assertThat(disciplines.size()).isEqualTo(3);
        assertThat(disciplines).extracting(Discipline::getName)
                .containsExactlyInAnyOrder("Biology", "Chemistry", "Math");
    }

    @Test
    public void disciplineRepo_findById_returnsDiscipline() {
        Optional<Discipline> foundDiscipline = disciplineRepository.findById(biology.getId());

        assertThat(foundDiscipline).isPresent();
        assertThat(foundDiscipline.get().getName()).isEqualTo("Biology");
        assertThat(foundDiscipline.get().getDisciplineType()).isEqualTo(biologyType);
    }

    @Test
    public void disciplineRepo_findById_throwsEntityNotFoundException() {
        Long notFoundId = 100L;
        assertThrows(EntityNotFoundException.class, () ->
                disciplineRepository.findById(notFoundId)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

    @Test
    public void disciplineRepo_save_returnsSavedDiscipline() {
        Discipline history = new Discipline();
        DisciplineType historyType = new DisciplineType();
        historyType.setDisciplineTypeEnum(DisciplineTypeEnum.HISTORY);
        disciplineTypeRepository.save(historyType);

        history.setName("History");
        history.setDisciplineType(historyType);

        Discipline savedDiscipline = disciplineRepository.save(history);

        assertThat(savedDiscipline).isNotNull();
        assertThat(savedDiscipline.getId()).isNotNull();
        assertThat(savedDiscipline.getName()).isEqualTo("History");
        assertThat(savedDiscipline.getDisciplineType()).isEqualTo(historyType);
    }

    @Test
    public void disciplineRepo_update_returnsUpdatedDiscipline() {
        biology.setName("Advanced Biology");
        Discipline updatedDiscipline = disciplineRepository.save(biology);

        assertThat(updatedDiscipline).isNotNull();
        assertThat(updatedDiscipline.getId()).isNotNull();
        assertThat(updatedDiscipline.getName()).isEqualTo("Advanced Biology");
        assertThat(updatedDiscipline.getDisciplineType()).isEqualTo(biologyType);
    }

    @Test
    public void disciplineRepo_delete_returnsDeletedDiscipline() {
        disciplineRepository.delete(biology);
        Optional<Discipline> deletedDiscipline = disciplineRepository.findById(biology.getId());

        assertThat(deletedDiscipline).isNotPresent();
    }
}
