package com.comune.model.android;


import java.util.ArrayList;

import com.comune.model.Survey;


/**
* Esse arquivo implementa um objeto da classe 'AvailableSurveys', 
* utilizado para mapear objetos JSON enviados em resposta a uma requisição.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class AvailableSurveys 
{
	private int userId;
	private int placeId;
	private ArrayList<Survey> surveys;
	
	
	public AvailableSurveys()
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

	/*public ArrayList<Integer> getSurveysIds() {
		return surveysIds;
	}

	public void setSurveysIds(ArrayList<Integer> surveysIds) {
		this.surveysIds = surveysIds;
	}*/
	
	public ArrayList<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(ArrayList<Survey> surveys) {
		this.surveys = surveys;
	}
	
}
