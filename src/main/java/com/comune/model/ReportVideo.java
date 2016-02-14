package com.comune.model;


import javax.persistence.*;


/**
* Esse arquivo implementa um objeto da classe 'ReportVideo' 
* e contém anotações para a criação da tabela 'REPORT_VIDEO' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "REPORT_VIDEO",
				uniqueConstraints = @UniqueConstraint(columnNames = {"id_report", "id_video"}))
public class ReportVideo 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
		
	@ManyToOne
	@JoinColumn(name = "id_report", nullable = false, unique = true)
	private Report report;
	
	@ManyToOne
	@JoinColumn(name = "id_video", nullable = false)
	private UploadedFiles video;
	
	public ReportVideo()
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

	public UploadedFiles getVideo() {
		return video;
	}

	public void setVideo(UploadedFiles video) {
		this.video = video;
	}

}
