package com.petclinic.visit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petclinic.visit.dto.AppointmentRequest;
import com.petclinic.visit.model.Appointment;
import com.petclinic.visit.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Create Appointment
    @PostMapping
    public ResponseEntity<String> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        return ResponseEntity.ok(
                appointmentService.createAppointment(request));
    }

    // Get All Appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {

        return ResponseEntity.ok(
                appointmentService.getAllAppointments());
    }

    // Get Appointment By Id
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentById(id));
    }

    // Get Appointments By Customer Id
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByCustomerId(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByCustomerId(customerId));
    }

    // Get Appointments By Pet Id
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPetId(
            @PathVariable Long petId) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByPetId(petId));
    }

    // Get Appointments By Vet Id
    @GetMapping("/vet/{vetId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByVetId(
            @PathVariable Long vetId) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByVetId(vetId));
    }

    // Update Appointment
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request) {

        return ResponseEntity.ok(
                appointmentService.updateAppointment(id, request));
    }

    // Delete Appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.deleteAppointment(id));
    }
    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.acceptAppointment(id));
    }
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.rejectAppointment(id));
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.cancelAppointment(id));
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<String> completeAppointment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.completeAppointment(id));
    }
    
}