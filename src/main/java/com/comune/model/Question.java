package com.comune.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
* Esse arquivo implementa um objeto da classe 'Question' 
* e contém anotações para a criação da tabela 'QUESTION' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "QUESTION")
public class Question 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "descr", length = 255, nullable = false)
	private String descr;
	
	@Column(name = "mandatory", nullable = false)
	private boolean mandatory;
	
	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "id_subcategory")
	private Subcategory subcategory;
	
	@ManyToOne
	@JoinColumn(name = "id_type", nullable = false)
	private QuestionType questionType;
	
	/*@OneToMany(mappedBy = "question")
	private Set<SurveyQuestion> surveyQuestions;
	
	@OneToMany(mappedBy = "question")
	private Set<QuestionAnswerOption> questionAnswerOptions;
	
	@OneToMany(mappedBy = "question")
	private Set<YesNoQuestionAnswer> yesNoQuestionAnswers;
	
	@OneToMany(mappedBy = "question")
	private Set<RatingQuestionAnswer> ratingQuestionAnswers;
	
	@OneToMany(mappedBy = "question")
	private Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers;
	
	@OneToMany(mappedBy = "question")
	private Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers;*/
	
	
	public Question()
	{}
	
	public Question(String descr, boolean mandatory, Category category, QuestionType questionType)
	{
		this.descr = descr;
		this.mandatory = mandatory;
		this.category = category;
		this.questionType = questionType;
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

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	/*public Set<SurveyQuestion> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(Set<SurveyQuestion> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public Set<QuestionAnswerOption> getQuestionAnswerOptions() {
		return questionAnswerOptions;
	}

	public void setQuestionAnswerOptions(
			Set<QuestionAnswerOption> questionAnswerOptions) {
		this.questionAnswerOptions = questionAnswerOptions;
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
