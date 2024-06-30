package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.DisciplineTypeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DisciplineTypeServiceTests {

    @Mock
    DisciplineTypeRepository disciplineTypeRepository;

    @InjectMocks
    DisciplineTypeServiceImpl disciplineTypeService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

}
