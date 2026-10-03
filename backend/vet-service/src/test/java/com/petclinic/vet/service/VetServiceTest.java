package com.petclinic.vet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.petclinic.vet.exception.ResourceNotFoundException;
import com.petclinic.vet.model.Veterinarian;
import com.petclinic.vet.repository.VetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetService vetService;

    private Veterinarian vet;

    @BeforeEach
    void setUp() {

        vet = new Veterinarian();
        vet.setId(1L);
        vet.setName("Dr. Smith");
        vet.setSpecialization("Dog Specialist");
        vet.setEmail("smith@gmail.com");
        vet.setPhone("9999999999");
        vet.setExperience(5);
    }

    @Test
    void testSaveVet() {

        when(vetRepository.save(vet))
                .thenReturn(vet);

        Veterinarian result =
                vetService.saveVet(vet);

        assertNotNull(result);
        assertEquals("Dr. Smith",
                result.getName());

        verify(vetRepository,
                times(1))
                .save(vet);
    }

    @Test
    void testGetAllVets() {

        List<Veterinarian> vets =
                Arrays.asList(vet);

        when(vetRepository.findAll())
                .thenReturn(vets);

        List<Veterinarian> result =
                vetService.getAllVets();

        assertEquals(1,
                result.size());

        assertEquals("Dr. Smith",
                result.get(0).getName());
    }

    @Test
    void testGetVetByIdSuccess() {

        when(vetRepository.findById(1L))
                .thenReturn(Optional.of(vet));

        Veterinarian result =
                vetService.getVetById(1L);

        assertNotNull(result);
        assertEquals(1L,
                result.getId());

        assertEquals("Dr. Smith",
                result.getName());
    }

    @Test
    void testGetVetByIdNotFound() {

        when(vetRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> vetService.getVetById(1L));
    }

    @Test
    void testDeleteVetSuccess() {

        when(vetRepository.findById(1L))
                .thenReturn(Optional.of(vet));

        vetService.deleteVet(1L);

        verify(vetRepository,
                times(1))
                .delete(vet);
    }

    @Test
    void testDeleteVetNotFound() {

        when(vetRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> vetService.deleteVet(1L));
    }
}
