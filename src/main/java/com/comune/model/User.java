package com.comune.model;


import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;


/**
* Esse arquivo implementa um objeto da classe 'User' 
* e contém anotações para a criação da tabela 'USER_INFO' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "USER_INFO")
public class User
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;
	
	@Column(name = "date_birth", nullable = false)
	private Date dateOfBirth;
	
	@Email
	@Column(name = "email", length = 255, nullable = false, unique = true)
	private String email;
	
	@Column(name = "hashed_password", length = 255, nullable = false)
	private String hashedPassword;
	
	@Column(name = "hashed_temp_password", length = 255)
	private String hashedTemporaryPassword;
	
	@Column(name = "mobile_number", length = 30, nullable = false)
	private String mobileNumber;
	
	@Column(name = "phone_number", length = 30)
	private String phoneNumber;
	
	/*@Column(name = "photo")
	@Lob
	private Blob photo;*/
	
	@Column(name = "registered_at", nullable = false)
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp registeredAt;
	
	@Column(name = "last_logged_in_at")
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastLoggedInAt;
	
	/*@OneToMany(mappedBy = "user")
	private Set<Report> reports;
	
	@OneToMany(mappedBy = "user")
	private Set<CompletedSurvey> completedSurveys;
	
	@OneToMany(mappedBy = "user")
	private Set<YesNoQuestionAnswer> yesNoQuestionAnswers;
	
	@OneToMany(mappedBy = "user")
	private Set<RatingQuestionAnswer> ratingQuestionAnswers;
	
	@OneToMany(mappedBy = "user")
	private Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers;
	
	@OneToMany(mappedBy = "user")
	private Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers;*/
	
	
	public User()
	{}
	
	public User(String email, String password, String firstName, 
			String lastName, Date dateOfBirth, String mobileNumber)
	{
		this.email = email;
		this.hashedPassword = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getHashedTemporaryPassword() {
		return hashedTemporaryPassword;
	}

	public void setHashedTemporaryPassword(String hashedTemporaryPassword) {
		this.hashedTemporaryPassword = hashedTemporaryPassword;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*public Blob getPhoto() {
		return photo;
	}
	
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}*/

	public Timestamp getRegisteredAt() {
		return registeredAt;
	}
	
	public void setRegisteredAt(Timestamp registeredAt) {
		this.registeredAt = registeredAt;
	}

	public Timestamp getLastLoggedInAt() {
		return lastLoggedInAt;
	}

	public void setLastLoggedInAt(Timestamp lastLoggedInAt) {
		this.lastLoggedInAt = lastLoggedInAt;
	}

	/*public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public Set<CompletedSurvey> getCompletedSurveys() {
		return completedSurveys;
	}

	public void setCompletedSurveys(Set<CompletedSurvey> completedSurveys) {
		this.completedSurveys = completedSurveys;
	}

	public Set<YesNoQuestionAnswer> getYesNoQuestionAnswers() {
		return yesNoQuestionAnswers;
	}

	public void setYesNoQuestionAnswers(
			Set<YesNoQuestionAnswer> yesNoQuestionAnswers) {
		this.yesNoQuestionAnswers = yesNoQuestionAnswers;
	}

	public Set<RatingQuestionAnswer> getRatingQuestionAnswers() {
		return ratingQuestionAnswers;
	}

	public void setRatingQuestionAnswers(
			Set<RatingQuestionAnswer> ratingQuestionAnswers) {
		this.ratingQuestionAnswers = ratingQuestionAnswers;
	}

	public Set<MultipleChoiceQuestionAnswer> getMultipleChoiceQuestionAnswers() {
		return multipleChoiceQuestionAnswers;
	}

	public void setMultipleChoiceQuestionAnswers(
			Set<MultipleChoiceQuestionAnswer> multipleChoiceQuestionAnswers) {
		this.multipleChoiceQuestionAnswers = multipleChoiceQuestionAnswers;
	}

	public Set<CheckboxesQuestionAnswer> getCheckboxesQuestionAnswers() {
		return checkboxesQuestionAnswers;
	}

	public void setCheckboxesQuestionAnswers(
			Set<CheckboxesQuestionAnswer> checkboxesQuestionAnswers) {
		this.checkboxesQuestionAnswers = checkboxesQuestionAnswers;
	}*/
	
}