package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'ReportResponseVisualized', 
* utilizado para mapear objetos JSON recebidos em uma requisição.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2016-01-16 
*/
public class ReportResponseVisualized 
{
	private int userId;
	private int reportId;
	private int responseId;

	public ReportResponseVisualized()
	{
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
