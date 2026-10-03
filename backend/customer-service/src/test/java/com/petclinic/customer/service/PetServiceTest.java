package com.petclinic.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.petclinic.customer.dto.PetRequest;
import com.petclinic.customer.exception.ResourceNotFoundException;
import com.petclinic.customer.model.Customer;
import com.petclinic.customer.model.Pet;
import com.petclinic.customer.repository.CustomerRepository;
import com.petclinic.customer.repository.PetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PetService petService;

    private Pet pet;
    private PetRequest request;
    private Customer customer;

    @BeforeEach
    void setUp() {

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("Anshu");
        customer.setLastName("Kumar");

        pet = new Pet();
        pet.setPetId(1L);
        pet.setPetName("Tommy");
        pet.setPetType("Dog");
        pet.setBreed("Labrador");
        pet.setAge(3);
        pet.setCustomer(customer);

        request = new PetRequest();
        request.setPetName("Tommy");
        request.setPetType("Dog");
        request.setBreed("Labrador");
        request.setAge(3);
        request.setCustomerId(1L);
    }

    @Test
    void testCreatePetSuccess() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        String response = petService.createPet(request);

        assertEquals(
                "Pet Created Successfully",
                response);

        verify(petRepository, times(1))
                .save(any(Pet.class));
    }

    @Test
    void testCreatePetCustomerNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> petService.createPet(request));

        verify(petRepository, never())
                .save(any(Pet.class));
    }

    @Test
    void testGetAllPets() {

        List<Pet> pets = Arrays.asList(pet);

        when(petRepository.findAll())
                .thenReturn(pets);

        List<Pet> result =
                petService.getAllPets();

        assertEquals(1, result.size());
        assertEquals("Tommy",
                result.get(0).getPetName());
    }

    @Test
    void testGetPetByIdSuccess() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.of(pet));

        Pet result =
                petService.getPetById(1L);

        assertNotNull(result);
        assertEquals(1L,
                result.getPetId());
    }

    @Test
    void testGetPetByIdNotFound() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> petService.getPetById(1L));
    }

    @Test
    void testUpdatePetSuccess() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.of(pet));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        String response =
                petService.updatePet(1L, request);

        assertEquals(
                "Pet Updated Successfully",
                response);

        verify(petRepository,
                times(1))
                .save(pet);
    }

    @Test
    void testUpdatePetNotFound() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> petService.updatePet(1L, request));
    }

    @Test
    void testUpdatePetCustomerNotFound() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.of(pet));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> petService.updatePet(1L, request));
    }

    @Test
    void testDeletePetSuccess() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.of(pet));

        String response =
                petService.deletePet(1L);

        assertEquals(
                "Pet Deleted Successfully",
                response);

        verify(petRepository,
                times(1))
                .delete(pet);
    }

    @Test
    void testDeletePetNotFound() {

        when(petRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> petService.deletePet(1L));
    }

    @Test
    void testGetPetsByCustomerId() {

        List<Pet> pets = Arrays.asList(pet);

        when(petRepository.findByCustomerCustomerId(1L))
                .thenReturn(pets);

        List<Pet> result =
                petService.getPetsByCustomerId(1L);

        assertEquals(1, result.size());
        assertEquals("Tommy",
                result.get(0).getPetName());
    }
}