package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'SurveyAnswersStored', 
* utilizado para mapear objetos JSON enviados como resposta a uma requisição.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class SurveyAnswersStored 
{
	private int userId;
	private int placeId;
	private int surveyId;
	
	
	public SurveyAnswersStored()
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

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	
}
