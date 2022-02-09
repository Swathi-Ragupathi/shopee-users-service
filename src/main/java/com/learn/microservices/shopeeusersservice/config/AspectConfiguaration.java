package com.learn.microservices.shopeeusersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.learn.microservices.shopeeusersservice.aspect.AddressAspect;
import com.learn.microservices.shopeeusersservice.aspect.PasswordAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguaration {
	
	@Bean
	public PasswordAspect passwordAspect() {
		return new PasswordAspect();
	}
	
	@Bean 
	public AddressAspect addressAspect() {
		return new AddressAspect();
	}

}
