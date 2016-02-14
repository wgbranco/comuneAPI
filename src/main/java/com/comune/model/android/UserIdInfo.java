package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'UserIdInfo', 
* utilizado para mapear objetos JSON recebidos em uma requisição.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class UserIdInfo
{
	private int id;
	
	
	public UserIdInfo()
	{}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
