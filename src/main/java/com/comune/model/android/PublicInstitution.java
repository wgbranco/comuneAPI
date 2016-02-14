package com.comune.model.android;

import java.util.List;

import com.comune.model.PlaceWorkingDays;


/**
* Esse arquivo implementa um objeto da classe 'PublicInstitution', 
* utilizado para mapear objetos JSON enviados como resposta a uma requisição.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class PublicInstitution 
{
	private int id;
	private String name;	
	private String nickname;
	private String abbrevName;
	private int status;
	private double latitude;
	private double longitude;
	private String address;
	private Double rating;
	//private int openingTime;
	//private int closingTime;
	private int category;
	private int subcategory;
	private List<WorkingDay> workingDays;


	public PublicInstitution()
	{}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAbbrevName() {
		return abbrevName;
	}

	public void setAbbrevName(String abbrevName) {
		this.abbrevName = abbrevName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	/*public int getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(int openingTime) {
		this.openingTime = openingTime;
	}

	public int getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(int closingTime) {
		this.closingTime = closingTime;
	}*/

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(int subcategory) {
		this.subcategory = subcategory;
	}
	
	public List<WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}
}
