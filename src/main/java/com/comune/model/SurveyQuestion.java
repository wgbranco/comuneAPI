package com.comune.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
* Esse arquivo implementa um objeto da classe 'SurveyQuestion' 
* e contém anotações para a criação da tabela 'SURVEY_QUESTIONS' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "SURVEY_QUESTIONS",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_survey", "id_question"}))
public class SurveyQuestion 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_survey", nullable = false)
	private Survey survey;
	
	@ManyToOne
	@JoinColumn(name = "id_question", nullable = false)
	private Question question;
	
	
	public SurveyQuestion()
	{}
	
	public SurveyQuestion(Survey survey, Question question)
	{
		this.survey = survey;
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
