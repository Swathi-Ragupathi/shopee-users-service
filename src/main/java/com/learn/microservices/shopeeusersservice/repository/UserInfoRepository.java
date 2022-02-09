package com.learn.microservices.shopeeusersservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.microservices.shopeeusersservice.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByEmailAddress(String emailAddress);

	UserInfo findByIdAndActiveIsTrue(Long userId);

}
