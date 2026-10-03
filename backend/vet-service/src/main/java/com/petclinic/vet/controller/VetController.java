package com.petclinic.vet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petclinic.vet.model.Veterinarian;
import com.petclinic.vet.service.VetService;

@RestController
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @PostMapping
    public ResponseEntity<Veterinarian> createVet(
            @RequestBody Veterinarian vet) {

        return ResponseEntity.ok(
                vetService.saveVet(vet));
    }

    @GetMapping
    public ResponseEntity<List<Veterinarian>> getAllVets() {

        return ResponseEntity.ok(
                vetService.getAllVets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinarian> getVetById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vetService.getVetById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVet(
            @PathVariable Long id) {

        vetService.deleteVet(id);

        return ResponseEntity.ok(
                "Vet deleted successfully");
    }
}