package com.comune.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
* Esse arquivo implementa um objeto da classe 'Subcategory' 
* e contém anotações para a criação da tabela 'SUBCATEGORY' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "SUBCATEGORY")
public class Subcategory
{
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;
	
	/*@OneToMany(mappedBy = "subcategory")
	private Set<Place> places;
	
	@OneToMany(mappedBy = "subcategory")
	private Set<Question> questions;*/
	
	
	public Subcategory()
	{}
	
	public Subcategory(int id, String name, Category category)
	{
		this.id = id;
		this.name = name;
		this.category = category;
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	/*public Set<Place> getPlaces() {
		return places;
	}

	public void setPlaces(Set<Place> places) {
		this.places = places;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}*/
	
}