package com.petclinic.visit.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(
	    name = "CUSTOMER-SERVICE",
	    contextId = "petClient"
	)
	public interface PetClient {

	    @GetMapping("/pets/{id}")
	    Object getPetById(@PathVariable Long id);
	}
