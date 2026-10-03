package com.petclinic.visit.service;
import com.petclinic.visit.feign.PetClient;
import java.util.List;
import com.petclinic.visit.feign.CustomerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.petclinic.visit.feign.VetClient;
import com.petclinic.visit.dto.AppointmentRequest;
import com.petclinic.visit.enums.AppointmentStatus;
import com.petclinic.visit.exception.ResourceNotFoundException;
import com.petclinic.visit.exception.SlotAlreadyBookedException;
import com.petclinic.visit.model.Appointment;
import com.petclinic.visit.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    
    private final PetClient petClient;
    private final VetClient vetClient;
    private final CustomerClient customerClient;


    public AppointmentService(
            AppointmentRepository appointmentRepository,
            CustomerClient customerClient,
            VetClient vetClient,
            PetClient petClient) {

        this.appointmentRepository = appointmentRepository;
        this.customerClient = customerClient;
        this.vetClient = vetClient;
        this.petClient = petClient;
    }
    


    // Create Appointment

    public String createAppointment(AppointmentRequest request) {

        logger.info("Creating appointment");

        // Validate Customer Exists
        customerClient.getCustomerById(
                request.getCustomerId());

        logger.info("Customer validated successfully");

     // Validate Vet Exists
        vetClient.getVetById(
                request.getVetId());

        logger.info("Vet validated successfully");
     // Validate Pet Exists
        petClient.getPetById(
                request.getPetId());

        logger.info("Pet validated successfully");
        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(request.getStatus());
        appointment.setCustomerId(request.getCustomerId());
        appointment.setPetId(request.getPetId());
        appointment.setVetId(request.getVetId());

        boolean slotExists =
                appointmentRepository
                        .existsByVetIdAndAppointmentDateAndAppointmentTime(
                                request.getVetId(),
                                request.getAppointmentDate(),
                                request.getAppointmentTime());

        if (slotExists) {
            throw new SlotAlreadyBookedException(
                    "Selected slot is already booked");
        }
        
        appointmentRepository.save(appointment);

        logger.info("Appointment created successfully");

        return "Appointment Created Successfully";
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() {

        logger.info("Fetching all appointments");

        return appointmentRepository.findAll();
    }

    // Get Appointment By Id
    public Appointment getAppointmentById(Long id) {

        logger.info("Fetching appointment with id: {}", id);

        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));
    }

    // Get Appointments By Customer Id
    public List<Appointment> getAppointmentsByCustomerId(
            Long customerId) {

        logger.info("Fetching appointments for customer: {}", customerId);

        return appointmentRepository.findByCustomerId(customerId);
    }

    // Get Appointments By Pet Id
    public List<Appointment> getAppointmentsByPetId(
            Long petId) {

        logger.info("Fetching appointments for pet: {}", petId);

        return appointmentRepository.findByPetId(petId);
    }

    // Get Appointments By Vet Id
    public List<Appointment> getAppointmentsByVetId(
            Long vetId) {

        logger.info("Fetching appointments for vet: {}", vetId);

        return appointmentRepository.findByVetId(vetId);
    }

    // Update Appointment
    // Update Appointment
    public String updateAppointment(
            Long id,
            AppointmentRequest request) {

        logger.info("Updating appointment with id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(request.getStatus());
        appointment.setCustomerId(request.getCustomerId());
        appointment.setPetId(request.getPetId());
        appointment.setVetId(request.getVetId());

        appointmentRepository.save(appointment);

        logger.info("Appointment updated successfully");

        return "Appointment Updated Successfully";
    }

    // Delete Appointment
    public String deleteAppointment(Long id) {

        logger.info("Deleting appointment with id: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));

        appointmentRepository.delete(appointment);

        logger.info("Appointment deleted successfully");

        return "Appointment Deleted Successfully";
    }
 // Accept Appointment
    public String acceptAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.ACCEPTED);

        appointmentRepository.save(appointment);

        return "Appointment Accepted Successfully";
    }
 // Reject Appointment
    public String rejectAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.REJECTED);

        appointmentRepository.save(appointment);

        return "Appointment Rejected Successfully";
    }
 // Cancel Appointment
    public String cancelAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.CANCELLED);

        appointmentRepository.save(appointment);

        return "Appointment Cancelled Successfully";
    }
 // Complete Appointment
    public String completeAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id));

        appointment.setStatus(AppointmentStatus.COMPLETED);

        appointmentRepository.save(appointment);

        return "Appointment Completed Successfully";
    }
}