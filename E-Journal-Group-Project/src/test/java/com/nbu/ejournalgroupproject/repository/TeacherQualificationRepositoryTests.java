package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
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
public class TeacherQualificationRepositoryTests {

    @Autowired
    private TeacherQualificationRepository teacherQualificationRepository;

    private TeacherQualification biologyQualification;
    private TeacherQualification chemistryQualification;
    private TeacherQualification mathQualification;

    @BeforeEach
    public void setUp() {
        biologyQualification = new TeacherQualification();
        biologyQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);

        chemistryQualification = new TeacherQualification();
        chemistryQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_CHEMISTRY_TEACHING);

        mathQualification = new TeacherQualification();
        mathQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_MATH_TEACHING);

        teacherQualificationRepository.save(biologyQualification);
        teacherQualificationRepository.save(chemistryQualification);
        teacherQualificationRepository.save(mathQualification);
    }

    @AfterEach
    public void tearDown() {
        teacherQualificationRepository.deleteAll();
    }

    @Test
    public void teacherQualificationRepo_findAll_returnsManyTeacherQualifications() {
        List<TeacherQualification> qualifications = teacherQualificationRepository.findAll();

        assertThat(qualifications).isNotNull();
        assertThat(qualifications.size()).isEqualTo(3);
        assertThat(qualifications).extracting(TeacherQualification::getQualificationEnum)
                .containsExactlyInAnyOrder(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, TeacherQualificationEnum.PERMIT_CHEMISTRY_TEACHING, TeacherQualificationEnum.PERMIT_MATH_TEACHING);
    }

    @Test
    public void teacherQualificationRepo_findById_returnsTeacherQualification() {
        Optional<TeacherQualification> foundQualification = teacherQualificationRepository.findById(biologyQualification.getId());

        assertThat(foundQualification).isPresent();
        assertThat(foundQualification.get().getQualificationEnum()).isEqualTo(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);
    }

    @Test
    public void teacherQualificationRepo_findById_throwsEntityNotFoundException() {
        Long notFoundId = 10L;
        assertThrows(EntityNotFoundException.class, () ->
                teacherQualificationRepository.findById(notFoundId)
                .orElseThrow(EntityNotFoundException::new)
                );
    }

    @Test
    public void teacherQualificationRepo_save_returnsSavedTeacherQualification() {
        TeacherQualification newQualification = new TeacherQualification();
        newQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_HISTORY_TEACHING);

        TeacherQualification savedQualification = teacherQualificationRepository.save(newQualification);

        assertThat(savedQualification).isNotNull();
        assertThat(savedQualification.getId()).isNotNull();
        assertThat(savedQualification.getQualificationEnum()).isEqualTo(TeacherQualificationEnum.PERMIT_HISTORY_TEACHING);
    }

    @Test
    public void teacherQualificationRepo_update_returnsUpdatedTeacherQualification() {
        biologyQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_HISTORY_TEACHING);
        TeacherQualification updatedQualification = teacherQualificationRepository.save(biologyQualification);

        assertThat(updatedQualification).isNotNull();
        assertThat(updatedQualification.getId()).isNotNull();
        assertThat(updatedQualification.getQualificationEnum()).isEqualTo(TeacherQualificationEnum.PERMIT_HISTORY_TEACHING);
    }

    @Test
    public void teacherQualificationRepo_delete_returnsDeletedTeacherQualification() {
        teacherQualificationRepository.delete(biologyQualification);
        Optional<TeacherQualification> deletedQualification = teacherQualificationRepository.findById(biologyQualification.getId());

        assertThat(deletedQualification).isNotPresent();
    }
}
