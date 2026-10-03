package com.petclinic.visit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petclinic.visit.model.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByCustomerId(Long customerId);

    List<Visit> findByPetId(Long petId);

    List<Visit> findByVetId(Long vetId);

    List<Visit> findByAppointmentId(Long appointmentId);
}