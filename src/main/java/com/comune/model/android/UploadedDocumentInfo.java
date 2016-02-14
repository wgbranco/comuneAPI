package com.comune.model.android;


/**
* Esse arquivo implementa um objeto da classe 'UploadedDocumentInfo', 
* utilizado para mapear objetos JSON enviados como resposta a uma requisição.
* @author  William Gomes de Branco
* @version 1.0
* @since   2015-11-16 
*/

public class UploadedDocumentInfo 
{
	private int ownerId;
	private int fileId;
	private String fileName;
	
	
	public UploadedDocumentInfo()
	{}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
