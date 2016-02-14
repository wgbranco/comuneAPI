package com.comune.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
* Esse arquivo implementa um objeto da classe 'PlaceSurveys' 
* e contém anotações para a criação da tabela 'PLACE_SURVEYS' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "PLACE_SURVEYS",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_place", "id_survey"}))
public class PlaceSurveys 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_place", nullable = false)
	private Place place;
	
	@ManyToOne
	@JoinColumn(name = "id_survey", nullable = false)
	private Survey survey;
	
	@Column(name = "opening_time")
	private Date openingTime;
	
	@Column(name = "closing_time")
	private Date closingTime;
	
	/*@Column(name = "active")
	private boolean active;*/
	
	public PlaceSurveys()
	{}
	
	public PlaceSurveys(Place place, Survey survey)
	{
		this.place = place;
		this.survey = survey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}

	/*public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}*/

}
