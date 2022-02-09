package com.learn.microservices.shopeeusersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.microservices.shopeeusersservice.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
