package com.comune.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
* Esse arquivo implementa um objeto da classe 'QuestionType' 
* e contém anotações para a criação da tabela 'QUESTION_TYPE' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "QUESTION_TYPE")
public class QuestionType 
{
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	/*@OneToMany(mappedBy = "questionType")
	private Set<Question> questions;*/
	
	
	public QuestionType()
	{}
	
	public QuestionType(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/*Getters and Setters*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}*/
	
}
