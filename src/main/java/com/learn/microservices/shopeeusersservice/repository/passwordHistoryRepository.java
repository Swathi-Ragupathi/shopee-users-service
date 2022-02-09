package com.learn.microservices.shopeeusersservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learn.microservices.shopeeusersservice.model.PasswordHistory;

public interface passwordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

	List<PasswordHistory> findAllByUserInfoId(Long id);
	
	@Query(value = "select * from password_history where user_info_id =?1 order by created_date desc limit 1", nativeQuery = true)
	PasswordHistory findLatestByUserInfoId(Long id);

}
