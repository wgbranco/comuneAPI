package com.comune.model.android;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import com.comune.util.Constants;


/**
* Esse arquivo implementa um objeto da classe 'CheckboxesQuestion', 
* utilizado para mapear objetos JSON recebidos em uma requisição ou enviados como resposta.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = YesOrNoQuestion.class, name = "1"), //Integer.toString(Constants.TIPO_QUESTAO_SIM_NAO)
        @JsonSubTypes.Type(value = RatingQuestion.class, name = "2"), //Integer.toString(Constants.TIPO_QUESTAO_ESCALA)
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "3"), //Integer.toString(Constants.TIPO_QUESTAO_MULTIPLA_ESCOLHA)
        @JsonSubTypes.Type(value = CheckboxesQuestion.class, name = "4") //Integer.toString(Constants.TIPO_QUESTAO_CHECKBOXES)
        })
public class EntireQuestion 
{
	private int id;
	private int type;
	private String descr;
	private boolean mandatory;
	private int category;
	private int subcategory;
	
	public EntireQuestion()
	{}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(int subcategory) {
		this.subcategory = subcategory;
	}
	
}
