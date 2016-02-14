package com.comune.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;


/**
* Esse arquivo implementa um objeto da classe 'Category' 
* e contém anotações para a criação da tabela 'CATEGORY' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "CATEGORY")
public class Category
{
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	/*@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private Set<Subcategory> subcategories;

	@OneToMany(mappedBy = "category")
	private Set<Question> questions;*/
	
	
	public Category()
	{}
	
	public Category(int id, String name)
	{
		this.id = id;
		this.name = name;
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

	/*public Set<Subcategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(Set<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}*/

}