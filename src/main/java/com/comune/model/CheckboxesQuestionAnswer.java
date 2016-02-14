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
* Esse arquivo implementa um objeto da classe 'CheckboxesQuestionAnswer' 
* e contém anotações para a criação da tabela 'CHECKBOXES_QUESTION_ANSWER' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "CHECKBOXES_QUESTION_ANSWER",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_completed_survey", "id_question_answer_option"}))
public class CheckboxesQuestionAnswer
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_completed_survey", nullable = false)
	private CompletedSurvey completedSurvey;
	
	@ManyToOne
	@JoinColumn(name = "id_question_answer_option", nullable = false)
	private QuestionAnswerOption questionAnswerOption;
	
	@Column(name = "checked", nullable = false)
	private boolean checked;
	
	
	public CheckboxesQuestionAnswer()
	{}
	
	public CheckboxesQuestionAnswer(CompletedSurvey completedSurvey, QuestionAnswerOption questionAnswerOption, boolean checked)
	{
		this.completedSurvey = completedSurvey;
		this.questionAnswerOption = questionAnswerOption;
		this.checked = checked;
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

	public QuestionAnswerOption getQuestionAnswerOption() {
		return questionAnswerOption;
	}

	public void setQuestionAnswerOption(QuestionAnswerOption questionAnswerOption) {
		this.questionAnswerOption = questionAnswerOption;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}	