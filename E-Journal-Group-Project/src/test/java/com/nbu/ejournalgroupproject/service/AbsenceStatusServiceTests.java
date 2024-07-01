package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import com.nbu.ejournalgroupproject.mappers.AbsenceStatusMapper;
import com.nbu.ejournalgroupproject.model.AbsenceStatus;
import com.nbu.ejournalgroupproject.repository.AbsenceStatusRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.AbsenceStatusServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AbsenceStatusServiceTests {

    @Mock
    private AbsenceStatusRepository absenceStatusRepository;

    @Mock
    private AbsenceStatusMapper absenceStatusMapper;

    @InjectMocks
    private AbsenceStatusServiceImpl absenceStatusService;

    private AbsenceStatus absenceStatus;
    private AbsenceStatusDTO absenceStatusDTO;

    @BeforeEach
    void setUp() {
        absenceStatus = new AbsenceStatus();
        absenceStatus.setId(1L);
        absenceStatus.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);

        absenceStatusDTO = new AbsenceStatusDTO();
        absenceStatusDTO.setId(1L);
        absenceStatusDTO.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);
    }

    @Test
    public void absenceStatusService_getAllAbsenceStatuses_returnsAllAbsenceStatuses() {
        when(absenceStatusRepository.findAll()).thenReturn(List.of(absenceStatus));
        when(absenceStatusMapper.toDTO(any(AbsenceStatus.class))).thenReturn(absenceStatusDTO);

        List<AbsenceStatusDTO> result = absenceStatusService.getAllAbsenceStatuses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(AbsenceStatusEnum.EXCUSED, result.get(0).getAbsenceStatusEnum());
        verify(absenceStatusRepository, times(1)).findAll();
        verify(absenceStatusMapper, times(1)).toDTO(any(AbsenceStatus.class));
    }

    @Test
    public void absenceStatusService_getAbsenceStatusById_returnsAbsenceStatus() {
        when(absenceStatusRepository.findById(1L)).thenReturn(Optional.of(absenceStatus));
        when(absenceStatusMapper.toDTO(any(AbsenceStatus.class))).thenReturn(absenceStatusDTO);

        AbsenceStatusDTO result = absenceStatusService.getAbsenceStatusById(1L);

        assertNotNull(result);
        assertEquals(AbsenceStatusEnum.EXCUSED, result.getAbsenceStatusEnum());
        verify(absenceStatusRepository, times(1)).findById(1L);
        verify(absenceStatusMapper, times(1)).toDTO(any(AbsenceStatus.class));
    }

    @Test
    public void absenceStatusService_getAbsenceStatusById_throwsException() {
        when(absenceStatusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceStatusService.getAbsenceStatusById(1L));
        verify(absenceStatusRepository, times(1)).findById(1L);
    }

    @Test
    public void absenceStatusService_createAbsenceStatus_returnsAbsenceStatus() {
        when(absenceStatusMapper.toEntity(any(AbsenceStatusDTO.class))).thenReturn(absenceStatus);
        when(absenceStatusRepository.save(any(AbsenceStatus.class))).thenReturn(absenceStatus);
        when(absenceStatusMapper.toDTO(any(AbsenceStatus.class))).thenReturn(absenceStatusDTO);

        AbsenceStatusDTO result = absenceStatusService.createAbsenceStatus(absenceStatusDTO);

        assertNotNull(result);
        assertEquals(AbsenceStatusEnum.EXCUSED, result.getAbsenceStatusEnum());
        verify(absenceStatusMapper, times(1)).toEntity(any(AbsenceStatusDTO.class));
        verify(absenceStatusRepository, times(1)).save(any(AbsenceStatus.class));
        verify(absenceStatusMapper, times(1)).toDTO(any(AbsenceStatus.class));
    }

    @Test
    public void absenceStatusService_updateAbsenceStatus_returnsAbsenceStatus() {
        when(absenceStatusRepository.findById(1L)).thenReturn(Optional.of(absenceStatus));
        when(absenceStatusMapper.toEntity(any(AbsenceStatusDTO.class))).thenReturn(absenceStatus);
        when(absenceStatusRepository.save(any(AbsenceStatus.class))).thenReturn(absenceStatus);
        when(absenceStatusMapper.toDTO(any(AbsenceStatus.class))).thenReturn(absenceStatusDTO);

        AbsenceStatusDTO result = absenceStatusService.updateAbsenceStatus(1L, absenceStatusDTO);

        assertNotNull(result);
        assertEquals(AbsenceStatusEnum.EXCUSED, result.getAbsenceStatusEnum());
        verify(absenceStatusRepository, times(1)).findById(1L);
        verify(absenceStatusMapper, times(1)).toEntity(any(AbsenceStatusDTO.class));
        verify(absenceStatusRepository, times(1)).save(any(AbsenceStatus.class));
        verify(absenceStatusMapper, times(1)).toDTO(any(AbsenceStatus.class));
    }

    @Test
    public void absenceStatusService_updateAbsenceStatus_throwsException() {
        when(absenceStatusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceStatusService.updateAbsenceStatus(1L, absenceStatusDTO));
        verify(absenceStatusRepository, times(1)).findById(1L);
    }

    @Test
    public void absenceStatusService_deleteAbsenceStatus_deletesAbsenceStatus() {
        doNothing().when(absenceStatusRepository).deleteById(1L);

        absenceStatusService.deleteAbsenceStatus(1L);

        verify(absenceStatusRepository, times(1)).deleteById(1L);
    }
}