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
* Esse arquivo implementa um objeto da classe 'QuestionAnswerOption' 
* e contém anotações para a criação da tabela 'QUESTION_ANSWER_OPTION' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "QUESTION_ANSWER_OPTION",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id_question", "id_answer_option"}))
public class QuestionAnswerOption 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_question", nullable = false)
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "id_answer_option", nullable = false)
	private AnswerOption answerOption;
	
	
	public QuestionAnswerOption()
	{}
	
	public QuestionAnswerOption(Question question, AnswerOption answerOption)
	{
		this.question = question;
		this.answerOption = answerOption;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public AnswerOption getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(AnswerOption answerOption) {
		this.answerOption = answerOption;
	}
	
}
