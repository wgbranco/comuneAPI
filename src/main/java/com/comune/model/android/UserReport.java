package com.comune.model.android;


import java.sql.Timestamp;


/**
* Esse arquivo implementa um objeto da classe 'UserReport', 
* utilizado para mapear objetos JSON enviados como resposta a uma requisição.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class UserReport 
{
	private int reportId;
	private int userId;
	private int placeId;
	private int responseId;
	private String comment;
	private long madeAt;
	private boolean responseVisualized;
	private int idPicture;
	private int idFootage;
	
	
	public UserReport()
	{}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

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
	
	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public boolean isResponseVisualized() {
		return responseVisualized;
	}

	public void setResponseVisualized(boolean responseVisualized) {
		this.responseVisualized = responseVisualized;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getMadeAt() {
		return madeAt;
	}

	public void setMadeAt(long madeAt) {
		this.madeAt = madeAt;
	}

	public int getIdPicture() {
		return idPicture;
	}

	public void setIdPicture(int idPicture) {
		this.idPicture = idPicture;
	}

	public int getIdFootage() {
		return idFootage;
	}

	public void setIdFootage(int idFootage) {
		this.idFootage = idFootage;
	}

}
