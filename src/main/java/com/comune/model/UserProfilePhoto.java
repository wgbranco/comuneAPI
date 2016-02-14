package com.comune.model;


import javax.persistence.*;


/**
* Esse arquivo implementa um objeto da classe 'UserProfilePhoto' 
* e contém anotações para a criação da tabela 'USER_PROFILE_PHOTO' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "USER_PROFILE_PHOTO",
			uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "id_picture"}))
public class UserProfilePhoto 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false, unique = true)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_picture", nullable = false)
	private UploadedFiles picture;
	
	
	public UserProfilePhoto()
	{}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UploadedFiles getPicture() {
		return picture;
	}

	public void setPicture(UploadedFiles picture) {
		this.picture = picture;
	}
	
}
