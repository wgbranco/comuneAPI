package com.comune.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
* Esse arquivo implementa um objeto da classe 'CompletedSurvey' 
* e contém anotações para a criação da tabela 'COMPLETED_SURVEY' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "COMPLETED_SURVEY",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "id_place_survey"}))
public class CompletedSurvey
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_place_survey", nullable = false)
	private PlaceSurveys placeSurvey;
	
	@Column(name = "started_at", nullable = false)
	private Timestamp startedAt;
	
	@Column(name = "completed_at", nullable = false)
	private Timestamp completedAt;
	
	
	public CompletedSurvey()
	{}
	
	public CompletedSurvey(User user, PlaceSurveys placeSurvey)
	{
		this.user = user;
		this.placeSurvey = placeSurvey;
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

	public PlaceSurveys getPlaceSurvey() {
		return placeSurvey;
	}

	public void setPlaceSurvey(PlaceSurveys placeSurvey) {
		this.placeSurvey = placeSurvey;
	}

	public Timestamp getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Timestamp startedAt) {
		this.startedAt = startedAt;
	}

	public Timestamp getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Timestamp completedAt) {
		this.completedAt = completedAt;
	}
	
}
