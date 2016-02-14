package com.comune.model;


import javax.persistence.*;


/**
* Esse arquivo implementa um objeto da classe 'ReportPicture' 
* e contém anotações para a criação da tabela 'REPORT_PICTURE' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "REPORT_PICTURE",
				uniqueConstraints = @UniqueConstraint(columnNames = {"id_report", "id_picture"}))
public class ReportPicture 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_report", nullable = false, unique = true)
	private Report report;
	
	@ManyToOne
	@JoinColumn(name = "id_picture", nullable = false)
	private UploadedFiles picture;
	
	
	public ReportPicture()
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

	public UploadedFiles getPicture() {
		return picture;
	}

	public void setPicture(UploadedFiles picture) {
		this.picture = picture;
	}
	
}
