package com.comune.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Range;

import java.sql.Time;


/**
* Esse arquivo implementa um objeto da classe 'PlaceWorkingDays' 
* e contém anotações para a criação da tabela 'PLACE_WORKING_DAYS' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "PLACE_WORKING_DAYS",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_place", "day_of_the_week"}))
public class PlaceWorkingDays
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_place", nullable = false)
	private Place place;
	
	@Range(min = 1, max = 7)
	@Column(name = "day_of_the_week", nullable = false)
	private int dayOfTheWeek;
	
	@Column(name = "opening_time", nullable = false)
	private Time openingTime;
	
	@Column(name = "closing_time", nullable = false)
	private Time closingTime;
	
	
	public PlaceWorkingDays()
	{}
	
	public PlaceWorkingDays(Place place, int dayOfTheWeek)
	{
		this.place = place;
		this.dayOfTheWeek = dayOfTheWeek;
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

	public int getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(int dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public Time getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime;
	}

	public Time getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime;
	}
	
}