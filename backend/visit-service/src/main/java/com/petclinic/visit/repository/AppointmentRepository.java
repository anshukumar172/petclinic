package com.petclinic.visit.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petclinic.visit.model.Appointment;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByPetId(Long petId);

    List<Appointment> findByVetId(Long vetId);

    List<Appointment> findByStatus(String status);

    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    boolean existsByVetIdAndAppointmentDateAndAppointmentTime(
    		
    		Long vetId,
    		LocalDate appointmentDate,
    		LocalTime appointmentTime);
}