package com.comune.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
* Esse arquivo implementa um objeto da classe 'AnswerOption' 
* e contém anotações para a criação da tabela 'ANSWER_OPTION' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

@Entity
@Table(name = "ANSWER_OPTION")
public class AnswerOption 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "header", length = 100, nullable = false)
	private String header;
	
	/*@OneToMany(mappedBy = "answerOption")
	private Set<QuestionAnswerOption> questionAnswerOptions;
	
	@OneToMany(mappedBy = "answer")
	private Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers;
	
	@OneToMany(mappedBy = "answerOption")
	private Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers;*/
	
	
	public AnswerOption()
	{}
	
	public AnswerOption(String header)
	{
		this.header = header;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	/*public Set<QuestionAnswerOption> getQuestionAnswerOptions() {
		return questionAnswerOptions;
	}

	public void setQuestionAnswerOptions(
			Set<QuestionAnswerOption> questionAnswerOptions) {
		this.questionAnswerOptions = questionAnswerOptions;
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
