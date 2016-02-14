package com.comune.model;


import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;


/**
* Esse arquivo implementa um objeto da classe 'Report' 
* e contém anotações para a criação da tabela 'REPORT' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "REPORT",
		uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "made_at"}))
public class Report
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_place", nullable = false)
	private Place place;
	
	@NotEmpty
    @Column(name = "comment", length = 1000, nullable = false)
	private String comment;
	
	@Column(name = "made_at", nullable = false)
	private Timestamp madeAt;
	
	@Column(name = "received_at", nullable = false)
	private Timestamp receivedAt;
	
	/*@OneToMany(mappedBy = "report")
	private ReportResponse reportResponse;*/
	
	
	public Report()
	{}
			
	public Report(User user, Place place, String comment, Timestamp madeAt)
	{
		this.user = user;
		this.place = place;
		this.comment = comment;
		this.madeAt = madeAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/*public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public Blob getVideo() {
		return video;
	}

	public void setVideo(Blob video) {
		this.video = video;
	}*/

	public Timestamp getMadeAt() {
		return madeAt;
	}

	public void setMadeAt(Timestamp madeAt) {
		this.madeAt = madeAt;
	}

	public Timestamp getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(Timestamp receivedAt) {
		this.receivedAt = receivedAt;
	}

	/*public ReportResponse getReportResponse() {
		return reportResponse;
	}

	public void setReportResponse(ReportResponse reportResponse) {
		this.reportResponse = reportResponse;
	}*/
	
}