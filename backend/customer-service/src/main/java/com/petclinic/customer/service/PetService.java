package com.petclinic.customer.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.petclinic.customer.dto.PetRequest;
import com.petclinic.customer.exception.ResourceNotFoundException;
import com.petclinic.customer.model.Customer;
import com.petclinic.customer.model.Pet;
import com.petclinic.customer.repository.CustomerRepository;
import com.petclinic.customer.repository.PetRepository;

@Service
public class PetService {

    private static final Logger logger =
            LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository,
                      CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    // Create Pet
    public String createPet(PetRequest request) {

        logger.info("Creating pet: {}", request.getPetName());

        Customer customer = customerRepository.findById(
                request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + request.getCustomerId()));

        Pet pet = new Pet();

        pet.setPetName(request.getPetName());
        pet.setPetType(request.getPetType());
        pet.setBreed(request.getBreed());
        pet.setAge(request.getAge());
        pet.setCustomer(customer);

        petRepository.save(pet);

        return "Pet Created Successfully";
    }

    // Get All Pets
    public List<Pet> getAllPets() {

        logger.info("Fetching all pets");

        return petRepository.findAll();
    }

    // Get Pet By Id
    public Pet getPetById(Long id) {

        logger.info("Fetching pet with id: {}", id);

        return petRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet not found with id: " + id));
    }
 // Update Pet
    public String updatePet(Long id, PetRequest request) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet not found with id: " + id));

        Customer customer = customerRepository.findById(
                request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + request.getCustomerId()));

        pet.setPetName(request.getPetName());
        pet.setPetType(request.getPetType());
        pet.setBreed(request.getBreed());
        pet.setAge(request.getAge());
        pet.setCustomer(customer);

        petRepository.save(pet);

        return "Pet Updated Successfully";
    }

    // Delete Pet
    public String deletePet(Long id) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet not found with id: " + id));

        petRepository.delete(pet);

        return "Pet Deleted Successfully";
    }
 // Get Pets By Customer Id
    public List<Pet> getPetsByCustomerId(Long customerId) {

        logger.info("Fetching pets for customer: {}", customerId);

        return petRepository.findByCustomerCustomerId(customerId);
    }
    
}