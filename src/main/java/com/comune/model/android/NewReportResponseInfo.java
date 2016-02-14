package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'NewReportResponseInfo', 
* utilizado para mapear objetos JSON enviados em resposta a uma requisição.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class NewReportResponseInfo 
{
	private int userId;
	private int placeId;
    private int reportId;
    private int responseId;
    
    
    public NewReportResponseInfo()
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

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}
    
}
