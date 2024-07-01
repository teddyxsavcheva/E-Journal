package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AbsenceRepositoryTests {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

    @Autowired
    private AbsenceTypeRepository absenceTypeRepository;

    @Autowired
    private AbsenceStatusRepository absenceStatusRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolTypeRepository schoolTypeRepository;

    private SchoolType schoolType;
    private School school;
    private SchoolClass schoolClass;
    private Student student;
    private Discipline discipline;
    private DisciplineType disciplineType;
    private AbsenceType absenceType;
    private AbsenceStatus absenceStatusJustified;
    private AbsenceStatus absenceStatusUnjustified;
    private Absence absence1;
    private Absence absence2;

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
        schoolClass.setName("Test Class");
        schoolClass.setYear(2023);
        schoolClass.setSchool(school);
        schoolClassRepository.save(schoolClass);

        student = new Student();
        student.setName("Test Student");
        student.setNumberInClass(1);
        student.setSchoolClass(schoolClass);
        studentRepository.save(student);

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.MATH);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Mathematics");
        discipline.setDisciplineType(disciplineType);
        disciplineRepository.save(discipline);

        absenceType = new AbsenceType();
        absenceType.setAbsenceTypeEnum(AbsenceTypeEnum.SICK_LEAVE);
        absenceTypeRepository.save(absenceType);

        absenceStatusJustified = new AbsenceStatus();
        absenceStatusJustified.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);
        absenceStatusRepository.save(absenceStatusJustified);

        absenceStatusUnjustified = new AbsenceStatus();
        absenceStatusUnjustified.setAbsenceStatusEnum(AbsenceStatusEnum.NOT_EXCUSED);
        absenceStatusRepository.save(absenceStatusUnjustified);

        absence1 = new Absence();
        absence1.setDateOfIssue(LocalDate.now());
        absence1.setStudent(student);
        absence1.setDiscipline(discipline);
        absence1.setAbsenceType(absenceType);
        absence1.setAbsenceStatus(absenceStatusJustified);
        absenceRepository.save(absence1);

        absence2 = new Absence();
        absence2.setDateOfIssue(LocalDate.now());
        absence2.setStudent(student);
        absence2.setDiscipline(discipline);
        absence2.setAbsenceType(absenceType);
        absence2.setAbsenceStatus(absenceStatusUnjustified);
        absenceRepository.save(absence2);
    }

    @Test
    public void absenceRepo_findAll_returnsManyAbsences() {
        List<Absence> absences = absenceRepository.findAll();

        assertThat(absences).isNotNull();
        assertThat(absences.size()).isEqualTo(2);
        assertThat(absences).extracting(Absence::getAbsenceStatus)
                .containsExactlyInAnyOrder(absenceStatusJustified, absenceStatusUnjustified);
    }

    @Test
    public void absenceRepo_findById_returnsAbsence() {
        Optional<Absence> foundAbsence = absenceRepository.findById(absence1.getId());

        assertThat(foundAbsence).isPresent();
        assertThat(foundAbsence.get().getDateOfIssue()).isEqualTo(absence1.getDateOfIssue());
        assertThat(foundAbsence.get().getStudent()).isEqualTo(student);
        assertThat(foundAbsence.get().getDiscipline()).isEqualTo(discipline);
        assertThat(foundAbsence.get().getAbsenceType()).isEqualTo(absenceType);
        assertThat(foundAbsence.get().getAbsenceStatus()).isEqualTo(absenceStatusJustified);
    }

    @Test
    public void absenceRepo_findById_throwsEntityNotFoundException() {
        Long nonExistentAbsenceId = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            absenceRepository.findById(nonExistentAbsenceId).orElseThrow(EntityNotFoundException::new);
        });
    }

    @Test
    public void absenceRepo_save_returnsSavedAbsence() {
        Absence absence3 = new Absence();
        absence3.setDateOfIssue(LocalDate.now());
        absence3.setStudent(student);
        absence3.setDiscipline(discipline);
        absence3.setAbsenceType(absenceType);
        absence3.setAbsenceStatus(absenceStatusJustified);

        Absence savedAbsence = absenceRepository.save(absence3);

        assertThat(savedAbsence).isNotNull();
        assertThat(savedAbsence.getId()).isNotNull();
        assertThat(savedAbsence.getDateOfIssue()).isEqualTo(LocalDate.now());
        assertThat(savedAbsence.getStudent()).isEqualTo(student);
        assertThat(savedAbsence.getDiscipline()).isEqualTo(discipline);
        assertThat(savedAbsence.getAbsenceType()).isEqualTo(absenceType);
        assertThat(savedAbsence.getAbsenceStatus()).isEqualTo(absenceStatusJustified);
    }

    @Test
    public void absenceRepo_update_returnsUpdatedAbsence() {
        absence1.setDateOfIssue(LocalDate.now().minusDays(2));
        Absence updatedAbsence = absenceRepository.save(absence1);

        assertThat(updatedAbsence).isNotNull();
        assertThat(updatedAbsence.getId()).isNotNull();
        assertThat(updatedAbsence.getDateOfIssue()).isEqualTo(LocalDate.now().minusDays(2));
    }

    @Test
    public void absenceRepo_delete_returnsDeletedAbsence() {
        absenceRepository.delete(absence1);
        Optional<Absence> deletedAbsence = absenceRepository.findById(absence1.getId());

        assertThat(deletedAbsence).isNotPresent();
    }

    @Test
    public void absenceRepo_excusedAbsencesCountByStudentAndDiscipline_returnsCount() {
        Long excusedCount = absenceRepository.excusedAbsencesCountByStudentAndDiscipline(student.getId(), discipline.getId());

        assertThat(excusedCount).isEqualTo(1);
    }

    @Test
    public void absenceRepo_notExcusedAbsencesCountByStudentAndDiscipline_returnsCount() {
        Long notExcusedCount = absenceRepository.notExcusedAbsencesCountByStudentAndDiscipline(student.getId(), discipline.getId());

        assertThat(notExcusedCount).isEqualTo(1);
    }

    @Test
    public void absenceRepo_getAllByDisciplineIdAndStudentId_returnsAbsences() {
        List<Absence> absences = absenceRepository.getAllByDisciplineIdAndStudentId(discipline.getId(), student.getId());

        assertThat(absences).isNotNull();
        assertThat(absences.size()).isEqualTo(2);
        assertThat(absences).extracting(Absence::getAbsenceStatus)
                .containsExactlyInAnyOrder(absenceStatusJustified, absenceStatusUnjustified);
    }
}