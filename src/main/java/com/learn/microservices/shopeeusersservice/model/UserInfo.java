package com.learn.microservices.shopeeusersservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	@Column(length = 50)
	private String lastName;

	@Column(name = "email_address", nullable = false, unique = true, length = 50)
	private String emailAddress;

	@Column(length = 20)
	private String phoneNumber;
	@Column(length = 20)
	private String alternatePhoneNumber;

	@Column(name = "password", nullable = false, length = 50)
	private String password;
	
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean active = true;
	
	@Column(length = 25)
	private String activationStatus;
	@OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Address> contactAddress = new HashSet<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAlternatePhoneNumber() {
		return alternatePhoneNumber;
	}

	public void setAlternatePhoneNumber(String alternatePhoneNumber) {
		this.alternatePhoneNumber = alternatePhoneNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}

	public Set<Address> getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(Set<Address> contactAddress) {
		this.contactAddress = contactAddress;
	}

	public void putContactAddress(Address address) {
		contactAddress.add(address);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
