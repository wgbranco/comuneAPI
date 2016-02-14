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
* Esse arquivo implementa um objeto da classe 'RatingQuestionAnswer' 
* e contém anotações para a criação da tabela 'RATING_QUESTION_ANSWER' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "RATING_QUESTION_ANSWER",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_completed_survey", "id_question"}))
public class RatingQuestionAnswer 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_completed_survey", nullable = false)
	private CompletedSurvey completedSurvey;
	
	@ManyToOne
	@JoinColumn(name = "id_question", nullable = false)
	private Question question;
	
	@Column(name = "answer", nullable = false)
	private Float answer;
	
	
	public RatingQuestionAnswer()
	{}
	
	public RatingQuestionAnswer(CompletedSurvey completedSurvey, Question question, Float answer)
	{
		this.completedSurvey = completedSurvey;
		this.question = question;
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CompletedSurvey getCompletedSurvey() {
		return completedSurvey;
	}

	public void setCompletedSurvey(CompletedSurvey completedSurvey) {
		this.completedSurvey = completedSurvey;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Float getAnswer() {
		return answer;
	}

	public void setAnswer(Float answer) {
		this.answer = answer;
	}
	
}
