package com.petclinic.vet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petclinic.vet.exception.ResourceNotFoundException;
import com.petclinic.vet.model.Veterinarian;
import com.petclinic.vet.repository.VetRepository;

@Service
public class VetService {

    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public Veterinarian saveVet(Veterinarian vet) {
        return vetRepository.save(vet);
    }

    public List<Veterinarian> getAllVets() {
        return vetRepository.findAll();
    }

    public Veterinarian getVetById(Long id) {

        return vetRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vet not found with id " + id));
    }

    public void deleteVet(Long id) {

        Veterinarian vet = getVetById(id);

        vetRepository.delete(vet);
    }
}