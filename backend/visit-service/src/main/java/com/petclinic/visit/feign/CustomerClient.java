package com.petclinic.visit.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
	    name = "CUSTOMER-SERVICE",
	    contextId = "customerClient"
	)
	public interface CustomerClient {

	    @GetMapping("/customers/{id}")
	    Object getCustomerById(@PathVariable Long id);
	}