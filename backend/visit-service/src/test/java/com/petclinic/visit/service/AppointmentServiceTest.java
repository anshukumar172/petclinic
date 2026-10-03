package com.petclinic.visit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.petclinic.visit.dto.AppointmentRequest;
import com.petclinic.visit.enums.AppointmentStatus;
import com.petclinic.visit.exception.ResourceNotFoundException;
import com.petclinic.visit.exception.SlotAlreadyBookedException;
import com.petclinic.visit.feign.CustomerClient;
import com.petclinic.visit.feign.PetClient;
import com.petclinic.visit.feign.VetClient;
import com.petclinic.visit.model.Appointment;
import com.petclinic.visit.repository.AppointmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private VetClient vetClient;

    @Mock
    private PetClient petClient;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private AppointmentRequest request;

    @BeforeEach
    void setUp() {

        appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setAppointmentDate(LocalDate.of(2026, 9, 17));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCustomerId(1L);
        appointment.setPetId(1L);
        appointment.setVetId(2L);

        request = new AppointmentRequest();
        request.setAppointmentDate(LocalDate.of(2026, 9, 17));
        request.setAppointmentTime(LocalTime.of(10, 30));
        request.setStatus(AppointmentStatus.PENDING);
        request.setCustomerId(1L);
        request.setPetId(1L);
        request.setVetId(2L);
    }

    @Test
    void testCreateAppointmentSuccess() {

        when(customerClient.getCustomerById(1L))
                .thenReturn(new Object());

        when(vetClient.getVetById(2L))
                .thenReturn(new Object());

        when(petClient.getPetById(1L))
                .thenReturn(new Object());

        when(appointmentRepository
                .existsByVetIdAndAppointmentDateAndAppointmentTime(
                        anyLong(),
                        any(LocalDate.class),
                        any(LocalTime.class)))
                .thenReturn(false);

        String result =
                appointmentService.createAppointment(request);

        assertEquals(
                "Appointment Created Successfully",
                result);

        verify(customerClient).getCustomerById(1L);
        verify(vetClient).getVetById(2L);
        verify(petClient).getPetById(1L);

        verify(appointmentRepository)
                .save(any(Appointment.class));
    }

    @Test
    void testCreateAppointmentSlotAlreadyBooked() {

        when(appointmentRepository
                .existsByVetIdAndAppointmentDateAndAppointmentTime(
                        anyLong(),
                        any(LocalDate.class),
                        any(LocalTime.class)))
                .thenReturn(true);

        assertThrows(
                SlotAlreadyBookedException.class,
                () -> appointmentService.createAppointment(request));

        verify(appointmentRepository, never())
                .save(any(Appointment.class));
    }

    @Test
    void testGetAllAppointments() {

        when(appointmentRepository.findAll())
                .thenReturn(Arrays.asList(appointment));

        List<Appointment> result =
                appointmentService.getAllAppointments();

        assertEquals(1, result.size());
    }

    @Test
    void testGetAppointmentByIdSuccess() {

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        Appointment result =
                appointmentService.getAppointmentById(1L);

        assertNotNull(result);
        assertEquals(1L,
                result.getAppointmentId());
    }

    @Test
    void testGetAppointmentByIdNotFound() {

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.getAppointmentById(1L));
    }

    @Test
    void testGetAppointmentsByCustomerId() {

        when(appointmentRepository.findByCustomerId(1L))
                .thenReturn(Arrays.asList(appointment));

        List<Appointment> result =
                appointmentService
                        .getAppointmentsByCustomerId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetAppointmentsByPetId() {

        when(appointmentRepository.findByPetId(1L))
                .thenReturn(Arrays.asList(appointment));

        List<Appointment> result =
                appointmentService
                        .getAppointmentsByPetId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetAppointmentsByVetId() {

        when(appointmentRepository.findByVetId(2L))
                .thenReturn(Arrays.asList(appointment));

        List<Appointment> result =
                appointmentService
                        .getAppointmentsByVetId(2L);

        assertEquals(1, result.size());
    }

    @Test
    void testUpdateAppointmentSuccess() {

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        String result =
                appointmentService.updateAppointment(
                        1L,
                        request);

        assertEquals(
                "Appointment Updated Successfully",
                result);

        verify(appointmentRepository)
                .save(appointment);
    }
}