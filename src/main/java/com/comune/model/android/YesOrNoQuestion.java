package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'YesOrNoQuestion', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class YesOrNoQuestion extends EntireQuestion 
{
	private boolean answer;
	
	
	public YesOrNoQuestion()
	{
		super();
	}

	public boolean getAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
}
