package com.comune.model;


import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
* Esse arquivo implementa um objeto da classe 'Survey' 
* e contém anotações para a criação da tabela 'SURVEY' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "SURVEY")
public class Survey 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "description", length = 1000, nullable = false)
	private String description;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	/*@OneToMany(mappedBy = "survey")
	private Set<PlaceSurveys> placeSurveys;
	
	@OneToMany(mappedBy = "survey")
	private Set<SurveyQuestion> surveyQuestions;
	
	@OneToMany(mappedBy = "survey")
	private Set<CompletedSurvey> completedSurveys;
	
	@OneToMany(mappedBy = "survey")
	private Set<YesNoQuestionAnswer> yesNoQuestionAnswers;
	
	@OneToMany(mappedBy = "survey")
	private Set<RatingQuestionAnswer> ratingQuestionAnswers;
	
	@OneToMany(mappedBy = "survey")
	private Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers;
	
	@OneToMany(mappedBy = "survey")
	private Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers;*/
	
	public Survey()
	{}
	
	public Survey(String description)
	{
		this.description = description;
	}

	
	/*Getters and Setters*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	/*public Set<CompletedSurvey> getCompletedSurveys() {
		return completedSurveys;
	}

	public void setCompletedSurveys(Set<CompletedSurvey> completedSurveys) {
		this.completedSurveys = completedSurveys;
	}
	
	public Set<SurveyQuestion> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(Set<SurveyQuestion> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public Set<PlaceSurveys> getPlaceSurveys() {
		return placeSurveys;
	}

	public void setPlaceSurveys(Set<PlaceSurveys> placeSurveys) {
		this.placeSurveys = placeSurveys;
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
