package com.comune.model.android;


import java.sql.Timestamp;
import java.util.List;


/**
* Esse arquivo implementa um objeto da classe 'EntireSurvey', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class EntireSurvey 
{
	private int userId;
	private int placeId;
	private int surveyId;	
	private String description;	
	private long availableSince;
	private long startedAt;
	private long completedAt;
	private List<EntireQuestion> questions;
	
	
	public EntireSurvey()
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

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getAvailableSince() {
		return availableSince;
	}
	
	public void setAvailableSince(long availableSince) {
		this.availableSince = availableSince;
	}

	/*public Timestamp getStartedAt() {
		return new Timestamp(startedAt);
	}*/
	public long getStartedAt() {
		return startedAt;
	}

	/*public void setStartedAt(Timestamp startedAt) {
		this.startedAt = startedAt.getTime();
	}*/
	
	public void setStartedAt(long startedAt) {
		this.startedAt = startedAt;
	}

	/*public Timestamp getCompletedAt() {
		return new Timestamp(completedAt);
	}*/
	public long getCompletedAt() {
		return completedAt;
	}

	/*public void setCompletedAt(Timestamp completedAt) {
		this.completedAt = completedAt.getTime();
	}*/
	
	public void setCompletedAt(long completedAt) {
		this.completedAt = completedAt;
	}

	public List<EntireQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<EntireQuestion> questions) {
		this.questions = questions;
	}
}
