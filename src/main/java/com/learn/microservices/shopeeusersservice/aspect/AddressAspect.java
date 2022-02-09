package com.learn.microservices.shopeeusersservice.aspect;

import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.learn.microservices.shopeeusersservice.model.Address;
import com.learn.microservices.shopeeusersservice.model.UserInfo;
import com.learn.microservices.shopeeusersservice.repository.AddressRepository;
import com.learn.microservices.shopeeusersservice.service.UsersService;

@Aspect
public class AddressAspect {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Before( "execution(* com.learn.microservices.shopeeusersservice.repository.AddressRepository.save(..))  && args(newObject)")
	public void checkForPrimaryAddress(JoinPoint joinPoint, Object newObject) {
		if (newObject != null) {
			Address newAddress = (Address) newObject;
			UserInfo existinguser = usersService.userEntityById((newAddress.getUserInfo().getId()));
			// reset any of the previous primary address to 0
			if (newAddress.isPrimaryAddress()) {
				Set<Address> contactAddress = existinguser.getContactAddress().stream().map(addr -> {
					addr.setPrimaryAddress(false);
					return addr;
				}).collect(Collectors.toSet());
				addressRepository.saveAll(contactAddress);
			}
		}
	}

}
