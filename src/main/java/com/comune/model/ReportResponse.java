package com.comune.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
* Esse arquivo implementa um objeto da classe 'ReportResponse' 
* e contém anotações para a criação da tabela 'REPORT_RESPONSE' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "REPORT_RESPONSE",
		uniqueConstraints = @UniqueConstraint(columnNames = {"id_report", "id_response"}))
public class ReportResponse 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_report", nullable = false, unique = true)
	private Report report;
	
	@ManyToOne
	@JoinColumn(name = "id_response", nullable = false)
	private Report response;
	
	@Column(name = "visualized", nullable = false)
	private boolean visualized;
	
	
	public ReportResponse()
	{}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Report getResponse() {
		return response;
	}

	public void setResponse(Report response) {
		this.response = response;
	}

	public boolean isVisualized() {
		return visualized;
	}

	public void setVisualized(boolean visualized) {
		this.visualized = visualized;
	}
}
