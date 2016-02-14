package com.comune.model.android;


import java.util.List;
import com.comune.model.AnswerOption;


/**
* Esse arquivo implementa um objeto da classe 'MultipleChoiceQuestion', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class MultipleChoiceQuestion extends EntireQuestion 
{
	private int answerId;
	
	private List<AnswerOption> answerOptions;
	
	
	public MultipleChoiceQuestion()
	{}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public List<AnswerOption> getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(List<AnswerOption> answerOptions) {
		this.answerOptions = answerOptions;
	}
	
}
