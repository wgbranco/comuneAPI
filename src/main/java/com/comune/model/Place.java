package com.comune.model;


import java.util.Set;
import java.io.Serializable;

import com.vividsolutions.jts.geom.Point;


//import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import org.hibernate.validator.constraints.Range;


/**
* Esse arquivo implementa um objeto da classe 'Place' 
* e contém anotações para a criação da tabela 'PLACE' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "PLACE",
uniqueConstraints = @UniqueConstraint(columnNames = {"name", "location"}))
public class Place
{	
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
    @Column(name = "name", length = 255, nullable = false)
	private String name;
	
	@Column(name = "nickname", length = 255)
	private String nickname;
	
	@Column(name = "abbreviated_name", length = 20)
	private String abbrevName;
	
	@Column(name = "address", length = 500, nullable = false)
	private String address;
	
	@Column(name = "rating", nullable = false)
	private Double rating;
	
	/*@Range(min = 0, max = 23)
	@Column(name = "opening_time", nullable = false)
	private int openingTime;
	
	@Range(min = 0, max = 23)
	@Column(name = "closing_time", nullable = false)
	private int closingTime;*/
	
	@Column(name = "status", nullable = false)
	private int status;
	
	@Column(name = "location", nullable = false) //, columnDefinition = "geometry(Point,4326)"
	private Point location;
	
	@ManyToOne
	@JoinColumn(name = "id_subcategory", nullable = false)
	private Subcategory subcategory;
	
	/*
	@OneToMany(mappedBy = "place")
	private Set<PlaceWorkingDays> workingDays;
	
	@OneToMany(mappedBy = "place")
	private Set<PlacePictures> pictures;
	
	@OneToMany(mappedBy = "place")
	private Set<Report> reports;
	
	@OneToMany(mappedBy = "place")
	private Set<PlaceSurveys> surveys;
	
	@OneToMany(mappedBy = "place")
	private Set<CompletedSurvey> completedSurveys;
	
	@OneToMany(mappedBy = "place")
	private Set<YesNoQuestionAnswer> yesNoQuestionAnswers;
	
	@OneToMany(mappedBy = "place")
	private Set<RatingQuestionAnswer> ratingQuestionAnswers;
	
	@OneToMany(mappedBy = "place")
	private Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers;
	
	@OneToMany(mappedBy = "place")
	private Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers;*/
	
	
	public Place()
	{}
	
	public Place(String name, String address, int status, Point location, Subcategory subcategory)
	{
		this.name = name;
		this.address = address;
		this.status = status;
		this.location = location;
		this.subcategory = subcategory;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	/*public Set<PlacePictures> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PlacePictures> pictures) {
		this.pictures = pictures;
	}

	public Set<PlaceWorkingDays> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Set<PlaceWorkingDays> workingDays) {
		this.workingDays = workingDays;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public Set<PlaceSurveys> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<PlaceSurveys> surveys) {
		this.surveys = surveys;
	}

	public Set<CompletedSurvey> getCompletedSurveys() {
		return completedSurveys;
	}

	public void setCompletedSurveys(Set<CompletedSurvey> completedSurveys) {
		this.completedSurveys = completedSurveys;
	}

	public Set<YesNoQuestionAnswer> getYesNoQuestionAnswers() {
		return yesNoQuestionAnswers;
	}

	public void setYesNoQuestionAnswers(
			Set<YesNoQuestionAnswer> yesNoQuestionAnswers) {
		this.yesNoQuestionAnswers = yesNoQuestionAnswers;
	}

	public Set<RatingQuestionAnswer> getRatingQuestionAnswers() {
		return ratingQuestionAnswers;
	}

	public void setRatingQuestionAnswers(
			Set<RatingQuestionAnswer> ratingQuestionAnswers) {
		this.ratingQuestionAnswers = ratingQuestionAnswers;
	}

	public Set<MultipleChoiceQuestionAnswer> getMultipleChoiceQuestionAnswers() {
		return multipleChoiceQuestionAnswers;
	}

	public void setMultipleChoiceQuestionAnswers(
			Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers) {
		this.multipleChoiceQuestionAnswers = multipleChoiceQuestionAnswers;
	}

	public Set<CheckboxesQuestionAnswer> getCheckboxesQuestionAnswers() {
		return checkboxesQuestionAnswers;
	}

	public void setCheckboxesQuestionAnswers(
			Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers) {
		this.checkboxesQuestionAnswers = checkboxesQuestionAnswers;
	}*/
	
}