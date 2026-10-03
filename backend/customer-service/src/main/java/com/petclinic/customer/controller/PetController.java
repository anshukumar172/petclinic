package com.petclinic.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petclinic.customer.dto.PetRequest;
import com.petclinic.customer.model.Pet;
import com.petclinic.customer.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Create Pet
    @PostMapping
    public ResponseEntity<String> createPet(
            @Valid @RequestBody PetRequest request) {

        return ResponseEntity.ok(
                petService.createPet(request));
    }

    // Get All Pets
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {

        return ResponseEntity.ok(
                petService.getAllPets());
    }

    // Get Pet By Id
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                petService.getPetById(id));
    }

    // Get Pets By Customer Id
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Pet>> getPetsByCustomerId(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                petService.getPetsByCustomerId(customerId));
    }
    

    // Update Pet
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody PetRequest request) {

        return ResponseEntity.ok(
                petService.updatePet(id, request));
    }

    // Delete Pet
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePet(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                petService.deletePet(id));
    }
}