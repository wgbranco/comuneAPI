package com.comune.model.android;


import java.util.List;


/**
* Esse arquivo implementa um objeto da classe 'CheckboxesQuestion', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class CheckboxesQuestion extends EntireQuestion 
{
	private List<AnswerOptionStatus> answerOptions;
	
	
	public CheckboxesQuestion()
	{
		super();
	}

	public List<AnswerOptionStatus> getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(List<AnswerOptionStatus> answerOptions) {
		this.answerOptions = answerOptions;
	}
	
}
