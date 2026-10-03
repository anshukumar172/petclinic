package com.petclinic.visit.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VET-SERVICE")
public interface VetClient {

    @GetMapping("/vets/{id}")
    Object getVetById(@PathVariable Long id);
}