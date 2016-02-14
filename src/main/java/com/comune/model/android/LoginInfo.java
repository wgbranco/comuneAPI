package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'LoginInfo', 
* utilizado para mapear objetos JSON recebidos em uma requisição de login.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class LoginInfo 
{
	private String email;
	private String password;
	
	
	public LoginInfo()
	{}

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

}
