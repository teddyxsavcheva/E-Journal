package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.TeacherQualificationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TeacherQualificationServiceTests {

    @Mock
    TeacherQualificationRepository teacherQualificationRepository;

    @InjectMocks
    TeacherQualificationServiceImpl teacherQualificationService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }
}
