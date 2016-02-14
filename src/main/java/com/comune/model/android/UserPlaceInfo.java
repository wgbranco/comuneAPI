package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'UserPlaceInfo', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class UserPlaceInfo 
{
	private int userId;
	private int placeId;
	
	
	public UserPlaceInfo()
	{}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	
}
