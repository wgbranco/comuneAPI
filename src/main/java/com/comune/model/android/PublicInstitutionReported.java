package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'PublicInstitutionReported', 
* utilizado para mapear objetos JSON enviados como resposta a uma requisição.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class PublicInstitutionReported 
{
	private PublicInstitution institution;
	private int numberReportsMade;
	
	
	public PublicInstitutionReported()
	{}
	
	public PublicInstitution getInstitution() {
		return institution;
	}
	public void setInstitution(PublicInstitution institution) {
		this.institution = institution;
	}
	public int getNumberReportsMade() {
		return numberReportsMade;
	}
	public void setNumberReportsMade(int numberReportsMade) {
		this.numberReportsMade = numberReportsMade;
	}
	
}
