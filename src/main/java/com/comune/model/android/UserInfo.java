package com.comune.model.android;


import java.util.Date;


/**
* Esse arquivo implementa um objeto da classe 'UserInfo', 
* utilizado para mapear objetos JSON recebidos em uma requisição de cadastro de novo usuário.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class UserInfo 
{    
	private int id;
	private String firstName;
	private String lastName;
	private long dateOfBirth;
	private String email;
	private String password;
	private String mobileNumber;
	private String phoneNumber;
	private int idPhoto;
	
	
	public UserInfo()
	{}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

}
