package com.comune.model;

import java.sql.Timestamp;

import javax.persistence.*;


/**
* Esse arquivo implementa um objeto da classe 'UploadedFiles' 
* e contém anotações para a criação da tabela 'UPLOADED_FILES' à partir deste.
*
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/
@Entity
@Table(name = "UPLOADED_FILES")
public class UploadedFiles 
{
	@Id @GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "content_type", length = 100, nullable = false)
	private String contentType;
	
	@Column(name = "content_length", nullable = false) //em bytes
	private int contentLength;
	
	@Column(name = "content", nullable = false)
	@Lob @Basic(fetch = FetchType.LAZY)
	private byte[] content;

	@Column(name = "received_at", nullable = false)
	private Timestamp receivedAt;
	
	
	public UploadedFiles()
	{}

	
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Timestamp getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(Timestamp receivedAt) {
		this.receivedAt = receivedAt;
	}
	
}
