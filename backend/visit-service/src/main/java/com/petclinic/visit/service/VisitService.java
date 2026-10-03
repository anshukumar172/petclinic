package com.petclinic.visit.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.petclinic.visit.dto.VisitRequest;
import com.petclinic.visit.exception.ResourceNotFoundException;
import com.petclinic.visit.model.Visit;
import com.petclinic.visit.repository.VisitRepository;

@Service
public class VisitService {

    private static final Logger logger =
            LoggerFactory.getLogger(VisitService.class);

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    // Create Visit
    public String createVisit(VisitRequest request) {

        logger.info("Creating visit");

        Visit visit = new Visit();

        visit.setVisitDate(request.getVisitDate());
        visit.setDiagnosis(request.getDiagnosis());
        visit.setTreatment(request.getTreatment());
        visit.setPrescription(request.getPrescription());
        visit.setCustomerId(request.getCustomerId());
        visit.setPetId(request.getPetId());
        visit.setVetId(request.getVetId());
        visit.setAppointmentId(request.getAppointmentId());

        visitRepository.save(visit);

        logger.info("Visit created successfully");

        return "Visit Created Successfully";
    }

    // Get All Visits
    public List<Visit> getAllVisits() {

        logger.info("Fetching all visits");

        return visitRepository.findAll();
    }

    // Get Visit By Id
    public Visit getVisitById(Long id) {

        logger.info("Fetching visit with id: {}", id);

        return visitRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with id: " + id));
    }

    // Get Visits By Customer Id
    public List<Visit> getVisitsByCustomerId(Long customerId) {

        logger.info("Fetching visits for customer: {}", customerId);

        return visitRepository.findByCustomerId(customerId);
    }

    // Get Visits By Pet Id
    public List<Visit> getVisitsByPetId(Long petId) {

        logger.info("Fetching visits for pet: {}", petId);

        return visitRepository.findByPetId(petId);
    }

    // Get Visits By Vet Id
    public List<Visit> getVisitsByVetId(Long vetId) {

        logger.info("Fetching visits for vet: {}", vetId);

        return visitRepository.findByVetId(vetId);
    }

    // Get Visits By Appointment Id
    public List<Visit> getVisitsByAppointmentId(Long appointmentId) {

        logger.info("Fetching visits for appointment: {}", appointmentId);

        return visitRepository.findByAppointmentId(appointmentId);
    }
    
    // Update Visit
    public String updateVisit(Long id, VisitRequest request) {

        logger.info("Updating visit with id: {}", id);

        Visit visit = visitRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with id: " + id));

        visit.setVisitDate(request.getVisitDate());
        visit.setDiagnosis(request.getDiagnosis());
        visit.setTreatment(request.getTreatment());
        visit.setPrescription(request.getPrescription());
        visit.setCustomerId(request.getCustomerId());
        visit.setPetId(request.getPetId());
        visit.setVetId(request.getVetId());
        visit.setAppointmentId(request.getAppointmentId());

        visitRepository.save(visit);

        logger.info("Visit updated successfully");

        return "Visit Updated Successfully";
    }

    // Delete Visit
    public String deleteVisit(Long id) {

        logger.info("Deleting visit with id: {}", id);

        Visit visit = visitRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with id: " + id));

        visitRepository.delete(visit);

        logger.info("Visit deleted successfully");

        return "Visit Deleted Successfully";
    }
}

    
    