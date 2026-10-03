package com.petclinic.vet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petclinic.vet.model.Veterinarian;

@Repository
public interface VetRepository extends JpaRepository<Veterinarian, Long> {

    Optional<Veterinarian> findByEmail(String email);

    boolean existsByEmail(String email);

}