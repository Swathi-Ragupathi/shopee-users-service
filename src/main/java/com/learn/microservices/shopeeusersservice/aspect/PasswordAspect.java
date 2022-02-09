package com.learn.microservices.shopeeusersservice.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.learn.microservices.shopeeusersservice.model.PasswordHistory;
import com.learn.microservices.shopeeusersservice.model.UserInfo;
import com.learn.microservices.shopeeusersservice.repository.passwordHistoryRepository;

@Aspect
public class PasswordAspect {

	@Autowired
	private passwordHistoryRepository passwordHistoryRepository;

	@AfterReturning(pointcut = "execution(* com.learn.microservices.shopeeusersservice.repository.UserInfoRepository.save(..))", returning = "result")
	public void savePasswordHistory(JoinPoint joinPoint, Object result) {

		if (Objects.nonNull(result)) {
			UserInfo userInfo = (UserInfo) result;
			PasswordHistory latestPasswordHistory = passwordHistoryRepository.findLatestByUserInfoId(userInfo.getId());
			if (latestPasswordHistory == null || !latestPasswordHistory.getPassword().equals(userInfo.getPassword())) {

				List<PasswordHistory> passwordHistoryEntries = new ArrayList<>();
				if (latestPasswordHistory != null) {
					passwordHistoryEntries = passwordHistoryRepository.findAllByUserInfoId(userInfo.getId());
					passwordHistoryEntries = passwordHistoryEntries.stream().map(t -> {
						t.setCurrent(false);
						return t;
					}).collect(Collectors.toList());
				}

				PasswordHistory newPasswordHistory = PasswordHistory.builder().userInfoId(userInfo.getId())
						.current(true).password(userInfo.getPassword()).build();
				passwordHistoryEntries.add(newPasswordHistory);

				passwordHistoryRepository.saveAll(passwordHistoryEntries);
			}
		}

	}
}
