package com.comune.model.android;


import com.comune.model.AnswerOption;


/**
* Esse arquivo implementa um objeto da classe 'AnswerOptionStatus', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class AnswerOptionStatus extends AnswerOption
{
	private boolean checked;
	
	
	public AnswerOptionStatus()
	{
		super();
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}	
}
