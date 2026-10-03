package com.petclinic.visit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.petclinic.visit.dto.VisitRequest;
import com.petclinic.visit.exception.ResourceNotFoundException;
import com.petclinic.visit.model.Visit;
import com.petclinic.visit.repository.VisitRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitService visitService;

    private Visit visit;
    private VisitRequest request;

    @BeforeEach
    void setUp() {

        visit = new Visit();
        visit.setVisitId(1L);
        visit.setVisitDate(LocalDate.of(2026, 9, 17));
        visit.setDiagnosis("Fever");
        visit.setTreatment("Medication");
        visit.setPrescription("Paracetamol");
        visit.setCustomerId(1L);
        visit.setPetId(1L);
        visit.setVetId(2L);
        visit.setAppointmentId(1L);

        request = new VisitRequest();
        request.setVisitDate(LocalDate.of(2026, 9, 17));
        request.setDiagnosis("Fever");
        request.setTreatment("Medication");
        request.setPrescription("Paracetamol");
        request.setCustomerId(1L);
        request.setPetId(1L);
        request.setVetId(2L);
        request.setAppointmentId(1L);
    }

    @Test
    void testCreateVisitSuccess() {

        String result =
                visitService.createVisit(request);

        assertEquals(
                "Visit Created Successfully",
                result);

        verify(visitRepository, times(1))
                .save(any(Visit.class));
    }

    @Test
    void testGetAllVisits() {

        when(visitRepository.findAll())
                .thenReturn(Arrays.asList(visit));

        List<Visit> result =
                visitService.getAllVisits();

        assertEquals(1, result.size());
    }

    @Test
    void testGetVisitByIdSuccess() {

        when(visitRepository.findById(1L))
                .thenReturn(Optional.of(visit));

        Visit result =
                visitService.getVisitById(1L);

        assertNotNull(result);

        assertEquals(
                1L,
                result.getVisitId());
    }

    @Test
    void testGetVisitByIdNotFound() {

        when(visitRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> visitService.getVisitById(1L));
    }

    @Test
    void testGetVisitsByCustomerId() {

        when(visitRepository.findByCustomerId(1L))
                .thenReturn(Arrays.asList(visit));

        List<Visit> result =
                visitService.getVisitsByCustomerId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetVisitsByPetId() {

        when(visitRepository.findByPetId(1L))
                .thenReturn(Arrays.asList(visit));

        List<Visit> result =
                visitService.getVisitsByPetId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetVisitsByVetId() {

        when(visitRepository.findByVetId(2L))
                .thenReturn(Arrays.asList(visit));

        List<Visit> result =
                visitService.getVisitsByVetId(2L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetVisitsByAppointmentId() {

        when(visitRepository.findByAppointmentId(1L))
                .thenReturn(Arrays.asList(visit));

        List<Visit> result =
                visitService.getVisitsByAppointmentId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testUpdateVisitSuccess() {

        when(visitRepository.findById(1L))
                .thenReturn(Optional.of(visit));

        String result =
                visitService.updateVisit(1L, request);

        assertEquals(
                "Visit Updated Successfully",
                result);

        verify(visitRepository, times(1))
                .save(visit);
    }

    @Test
    void testUpdateVisitNotFound() {

        when(visitRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> visitService.updateVisit(1L, request));
    }

    @Test
    void testDeleteVisitSuccess() {

        when(visitRepository.findById(1L))
                .thenReturn(Optional.of(visit));

        String result =
                visitService.deleteVisit(1L);

        assertEquals(
                "Visit Deleted Successfully",
                result);

        verify(visitRepository, times(1))
                .delete(visit);
    }

    @Test
    void testDeleteVisitNotFound() {

        when(visitRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> visitService.deleteVisit(1L));
    }

}