package com.petclinic.visit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petclinic.visit.dto.VisitRequest;
import com.petclinic.visit.model.Visit;
import com.petclinic.visit.service.VisitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/visits")
public class VisitController {


    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    // Create Visit
    @PostMapping
    public ResponseEntity<String> createVisit(
            @Valid @RequestBody VisitRequest request) {

        return ResponseEntity.ok(
                visitService.createVisit(request));
    }

    // Get All Visits
    @GetMapping
    public ResponseEntity<List<Visit>> getAllVisits() {

        return ResponseEntity.ok(
                visitService.getAllVisits());
    }

    // Get Visit By Id
    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                visitService.getVisitById(id));
    }

    // Get Visits By Customer Id
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Visit>> getVisitsByCustomerId(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                visitService.getVisitsByCustomerId(customerId));
    }

    // Get Visits By Pet Id
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Visit>> getVisitsByPetId(
            @PathVariable Long petId) {

        return ResponseEntity.ok(
                visitService.getVisitsByPetId(petId));
    }

    // Get Visits By Vet Id
    @GetMapping("/vet/{vetId}")
    public ResponseEntity<List<Visit>> getVisitsByVetId(
            @PathVariable Long vetId) {

        return ResponseEntity.ok(
                visitService.getVisitsByVetId(vetId));
    }

    // Get Visits By Appointment Id
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Visit>> getVisitsByAppointmentId(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                visitService.getVisitsByAppointmentId(appointmentId));
    }

    // Update Visit
    @PutMapping("/{id}")
    public ResponseEntity<String> updateVisit(
            @PathVariable Long id,
            @Valid @RequestBody VisitRequest request) {

        return ResponseEntity.ok(
                visitService.updateVisit(id, request));
    }

    // Delete Visit
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVisit(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                visitService.deleteVisit(id));
    }
}
	