package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'RatingQuestion', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class RatingQuestion extends EntireQuestion 
{
	private float answer;
	
	
	public RatingQuestion()
	{
		super();
	}

	public float getAnswer() {
		return answer;
	}

	public void setAnswer(float answer) {
		this.answer = answer;
	}
	
}
