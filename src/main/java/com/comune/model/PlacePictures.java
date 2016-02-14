package com.comune.model;


import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
* Esse arquivo implementa um objeto da classe 'PlacePictures' 
* e contém anotações para a criação da tabela 'PLACE_PICTURES' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "PLACE_PICTURES")
public class PlacePictures
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_place", nullable = false)
	private Place place;
	
	@Column(name = "descr", length = 255, nullable = false)
	private String descr;
	
	@ManyToOne
	@JoinColumn(name = "id_picture", nullable = false)
	private UploadedFiles picture;
	
	
	public PlacePictures()
	{}
	
	public PlacePictures(Place place, String descr, UploadedFiles picture)
	{
		this.place = place;
		this.descr = descr;
		this.picture = picture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public UploadedFiles getPicture() {
		return picture;
	}

	public void setPicture(UploadedFiles picture) {
		this.picture = picture;
	}
}